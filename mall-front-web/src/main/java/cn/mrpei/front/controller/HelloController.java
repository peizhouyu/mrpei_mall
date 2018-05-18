package cn.mrpei.front.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Date:2017/11/20
 * Time:19:03
 *
 * @author 裴周宇
 */
@Controller
public class HelloController {

    @RequestMapping("/hello.do")
    @ResponseBody
    public String testInfo(){
        return "<h1>application start success</h1>";
    }
}
