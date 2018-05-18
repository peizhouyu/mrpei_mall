package cn.mrpei.manager.controller;


import cn.mrpei.common.pojo.ServerResponse;
import cn.mrpei.manager.service.CategoryService;
import cn.mrpei.manager.service.OrderService;
import cn.mrpei.manager.service.UserService;
import cn.mrpei.manager.vo.OrderVo;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author peizhouyu (大数据部-大数据研发部-平台产品研发部)
 * @version V1.0.0
 * @description
 * @date 2018/5/18
 * @last-modified ：
 * @class cn.mrpei.common.exception
 * @copyright Copyright © 2004-2018 京东JD.com ALL Right Reserved
 * @see
 */
@Controller
@RequestMapping("/manage/order")
public class OrderManageController {

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private OrderService orderService;


    @RequestMapping("/list.do")
    @ResponseBody
    public ServerResponse<PageInfo> orderList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                              @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        return orderService.manageList(pageNum,pageSize);
    }


    @RequestMapping("/detail.do")
    @ResponseBody
    public ServerResponse<OrderVo> orderDetail(Long orderNo){
        return orderService.manageDetail(orderNo);
    }


    @RequestMapping("/search.do")
    @ResponseBody
    public ServerResponse<PageInfo> orderSearch(Long orderNo,
                                                @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        return orderService.manageSearch(orderNo,pageNum,pageSize);
    }


    @RequestMapping("/send_goods.do")
    @ResponseBody
    public ServerResponse<String> send_goods(Long orderNo){
        return orderService.manageSendGoods(orderNo);
    }



}
