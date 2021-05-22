package com.example.test6.user_module.login.service;

import com.example.test6.user_module.login.model.SystemUser;
import com.example.test6.user_module.login.vo.UserLoginResultVO;

public interface UserLoginService {

    boolean selectByuser(SystemUser user);

    /**
     * 用户登录验证接口
     * @param user
     * @return
     */
    UserLoginResultVO user_login_test(SystemUser user);



}
