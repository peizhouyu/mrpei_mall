package cn.mrpei.manager.service;


import cn.mrpei.common.pojo.ServerResponse;
import cn.mrpei.manager.vo.CartVo;

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
public interface CartService {

    ServerResponse<CartVo> add(Integer userId, Integer productId, Integer count);
    ServerResponse<CartVo> update(Integer userId, Integer productId, Integer count);
    ServerResponse<CartVo> deleteProduct(Integer userId, String productIds);
    ServerResponse<CartVo> list(Integer userId);
    ServerResponse<CartVo> selectOrUnSelectAll(Integer userId, Integer checked);
    ServerResponse<CartVo> selectOrUnSelectOne(Integer userId, Integer productId, Integer checked);
    ServerResponse<Integer> getCartProductCount(Integer userId);

}
