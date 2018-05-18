package cn.mrpei.manager.service;

import cn.mrpei.common.pojo.EUDataGridResult;
import cn.mrpei.common.pojo.MallResult;
import cn.mrpei.manager.pojo.TbItem;
import cn.mrpei.manager.pojo.TbItemDesc;
import cn.mrpei.manager.pojo.TbItemParamItem;

/**
 * The interface Item service.
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
public interface ItemService {

    /**
     * Gets item by id.
     *
     * @param itemId the item id
     * @return the item by id
     */
    TbItem getItemById(long itemId);

    /**
     * Gets item list.
     *
     * @param page the page
     * @param rows the rows
     * @return the item list
     */
    EUDataGridResult getItemList(int page, int rows);

    /**
     * Create item mall result.
     *
     * @param item      the item
     * @param desc      the desc
     * @param itemParam the item param
     * @return the mall result
     * @throws Exception the exception
     */
    MallResult createItem(TbItem item, String desc, String itemParam) throws Exception;

    MallResult deleteItem(String ids);

    TbItemDesc listItemDesc(Long id);

    /**
     *
     * 更新商品
     * @param item
     * @param desc
     * @param itemParamss
     * @return
     */

    MallResult updateItem(TbItem item, TbItemDesc desc, TbItemParamItem itemParamss);

    /**
     *
     * 下架商品
     * @param ids
     * @return
     */
    MallResult instockItem(String ids);

    //上架
    MallResult reshelfItem(String ids);
}
