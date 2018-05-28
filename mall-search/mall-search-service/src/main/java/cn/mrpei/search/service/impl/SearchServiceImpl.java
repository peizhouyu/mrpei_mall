package cn.mrpei.search.service.impl;

import cn.mrpei.common.exception.MallException;
import cn.mrpei.common.pojo.ServerResponse;
import cn.mrpei.manager.pojo.Product;
import cn.mrpei.search.service.SearchService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Value;

import com.google.gson.Gson;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;

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
public class SearchServiceImpl implements SearchService {


    @Value("${ES_CONNECT_IP}")
    private String ES_CONNECT_IP;

    @Value("${ES_CLUSTER_NAME}")
    private String ES_CLUSTER_NAME;

    @Value("${ITEM_INDEX}")
    private String ITEM_INDEX;

    @Value("${ITEM_TYPE}")
    private String ITEM_TYPE;


    @Override
    public ServerResponse<PageInfo> search(String keyword, int page, int size, String sort) {
        try{
            Settings settings = Settings.builder()
                    .put("cluster.name", ES_CLUSTER_NAME).build();
            TransportClient client = new PreBuiltTransportClient(settings)
                    .addTransportAddress(new TransportAddress(InetAddress.getByName(ES_CONNECT_IP), 9300));

            PageInfo pageInfo = new PageInfo();

            //设置查询条件
            //单字段搜索
            QueryBuilder qb = matchQuery("name",keyword);

            //设置分页
            if (page <=0 ){
                page =1;
            }
            int start=(page - 1) * size;

            //设置高亮显示
            HighlightBuilder hiBuilder=new HighlightBuilder();
            hiBuilder.preTags("<a style=\"color: #e4393c\">");
            hiBuilder.postTags("</a>");
            hiBuilder.field("name");

            //执行搜索
            SearchResponse searchResponse = null;

            searchResponse=client.prepareSearch(ITEM_INDEX)
                    .setTypes(ITEM_TYPE)
                    .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                    .setQuery(qb)	// Query
                    .setFrom(start).setSize(size).setExplain(true)	//从第几个开始，显示size个数据
                    .highlighter(hiBuilder)		//设置高亮显示
                    .get();


            SearchHits hits = searchResponse.getHits();
            //返回总结果数
            pageInfo.setTotal(hits.getTotalHits());
            List<Product> list=new ArrayList<>();
            if (hits.totalHits > 0) {
                for (SearchHit hit : hits) {
                    //总页数
                    int totalPage=(int) (hit.getScore()/size);
                    if((hit.getScore()%size)!=0){
                        totalPage++;
                    }
                    //返回结果总页数
                    pageInfo.setPageNum(totalPage);
                    //设置高亮字段
                    Product product=new Gson().fromJson(hit.getSourceAsString(),Product.class);
                    String name = hit.getHighlightFields().get("name").getFragments()[0].toString();
                    product.setName(name);
                    //返回结果
                    list.add(product);
                }
            }
            pageInfo.setList(list);
            //因个人服务器配置过低此处取消关闭减轻搜索压力增快搜索速度
            //client.close();

            return ServerResponse.createBySuccess(pageInfo);
        }catch (Exception e){
            e.printStackTrace();
            throw new MallException("查询ES索引库出错");
        }
    }
}
