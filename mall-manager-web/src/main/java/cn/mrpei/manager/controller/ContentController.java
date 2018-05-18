package cn.mrpei.manager.controller;

import cn.mrpei.common.pojo.EUDResult;
import cn.mrpei.common.pojo.MallResult;
import cn.mrpei.manager.pojo.TbContent;
import cn.mrpei.manager.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
public class ContentController {

    @Autowired
    private ContentService contentService;

    /**
     * Insert content mall result.
     * 内容管理
     * @param content the content
     * @return the mall result
     */
    @RequestMapping("/content/save")
    @ResponseBody
    public MallResult insertContent(TbContent content) {
        MallResult result = contentService.insertContent(content);
        return result;
    }

    //加载列表
    @RequestMapping("/content/query/list")
    @ResponseBody
    public EUDResult getContentList(Long page, Long rows){
        EUDResult result = contentService.getContentList(page, rows);
        return result;
    }

    //删除
    @RequestMapping("/content/delete")
    @ResponseBody
    public MallResult deleteContent(String ids){
        return contentService.deleteContent(ids);
    }

    //更新
    @RequestMapping("/rest/content/edit")
    @ResponseBody
    public MallResult updateItem(TbContent content){
        MallResult result=contentService.updateContent(content);
        return result;
    }
}
