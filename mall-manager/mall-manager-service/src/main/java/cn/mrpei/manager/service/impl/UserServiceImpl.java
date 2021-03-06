package cn.mrpei.manager.service.impl;


import cn.mrpei.common.pojo.Const;
import cn.mrpei.common.pojo.ServerResponse;
import cn.mrpei.common.utils.MD5Util;
import cn.mrpei.common.utils.RedisPoolUtil;
import cn.mrpei.manager.dao.UserMapper;
import cn.mrpei.manager.pojo.User;
import cn.mrpei.manager.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public ServerResponse<User> login(String username, String password) {
        int resultCount = userMapper.checkUsername(username);
        if (resultCount == 0){
            return ServerResponse.createByErrorMessage("用户名不存在");
        }
        //TODO 密码登录 MD5
        String md5Passwword = MD5Util.MD5EncodeUtf8(password);
        User user = userMapper.selectLogin(username, md5Passwword);
        if (user == null){
            return ServerResponse.createByErrorMessage("密码错误");
        }
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess("登录成功",user);
    }

    @Override
    public ServerResponse<String> register(User user) {
        ServerResponse validResponse = this.checkValid(user.getUsername(),Const.USERNAME);
        if (!validResponse.isSuccess()){
            return validResponse;
        }
       validResponse = this.checkValid(user.getEmail(),Const.EMAIL);
        if (!validResponse.isSuccess()){
            return validResponse;
        }
        user.setRole(Const.Role.ROLE_CUSTOMER);
        //MD5 加密
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
        int resultCount = userMapper.insert(user);
        if (resultCount == 0) {
            return ServerResponse.createByErrorMessage("注册失败");
        }
        return ServerResponse.createBySuccessMessage("注册成功");
    }

    public ServerResponse<String> checkValid(String str, String type){
        if (StringUtils.isNoneBlank(type)){
            //开始校验
            if(Const.USERNAME.equals(type)){
                int resultCount = userMapper.checkUsername(str);
                if (resultCount > 0){
                    return ServerResponse.createByErrorMessage("用户名已存在");
                }
            }
            if (Const.EMAIL.equals(type)){
                int resultCount = userMapper.checkEmail(str);
                if (resultCount > 0){
                    return ServerResponse.createByErrorMessage("邮箱已存在");
                }
            }
        }else {
            return ServerResponse.createByErrorMessage("参数错误");
        }

        return ServerResponse.createBySuccessMessage("校验成功");
    }

    public ServerResponse selectQuestion(String username){
        ServerResponse validResponse = this.checkValid(username, Const.USERNAME);
        if (validResponse.isSuccess()){
            //用户不存在
            return ServerResponse.createByErrorMessage("用户不存在");
        }
        String question = userMapper.selectQuestionByUsername(username);
        if (StringUtils.isNoneBlank(question)){
            return ServerResponse.createBySuccess(question);
        }
        return ServerResponse.createByErrorMessage("密码问题为空");
    }

    public ServerResponse<String> checkAnswer(String username, String question, String answer){
        int resultCount = userMapper.checkAnswer(username,question,answer);
        if (resultCount > 0){
            //问题及问题答案属于该用户 并且正确
            String forgetToken = UUID.randomUUID().toString();
            //TokenCache.setKey(TokenCache.TOKEN_PREFIX + username,forgetToken);
            //本地cache 改为 Redis cache
            RedisPoolUtil.setEx(Const.TOKEN_PREFIX + username,forgetToken,60*60*12);
            return ServerResponse.createBySuccess(forgetToken);
        }
        return ServerResponse.createByErrorMessage("问题的答案错误");
    }

    public ServerResponse<String> forgetRestPassword(String username, String passwordNew, String forgetToken){
        if (StringUtils.isBlank(forgetToken)){
            return ServerResponse.createByErrorMessage("token为 null");
        }
        ServerResponse validResponse = this.checkValid(username, Const.USERNAME);
        if (validResponse.isSuccess()){
            //用户不存在
            return ServerResponse.createByErrorMessage("用户不存在");
        }
        //String token = TokenCache.getKey(TokenCache.TOKEN_PREFIX + username);
        //本地cache 改为 Redis cache
        String token = RedisPoolUtil.get(Const.TOKEN_PREFIX + username);
        if (StringUtils.isBlank(token)){
            return ServerResponse.createByErrorMessage("token 无效或过期");
        }
        if (StringUtils.equals(forgetToken,token)){
            String md5Password = MD5Util.MD5EncodeUtf8(passwordNew);
            int rowCount = userMapper.updatePasswordByUsername(username,md5Password);

            if (rowCount > 0){
                return ServerResponse.createBySuccessMessage("修改密码成功");
            }
        }else {
            return ServerResponse.createByErrorMessage("token错误请重新获取");
        }
        return ServerResponse.createByErrorMessage("修改密码失败");
    }

    public ServerResponse<String> resetPassowrd(String passwordOld, String passwordNew, User user){
        int resultCount = userMapper.checkPassword(MD5Util.MD5EncodeUtf8(passwordOld), user.getId());
        if (resultCount == 0){
            return ServerResponse.createByErrorMessage("旧密码错误");
        }
        user.setPassword(MD5Util.MD5EncodeUtf8(passwordNew));
        int updateCount = userMapper.updateByPrimaryKeySelective(user);
        if (updateCount > 0){
            return ServerResponse.createBySuccessMessage("密码更新成功");
        }
        return ServerResponse.createByErrorMessage("密码更新失败");
    }

    public ServerResponse<User> updateInformation(User user){
        int resultCount = userMapper.checkEmailByUserId(user.getEmail(),user.getId());
        if (resultCount > 0){
            return ServerResponse.createByErrorMessage("email已被占用");
        }
        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setEmail(user.getEmail());
        updateUser.setPhone(user.getPhone());
        updateUser.setQuestion(user.getQuestion());
        updateUser.setAnswer(user.getAnswer());

        int updateCount = userMapper.updateByPrimaryKeySelective(updateUser);
        if (updateCount > 0){
            return ServerResponse.createBySuccess("更新成功",updateUser);
        }

        return ServerResponse.createByErrorMessage("更新个人信息失败");
    }

    public ServerResponse<User> getInformation(Integer userId){
        User user = userMapper.selectByPrimaryKey(userId);
        if (user == null){
            return ServerResponse.createByErrorMessage("找不到当前用户");
        }
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess(user);
    }

    //backend

    /**
     * 校验是否为管理员
     * @param user
     * @return
     */
    public ServerResponse checkAdminRole(User user){
        if (user != null && user.getRole().intValue() == Const.Role.ROLE_ADMIN){
            return ServerResponse.createBySuccess();
        }else {
            return ServerResponse.createByError();
        }
    }

    @Override
    public Long getUserCount() {
        return userMapper.countUser();
    }

    @Override
    public ServerResponse getUserList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<User> userList = userMapper.selectList();
        for (User user : userList){
            user.setPassword(null);
        }
        PageInfo pageInfo = new PageInfo(userList);
        return ServerResponse.createBySuccess(pageInfo);
    }
}
