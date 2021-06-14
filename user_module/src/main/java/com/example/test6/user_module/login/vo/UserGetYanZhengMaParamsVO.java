package com.example.test6.user_module.login.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求参数封装类
 */
@Data
@ApiModel(value="UserGetYanZhengMaParamsVO对象", description="用户获取验证码类")
public class UserGetYanZhengMaParamsVO implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "用户名")
    String username;

    @ApiModelProperty(value = "密码")
    String password;


    @ApiModelProperty(value = "邮箱地址")
    String email;

    @ApiModelProperty(value = "获取验证码")
    String yanzhengma;

}
