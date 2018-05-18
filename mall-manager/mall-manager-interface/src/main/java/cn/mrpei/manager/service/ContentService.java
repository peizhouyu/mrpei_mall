package cn.mrpei.manager.service;

import cn.mrpei.common.pojo.EUDResult;
import cn.mrpei.common.pojo.MallResult;
import cn.mrpei.manager.pojo.TbContent;

/**
 * @author peizhouyu (大数据部-大数据研发部-平台产品研发部)
 * @version V1.0.0
 * @description
 * @date 2018/5/16
 * @last-modified ：
 * @class cn.mrpei.manager.service
 * @copyright Copyright © 2004-2018 京东JD.com ALL Right Reserved
 * @see
 */
public interface ContentService {
    MallResult insertContent(TbContent content);
    EUDResult getContentList(long page, long pageSize);
    MallResult deleteContent(String ids);
    MallResult updateContent(TbContent content);
}
