package com.example.test6.user_module.login.model;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author duyu
 * @since 2021-05-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("system_user")
@ApiModel(value="SystemUser对象", description="用户表")
public class SystemUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId("id")
    private String id;

    @ApiModelProperty(value = "用户名称")
    @TableField("username")
    private String username;

    @ApiModelProperty(value = "密码")
    @TableField("password")
    private String password;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "是否冻结（登录失败5次后冻结）0冻结，1正常")
    @TableField("is_dj")
    private String isDj;

    @ApiModelProperty(value = "冻结时间")
    @TableField("dj_time")
    private Date djTime;

    @ApiModelProperty(value = "解除冻结时间")
    @TableField("jcdj_time")
    private Date jcdjTime;

    @ApiModelProperty(value = "邮箱")
    @TableField("email")
    private String email;


}
