package com.example.test6.user_module.login.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 用户登录返回结果封装类
 */
@Data
@ApiModel(value="SystemUser对象", description="用户表")
public class UserLoginResultVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "返回状态码:1通过，2不通过")
    int code;

    @ApiModelProperty(value = "返回信息")
    String message;
}
