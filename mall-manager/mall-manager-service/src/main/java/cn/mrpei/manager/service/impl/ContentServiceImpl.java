package cn.mrpei.manager.service.impl;

import cn.mrpei.common.jedis.JedisClientCluster;
import cn.mrpei.common.jedis.JedisClientPool;
import cn.mrpei.common.pojo.MallResult;
import cn.mrpei.common.utils.HttpClientUtil;
import cn.mrpei.common.utils.JsonUtils;
import cn.mrpei.manager.dao.TbContentMapper;
import cn.mrpei.manager.pojo.TbContent;
import cn.mrpei.manager.pojo.TbContentExample;
import cn.mrpei.manager.service.ContentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author peizhouyu (大数据部-大数据研发部-平台产品研发部)
 * @version V1.0.0
 * @description
 * @date 2018/5/16
 * @last-modified ：
 * @class cn.mrpei.manager.service.impl
 * @copyright Copyright © 2004-2018 京东JD.com ALL Right Reserved
 * @see
 */
@Service
public class ContentServiceImpl implements ContentService {

    private JedisClientCluster cluster;

    @Autowired
    private TbContentMapper contentMapper;
    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value("${REST_CONTENT_SYNC_URL}")
    private String REST_CONTENT_SYNC_URL;

    @Autowired
    private JedisClientPool jedisClientPool;

    @Override
    public MallResult insertContent(TbContent content) {
        //补全pojo内容
        content.setCreated(new Date());
        content.setUpdated(new Date());
        contentMapper.insert(content);

        //添加缓存同步逻辑
        //TODO  发送消息给MQ
        try {
            HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_SYNC_URL + content.getCategoryId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return MallResult.ok();
    }


    public MallResult getContentList(long cid) throws Exception{
        //缓存逻辑，先判断缓存中是否有内容
        try {
            String contentStr = cluster.hget("content_key", cid + "");
            if (!StringUtils.isBlank(contentStr)) {
                //把json字符串转换成对象列表
                List<TbContent> list = JsonUtils.jsonToList(contentStr, TbContent.class);
                //返回结果
                return MallResult.ok(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
            //缓存不能影响正常逻辑
        }
        //从数据库中加载数据
        TbContentExample example = new TbContentExample();
        //添加条件
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(cid);
        List<TbContent> list = contentMapper.selectByExample(example);

        //把结果添加到redis数据库中
        try {
            cluster.hset("content_key", cid + "", JsonUtils.objectToJson(list));
        } catch (Exception e) {
            e.printStackTrace();
            //缓存不能影响正常逻辑
        }

        //返回结果
        return MallResult.ok(list);
    }

}
