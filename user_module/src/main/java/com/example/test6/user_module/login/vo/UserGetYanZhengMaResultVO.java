package com.example.test6.user_module.login.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册返回结果封装类
 */
@Data
@ApiModel(value="UserRegistResultVO对象", description="用户注册返回结果封装类")
public class UserGetYanZhengMaResultVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "返回状态码:0用户不存在，1注册成功！，2邮箱地址与注册邮箱不符！")
    int code;

    @ApiModelProperty(value = "返回信息")
    String message;

}
