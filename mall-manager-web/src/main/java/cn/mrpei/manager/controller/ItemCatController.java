package cn.mrpei.manager.controller;

import cn.mrpei.common.pojo.EUTreeNode;
import cn.mrpei.manager.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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
@RequestMapping("/item/cat")
public class ItemCatController {


    @Autowired
    private ItemCatService itemCatService;

    //商品分类管理
    @RequestMapping("/list")
    @ResponseBody
    private List<EUTreeNode> getCatList(@RequestParam(value="id",defaultValue="0")Long parentId) {
        List<EUTreeNode> list = itemCatService.getCatList(parentId);
        return list;
    }
}
