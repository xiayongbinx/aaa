package com.example.test6.user_module.login.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册返回结果封装类
 */
@Data
@ApiModel(value="SystemUser对象", description="用户表")
public class UserRegistResultVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "返回状态码:0该用户已经存在或者账号被冻结，1欢迎注册")
    int code;

    @ApiModelProperty(value = "返回信息")
    String message;

    @ApiModelProperty(value = "注册状态：0:失败1:成功")
    String state;
}
