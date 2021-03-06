package cn.mrpei.front.controller;


import cn.mrpei.common.pojo.Const;
import cn.mrpei.common.pojo.ResponseCodeEnum;
import cn.mrpei.common.pojo.ServerResponse;
import cn.mrpei.common.utils.CookieUtil;
import cn.mrpei.common.utils.JsonUtil;
import cn.mrpei.common.utils.RedisShardedPoolUtil;
import cn.mrpei.front.common.CommonMethod;
import cn.mrpei.manager.pojo.User;
import cn.mrpei.manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Date:2017/11/9
 * Time:10:30
 *
 * @author 裴周宇
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     *  登录
     * @param username
     * @param password
     * @param session
     * @return
     */
    @RequestMapping(value = "/login.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session, HttpServletResponse httpServletResponse){
        ServerResponse<User> response = userService.login(username, password);
        if (response.isSuccess()){
            //session.setAttribute(Const.CURRENT_USER,response.getData());
            CookieUtil.writeLoginToken(httpServletResponse,session.getId());

            RedisShardedPoolUtil.setEx(session.getId(), JsonUtil.objToString(response.getData()),Const.RedisCacheExtime.REDIS_SESSION_EXTIME);
        }
        return response;
    }

    @RequestMapping(value = "/logout.do")
    @ResponseBody
    public ServerResponse<String> logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        //session.removeAttribute(Const.CURRENT_USER);
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        CookieUtil.delLoginToken(httpServletRequest,httpServletResponse);
        RedisShardedPoolUtil.del(loginToken);
        return ServerResponse.createBySuccess();
    }

    @RequestMapping(value = "/register.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> register(User user){
        return userService.register(user);
    }

    @RequestMapping(value = "/check_valid.do")
    @ResponseBody
    public ServerResponse<String> checkValid(String str, String type){
        return userService.checkValid(str,type);
    }

    @RequestMapping(value = "/get_user_info.do")
    @ResponseBody
    public ServerResponse<User> getUserInfo(HttpServletRequest httpServletRequest){
        //User user = (User) session.getAttribute(Const.CURRENT_USER);
//        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
//        if (loginToken == null){
//            return ServerResponse.createByErrorMessage("用户未登录");
//        }
//        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
//        User user = JsonUtil.stringToObj(userJsonStr, User.class);
        User user = CommonMethod.checkLoginStatus(httpServletRequest);
        if (user != null){
            return ServerResponse.createBySuccess(user);
        }
        return ServerResponse.createByErrorMessage(ResponseCodeEnum.NEED_LOGIN.getDesc());
    }

    @RequestMapping(value = "/forget_get_question.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetGetQuestion(String username){
        return userService.selectQuestion(username);
    }

    @RequestMapping(value = "/forget_check_answer.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetCheckAnswer(String username, String question, String answer){
        return userService.checkAnswer(username,question,answer);
    }

    @RequestMapping(value = "/forget_reset_password.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetRestPassword(String username, String passwordNew, String forgetToken){
        return userService.forgetRestPassword(username,passwordNew,forgetToken);
    }

    @RequestMapping(value = "/reset_password.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> resetPassword(HttpServletRequest httpServletRequest, String passwordOld, String passwordNew){
        User user = CommonMethod.checkLoginStatus(httpServletRequest);
        if (user == null){
            return ServerResponse.createByErrorMessage(ResponseCodeEnum.NEED_LOGIN.getDesc());
        }
        return userService.resetPassowrd(passwordOld, passwordNew,user);
    }

    @RequestMapping(value = "/update_information.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> updateInformation(HttpServletRequest httpServletRequest, User user){
        User currentUser = CommonMethod.checkLoginStatus(httpServletRequest);
        if (currentUser == null){
            return ServerResponse.createByErrorMessage(ResponseCodeEnum.NEED_LOGIN.getDesc());
        }
        user.setId(currentUser.getId());
        user.setUsername(currentUser.getUsername());
        ServerResponse<User> response = userService.updateInformation(user);
        if (response.isSuccess()){
            String loginToken = CookieUtil.readLoginToken(httpServletRequest);
            RedisShardedPoolUtil.setEx(loginToken, JsonUtil.objToString(response.getData()),Const.RedisCacheExtime.REDIS_SESSION_EXTIME);
        }
        return response;
    }

    @RequestMapping(value = "/get_information.do")
    @ResponseBody
    public ServerResponse<User> getInformation(HttpServletRequest httpServletRequest){
        User currentUser = CommonMethod.checkLoginStatus(httpServletRequest);
        if (currentUser == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCodeEnum.NEED_LOGIN.getCode(),ResponseCodeEnum.NEED_LOGIN.getDesc());
        }
        return userService.getInformation(currentUser.getId());
    }


}
