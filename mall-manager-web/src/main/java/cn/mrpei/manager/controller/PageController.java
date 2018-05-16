package cn.mrpei.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
public class PageController {
    /**
     * 打开首页
     */
    @RequestMapping("/")
    public String showIndex() {
        return "index";
    }

    // 展示其他页面
    @RequestMapping("/{page}")
    public String showpage(@PathVariable String page) {
        return page;
    }
}
