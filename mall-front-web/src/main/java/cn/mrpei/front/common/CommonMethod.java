package cn.mrpei.front.common;



import cn.mrpei.common.utils.CookieUtil;
import cn.mrpei.common.utils.JsonUtil;
import cn.mrpei.common.utils.RedisShardedPoolUtil;
import cn.mrpei.manager.pojo.User;

import javax.servlet.http.HttpServletRequest;

/**
 * Date:2017/11/30
 * Time:22:12
 *
 * @author 裴周宇
 */
public class CommonMethod {

    public static User checkLoginStatus(HttpServletRequest httpServletRequest){
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if (loginToken == null){
            return null;
        }
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.stringToObj(userJsonStr, User.class);
        return user;
    }


}
