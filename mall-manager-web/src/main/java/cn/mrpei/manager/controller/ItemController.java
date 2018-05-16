package cn.mrpei.manager.controller;

import cn.mrpei.common.pojo.EUDataGridResult;
import cn.mrpei.common.pojo.MallResult;
import cn.mrpei.manager.pojo.TbItem;
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
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    /**
     * Gets item by id.
     * 商品管理
     * @param itemId the item id
     * @return the item by id
     */
    @RequestMapping("/{itemId}")
    @ResponseBody
    public TbItem getItemById(@PathVariable Long itemId) {
        TbItem tbItem = itemService.getItemById(itemId);
        return tbItem;
    }

    /**
     * Gets item list.
     *
     * @param page the page
     * @param rows the rows
     * @return the item list
     */
    @RequestMapping("/list")
    @ResponseBody
    public EUDataGridResult getItemList(Integer page, Integer rows) {
        EUDataGridResult result = itemService.getItemList(page, rows);
        return result;
    }

    @RequestMapping(value="/save", method=RequestMethod.POST)
    @ResponseBody
    private MallResult createItem(TbItem item, String desc, String itemParams) throws Exception {
        MallResult result = itemService.createItem(item, desc, itemParams);
        return result;
    }
}
