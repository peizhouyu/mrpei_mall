package cn.mrpei.search.service.impl;

import cn.mrpei.common.dto.EsCount;
import cn.mrpei.common.dto.EsInfo;
import cn.mrpei.common.exception.MallException;
import cn.mrpei.manager.pojo.Product;
import cn.mrpei.search.dao.ProductMapper;
import cn.mrpei.search.service.SearchItemService;
import com.google.gson.Gson;

import cn.mrpei.common.utils.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.util.List;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * @author peizhouyu (大数据部-大数据研发部-平台产品研发部)
 * @version V1.0.0
 * @description
 * @date 2018/5/22
 * @last-modified ：
 * @class cn.mrpei.search.service.impl
 * @copyright Copyright © 2004-2018 京东JD.com ALL Right Reserved
 * @see
 */
@Service
public class SearchItemServiceImpl implements SearchItemService {
    private final static Logger log= LoggerFactory.getLogger(SearchItemServiceImpl.class);

    @Autowired
    private ProductMapper productMapper;

    @Value("${ES_CONNECT_IP}")
    private String ES_CONNECT_IP;

    @Value("${ES_NODE_CLIENT_PORT}")
    private String ES_NODE_CLIENT_PORT;

    @Value("${ES_CLUSTER_NAME}")
    private String ES_CLUSTER_NAME;

    @Value("${ITEM_INDEX}")
    private String ITEM_INDEX;

    @Value("${ITEM_TYPE}")
    private String ITEM_TYPE;

    @Override
    public int importAllItems() {

        try{
            System.setProperty("es.set.netty.runtime.available.processors", "false");
            Settings settings = Settings.builder()
                    .put("cluster.name", ES_CLUSTER_NAME).build();
            TransportClient client = new PreBuiltTransportClient(settings)
                    .addTransportAddress(new TransportAddress(InetAddress.getByName(ES_CONNECT_IP), 9300));

            //批量添加
            BulkRequestBuilder bulkRequest = client.prepareBulk();

            //查询商品列表
            List<Product> productList = productMapper.selectList();

            //遍历商品列表
            for (Product product : productList) {

                bulkRequest.add(client.prepareIndex(ITEM_INDEX, ITEM_TYPE, String.valueOf(product.getId()))
                        .setSource(jsonBuilder()
                                .startObject()
                                .field("id", product.getId())
                                .field("categoryId", product.getCategoryId())
                                .field("name", product.getName())
                                .field("subtitle", product.getSubtitle())
                                .field("mainImage", product.getMainImage())
                                .field("subImages", product.getSubImages())
                                .field("detail", product.getDetail())
                                .field("price", product.getPrice())
                                .field("stock", product.getStock())
                                .field("status", product.getStatus())
                                .field("createTime", product.getCreateTime())
                                .field("updateTime", product.getUpdateTime())
                                .endObject()
                        )
                );
            }

            BulkResponse bulkResponse = bulkRequest.get();

            log.info("更新索引成功");

            client.close();
        }catch (Exception e){
            e.printStackTrace();
            throw new MallException("导入ES索引库出错，请再次尝试");
        }

        return 1;
    }


    @Override
    public EsInfo getEsInfo() {

        String healthUrl="http://"+ES_CONNECT_IP+":"+ES_NODE_CLIENT_PORT+"/_cluster/health";
        String countUrl="http://"+ES_CONNECT_IP+":"+ES_NODE_CLIENT_PORT+"/_count";
        String healthResult=HttpUtil.sendGet(healthUrl);
        String countResult=HttpUtil.sendGet(countUrl);
        if(StringUtils.isBlank(healthResult)||StringUtils.isBlank(countResult)){
            throw new MallException("连接集群失败，请检查ES运行状态");
        }
        EsInfo esInfo=new EsInfo();
        EsCount esCount=new EsCount();
        try {
            esInfo=new Gson().fromJson(healthResult,EsInfo.class);
            esCount=new Gson().fromJson(countResult,EsCount.class);
            esInfo.setCount(esCount.getCount());
        }catch (Exception e){
            e.printStackTrace();
            throw new MallException("获取ES信息出错");
        }

        return esInfo;
    }
}
