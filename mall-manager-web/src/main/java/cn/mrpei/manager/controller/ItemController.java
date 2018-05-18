package cn.mrpei.manager.controller;

import cn.mrpei.common.pojo.EUDResult;
import cn.mrpei.common.pojo.EUDataGridResult;
import cn.mrpei.common.pojo.MallResult;
import cn.mrpei.manager.pojo.TbItem;
import cn.mrpei.manager.pojo.TbItemDesc;
import cn.mrpei.manager.pojo.TbItemParamItem;
import cn.mrpei.manager.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author peizhouyu (大数据部-大数据研发部-平台产品研发部)
 * @version V1.0.0
 * @description
 * @date 2018 /5/16
 * @last-modified ：
 * @class cn.mrpei.manager.controller
 * @copyright Copyright © 2004-2018 京东JD.com ALL Right Reserved
 * @see
 */
@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping("/item/{itemId}")
    @ResponseBody
    public TbItem getItemById(@PathVariable  Long itemId){
        TbItem tbItem=itemService.getItemById(itemId);
        return tbItem;
    }

    @RequestMapping("/item/list")
    @ResponseBody
    public EUDataGridResult getItemList(Integer page, Integer rows){
        EUDataGridResult result = itemService.getItemList(page, rows);
        return result;
    }


    //接收表单中的内容，使用一个pojo接收表单内容
    @RequestMapping(value="/item/save",method=RequestMethod.POST)
    @ResponseBody
    public MallResult createItem(TbItem item,String desc,String itemParams) throws Exception{
        MallResult result=itemService.createItem(item,desc,itemParams);
        return result;
    }


    //删除商品
    @RequestMapping("/rest/item/delete")
    @ResponseBody
    public MallResult  deleteItem(String ids){
        return itemService.deleteItem(ids);

    }

    //商品描述
    @RequestMapping("/rest/item/query/item/desc/{id}")
    @ResponseBody
    public TbItemDesc listItemDesc(@PathVariable Long id) {
        return itemService.listItemDesc(id);
    }

    //更新
    @RequestMapping(value="/rest/item/update",method=RequestMethod.POST)
    @ResponseBody
    public MallResult updateItem(TbItem item, TbItemDesc desc, TbItemParamItem itemParams){
        MallResult result=itemService.updateItem(item,desc,itemParams);
        return result;
    }

    //下架
    @RequestMapping("/rest/item/instock")
    @ResponseBody
    public MallResult  instockItem(String ids){
        return itemService.instockItem(ids);
    }

    //上架
    @RequestMapping("/rest/item/reshelf")
    @ResponseBody
    public MallResult  reshelfItem(String ids){
        return itemService.reshelfItem(ids);
    }

}
