package cn.mrpei.manager.controller;

import cn.mrpei.common.pojo.EUDResult;
import cn.mrpei.common.pojo.MallResult;
import cn.mrpei.manager.pojo.TbItemParam;
import cn.mrpei.manager.pojo.TbItemParamItem;
import cn.mrpei.manager.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author peizhouyu (大数据部-大数据研发部-平台产品研发部)
 * @version V1.0.0
 * @description
 * @date 2018/5/16
 * @last-modified ：
 * @class cn.mrpei.manager.controller
 * @copyright Copyright © 2004-2018 京东JD.com ALL Right Reserved
 * @see
 */
@Controller
@RequestMapping("/item/param")
public class ItemParamController {

    @Autowired
    private ItemParamService itemParamService;

    @RequestMapping("/query/itemcatid/{itemCatId}")
    @ResponseBody
    public MallResult getItemParamByCid(@PathVariable Long itemCatId) {
        MallResult result = itemParamService.getItemParamByCid(itemCatId);
        return result;
    }

    @RequestMapping("/save/{cid}")
    @ResponseBody
    public MallResult insertItemParam(@PathVariable Long cid, String paramData) {
        //创建pojo对象
        TbItemParam itemParam = new TbItemParam();
        itemParam.setItemCatId(cid);
        itemParam.setParamData(paramData);
        MallResult result = itemParamService.insertItemParam(itemParam);
        return result;
    }

    @RequestMapping("/list")
    @ResponseBody
    public EUDResult getItemList(Integer page, Integer rows){
        EUDResult result = itemParamService.getItemList(page, rows);
        return result;
    }


    //加载商品规格
    @RequestMapping("/item/query/{id}")
    @ResponseBody
    public TbItemParamItem listItemDesc(@PathVariable Long id) {
        return itemParamService.listParamDesc(id);
    }


    //删除商品规格参数模板
    @RequestMapping("/delete")
    @ResponseBody
    public MallResult  deleteParam(String ids){
        return itemParamService.deleteParam(ids);

    }
}
