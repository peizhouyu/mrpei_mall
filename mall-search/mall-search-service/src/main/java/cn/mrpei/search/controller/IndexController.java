package cn.mrpei.search.controller;

import cn.mrpei.search.service.SearchItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author peizhouyu (大数据部-大数据研发部-平台产品研发部)
 * @version V1.0.0
 * @description
 * @date 2018/5/22
 * @last-modified ：
 * @class cn.mrpei.search.controller
 * @copyright Copyright © 2004-2018 京东JD.com ALL Right Reserved
 * @see
 */
@Controller
@RequestMapping("/es/search")
public class IndexController {

    @Autowired
    private SearchItemService searchItemService;

    @RequestMapping("/hello")
    @ResponseBody
    public String sayHello(){
        return "hello mrpei! this is es index service";
    }

    @RequestMapping("/init")
    @ResponseBody
    public String initIndex(){
        searchItemService.importAllItems();
       return "es search inited";
    }

}
