package cn.mrpei.front.controller;


import cn.mrpei.common.pojo.ResponseCodeEnum;
import cn.mrpei.common.pojo.ServerResponse;
import cn.mrpei.front.common.CommonMethod;
import cn.mrpei.manager.pojo.Shipping;
import cn.mrpei.manager.pojo.User;
import cn.mrpei.manager.service.ShippingService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Date:2017/11/18
 * Time:17:06
 *
 * @author 裴周宇
 */
@Controller
@RequestMapping("/shipping")
public class ShippingController {

    @Autowired
    private ShippingService shippingService;


    @RequestMapping("/add.do")
    @ResponseBody
    public ServerResponse add(HttpServletRequest httpServletRequest, Shipping shipping){
        User user = CommonMethod.checkLoginStatus(httpServletRequest);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCodeEnum.NEED_LOGIN.getCode(),"未登录，需要转到登录页！");
        }
        return shippingService.add(user.getId(),shipping);
    }

    @RequestMapping("/del.do")
    @ResponseBody
    public ServerResponse del(HttpServletRequest httpServletRequest, Integer shippingId){
        User user = CommonMethod.checkLoginStatus(httpServletRequest);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCodeEnum.NEED_LOGIN.getCode(),"未登录，需要转到登录页！");
        }
        return shippingService.del(user.getId(),shippingId);
    }


    @RequestMapping("/update.do")
    @ResponseBody
    public ServerResponse update(HttpServletRequest httpServletRequest, Shipping shipping){
        User user = CommonMethod.checkLoginStatus(httpServletRequest);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCodeEnum.NEED_LOGIN.getCode(),"未登录，需要转到登录页！");
        }
        return shippingService.update(user.getId(),shipping);
    }

    //查看地址详情
    @RequestMapping("/select.do")
    @ResponseBody
    public ServerResponse<Shipping> select(HttpServletRequest httpServletRequest, Integer shippingId){
        User user = CommonMethod.checkLoginStatus(httpServletRequest);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCodeEnum.NEED_LOGIN.getCode(),"未登录，需要转到登录页！");
        }
        return shippingService.select(user.getId(),shippingId);
    }

    //查看地址列表
    @RequestMapping("/list.do")
    @ResponseBody
    public ServerResponse<PageInfo> list(HttpServletRequest httpServletRequest,
                                         @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                         @RequestParam(value = "pageSize",defaultValue = "10") int pageSize){
        User user = CommonMethod.checkLoginStatus(httpServletRequest);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCodeEnum.NEED_LOGIN.getCode(),"未登录，需要转到登录页！");
        }
        return shippingService.list(user.getId(),pageNum,pageSize);
    }




}
