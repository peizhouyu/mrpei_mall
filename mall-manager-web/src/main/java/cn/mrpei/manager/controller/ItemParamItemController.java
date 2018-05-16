package cn.mrpei.manager.controller;

import cn.mrpei.manager.service.ItemParamItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
public class ItemParamItemController {

    @Autowired
    private ItemParamItemService itemParamItemService;

    /**
     * Show item param string.
     * 展示商品规格参数
     * @param itemId the item id
     * @param model  the model
     * @return the string
     */
    @RequestMapping("/showitem/{itemId}")
    public String showItemParam(@PathVariable Long itemId, Model model) {
        String string = itemParamItemService.getItemParamByItemId(itemId);
        model.addAttribute("itemParam", string);
        return "item";
    }
}
