package com.example.test6.user_module.login.controller;


import com.example.test6.user_module.login.constant.UserLoginConst;
import com.example.test6.user_module.login.model.SystemUser;
import com.example.test6.user_module.login.service.UserLoginService;
import com.example.test6.user_module.login.vo.UserLoginResultVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;


@RequestMapping(UserLoginConst.HOME)
@Controller
public class UserLoginController {

    @Resource
    UserLoginService userLoginService;


    @RequestMapping(UserLoginConst.USER_MODULE_LOGIN_MODULE_LOGIN)
    @ResponseBody
    public UserLoginResultVO login(String username,String password){
        SystemUser systemUser = new SystemUser();
        systemUser.setUsername(username);
        systemUser.setPassword(password);
        boolean exce = userLoginService.selectByuser(systemUser);
        UserLoginResultVO resultVO = new UserLoginResultVO();
        if (exce){
            resultVO.setCode(1);
            resultVO.setMessage("登陆成功！");
        }else {
            resultVO.setCode(0);
            resultVO.setMessage("登陆失败！");
        }
        return resultVO;
    }

    @RequestMapping(UserLoginConst.USER_MODULE_LOGIN_MODULE_LOGIN2)
    @ResponseBody
    public UserLoginResultVO login2(String username,String password){
        SystemUser systemUser = new SystemUser();
        systemUser.setUsername(username);
        systemUser.setPassword(password);
        UserLoginResultVO resultVO = userLoginService.user_login_test(systemUser);
        return resultVO;
    }


}
