package cn.mrpei.manager.controller;

import cn.mrpei.common.pojo.EUTreeNode;
import cn.mrpei.common.pojo.MallResult;
import cn.mrpei.manager.service.ContentCategoryService;
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
 * @date 2018/5/16
 * @last-modified ：
 * @class cn.mrpei.manager.controller
 * @copyright Copyright © 2004-2018 京东JD.com ALL Right Reserved
 * @see
 */
@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {

    @Autowired
    private ContentCategoryService contentCategoryService;

    /**
     * Gets content cat list.
     * 内容分类管理
     * @param parentId the parent id
     * @return the content cat list
     */
    @RequestMapping("/list")
    @ResponseBody
    public List<EUTreeNode> getContentCatList(@RequestParam(value="id", defaultValue="0")Long parentId) {
        List<EUTreeNode> list = contentCategoryService.getCategoryList(parentId);
        return list;
    }

    @RequestMapping("/create")
    @ResponseBody
    public MallResult createContentCategory(Long parentId, String name) {
        MallResult result = contentCategoryService.insertContentCategory(parentId, name);
        return result;
    }
}
