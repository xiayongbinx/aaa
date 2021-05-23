package com.example.test6.user_module.login.service;

import com.example.test6.user_module.login.model.SystemUser;
import com.example.test6.user_module.login.vo.UserLoginResultVO;
import com.example.test6.user_module.login.vo.UserRegistResultVO;

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
void user_regist_test(SystemUser user);

  Integer  user_CHeck_regist_test (String username);



}
