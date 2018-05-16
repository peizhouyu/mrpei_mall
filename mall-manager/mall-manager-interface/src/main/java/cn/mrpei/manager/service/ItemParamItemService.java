package cn.mrpei.manager.service;

/**
 *
 * @author peizhouyu (大数据部-大数据研发部-平台产品研发部)
 * @version V1.0.0
 * @description
 * @date 2018 /5/16
 * @last-modified ：
 * @class cn.mrpei.manager.service
 * @copyright Copyright © 2004-2018 京东JD.com ALL Right Reserved
 * @see
 */
public interface ItemParamItemService {
    /**
     * Gets item param by item id.
     *
     * @param itemId the item id
     * @return the item param by item id
     */
    String getItemParamByItemId(Long itemId);
}
