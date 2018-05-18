package cn.mrpei.manager.service;

import cn.mrpei.common.pojo.EUDResult;
import cn.mrpei.common.pojo.MallResult;
import cn.mrpei.manager.pojo.TbItemParam;
import cn.mrpei.manager.pojo.TbItemParamItem;

/**
 * The interface Item param service.
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
public interface ItemParamService {
    /**
     * Gets item param by cid.
     *
     * @param cid the cid
     * @return the item param by cid
     */
    MallResult getItemParamByCid(long cid);

    /**
     * Insert item param mall result.
     *
     * @param itemParam the item param
     * @return the mall result
     */
    MallResult insertItemParam(TbItemParam itemParam);
    EUDResult getItemList(int page, int rows);
    MallResult deleteParam(String ids);
    TbItemParamItem listParamDesc(Long id);

}
