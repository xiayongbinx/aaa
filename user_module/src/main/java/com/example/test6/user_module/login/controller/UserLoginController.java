package com.example.test6.user_module.login.controller;


import com.example.test6.user_module.login.constant.UserLoginConst;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@RequestMapping(UserLoginConst.HOME)
@Controller
public class UserLoginController {


    @RequestMapping(UserLoginConst.USER_MODULE_LOGIN_MODULE_LOGIN)
    @ResponseBody
    public void login(){
        System.out.println("111111111111111");
    }


}
