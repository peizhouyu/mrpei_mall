package cn.mrpei.front.filter;



import cn.mrpei.common.pojo.Const;
import cn.mrpei.common.utils.CookieUtil;

import cn.mrpei.common.utils.JsonUtil;
import cn.mrpei.common.utils.RedisPoolUtil;
import cn.mrpei.manager.pojo.User;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Date:2017/11/30
 * Time:21:38
 *    Filter 过滤器
 * @author 裴周宇
 */
public class SessionExpireFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        String loginToken = CookieUtil.readLoginToken(httpServletRequest);

        if (StringUtils.isNotEmpty(loginToken)){
            String userJsonStr = RedisPoolUtil.get(loginToken);
            User user = JsonUtil.stringToObj(userJsonStr,User.class);
            if (user != null){
                // 如果user不为空，则重置session的时间，即调用expire命令
                RedisPoolUtil.expire(loginToken, Const.RedisCacheExtime.REDIS_SESSION_EXTIME);
            }
        }

        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
