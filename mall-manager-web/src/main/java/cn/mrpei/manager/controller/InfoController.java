package cn.mrpei.manager.controller;

import cn.mrpei.common.pojo.ServerResponse;
import cn.mrpei.manager.service.OrderService;
import cn.mrpei.manager.service.ProductService;
import cn.mrpei.manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author peizhouyu (大数据部-大数据研发部-平台产品研发部)
 * @version V1.0.0
 * @description
 * @date 2018/5/22
 * @last-modified ：
 * @class cn.mrpei.manager.controller
 * @copyright Copyright © 2004-2018 京东JD.com ALL Right Reserved
 * @see
 */
@Controller
@RequestMapping("/manage/statistic")
public class InfoController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @RequestMapping("/base_count.do")
    @ResponseBody
    public ServerResponse baseCount(){
        Long userCount = userService.getUserCount();
        Long productCount = productService.getProductCount();
        Long orderCount = orderService.getOrderCount();
        Map<String,Long> countInfo = new HashMap<>();
        countInfo.put("userCount",userCount);
        countInfo.put("productCount",productCount);
        countInfo.put("orderCount",orderCount);
        return ServerResponse.createBySuccess(countInfo);

    }

}
