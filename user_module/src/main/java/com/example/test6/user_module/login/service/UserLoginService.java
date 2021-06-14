package com.example.test6.user_module.login.service;

import com.example.test6.user_module.login.model.SystemUser;
import com.example.test6.user_module.login.vo.*;

public interface UserLoginService {

    boolean selectByuser(SystemUser user);

    /**
     * 用户登录验证接口
     * @param user
     * @return
     */
    UserLoginResultVO user_login_test(SystemUser user);

    /**
     * @Author wpp
     * @Description //TODO 用户注册接口
     * @Date 15:09 2021/5/23
     * @Param [user]
     * @return com.example.test6.user_module.login.vo.UserLoginResultVO
     **/
    UserRegistResultVO user_regist_test(UserRegistParamsVO user);
/**
 * @Author xiayongbin
 * @Description //TODO 获取验证码接口
 * @Date 21:28 2021/6/13
 * @Param [systemUser]
 * @return java.lang.Integer
 **/

UserGetYanZhengMaResultVO user_get_yanzhengma_test  (UserGetYanZhengMaParamsVO userGetYanZhengMaParamsVO);



}
