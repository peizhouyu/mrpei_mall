package cn.mrpei.search.service;

import cn.mrpei.common.pojo.ServerResponse;
import com.github.pagehelper.PageInfo;

/**
 * @author peizhouyu (大数据部-大数据研发部-平台产品研发部)
 * @version V1.0.0
 * @description
 * @date 2018/5/22
 * @last-modified ：
 * @class cn.mrpei.search.service
 * @copyright Copyright © 2004-2018 京东JD.com ALL Right Reserved
 * @see
 */
public interface SearchService {

    /**
     * ES商品搜索
     * @param keyword
     * @param page
     * @param size
     * @param sort
     * @return
     */
    ServerResponse<PageInfo> search(String keyword, int page, int size, String sort);
}
