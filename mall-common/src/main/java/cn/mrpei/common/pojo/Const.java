package cn.mrpei.common.pojo;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * Date:2017/11/9
 * Time:13:26
 *
 * @author 裴周宇
 */
public class Const {
    public static final String CURRENT_USER = "currentUser";

    public static final String EMAIL = "email";
    public static final String USERNAME = "username";

    public static final String TOKEN_PREFIX = "token_";

    public interface RedisCacheExtime{
        int REDIS_SESSION_EXTIME = 60 * 30; //30分钟
    }

    public interface ProductListOrderBy{
        Set<String> PRICE_ASC_DESC = Sets.newHashSet("price_desc","price_asc");
    }

    public interface Cart{
        int CHECKED = 1; //购物车中选中状态
        int UN_CHECKED = 0; //购物车中未选中状态

        String LIMIT_NUM_FAIL = "LIMIT_NUM_FAIL";
        String LIMIT_NUM_SUCCESS = "LIMIT_NUM_SUCCESS";
    }


    public interface Role{
        int ROLE_CUSTOMER = 0;//普通用户
        int ROLE_ADMIN = 1; //管理员
    }




    public interface AlipayCallback{
        String TRADE_STATUS_WAIT_BUYER_PAY = "WAIT_BUYER_PAY";
        String TRADE_STATUS_TRADE_SUCCESS = "TRADE_SUCCESS";

        String RESPONSE_SUCCESS = "success";
        String RESPONSE_FAILED = "failed";
    }


    

    //关闭订单的分布式锁
    public interface REDIS_LOCK{
        String CLOSE_ORDER_TAST_LOCK = "CLOSE_ORDER_TAST_LOCK";
    }


}
