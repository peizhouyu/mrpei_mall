package cn.mrpei.manager.controller;

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
@RequestMapping("/content")
public class ContentController {

    @Autowired
    private ContentService contentService;

    /**
     * Insert content mall result.
     * 内容管理
     * @param content the content
     * @return the mall result
     */
    @RequestMapping("/save")
    @ResponseBody
    public MallResult insertContent(TbContent content) {
        MallResult result = contentService.insertContent(content);
        return result;
    }
}
