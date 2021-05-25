package com.example.test6.user_module.login.model;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author duyu
 * @since 2021-05-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("system_user_operation_log")
@ApiModel(value="SystemUserOperationLog对象", description="")
public class SystemUserOperationLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId("id")
    private String id;

    @ApiModelProperty(value = "用户名")
    @TableField("username")
    private String username;

    @ApiModelProperty(value = "操作时间")
    @TableField("operation_time")
    private Date operationTime;

    @ApiModelProperty(value = "操作类型(login:登录，login_out:退出登录,user_dj:冻结用户)")
    @TableField("operation_type")
    private String operationType;

    @ApiModelProperty(value = "0:失败1:成功")
    @TableField("operation_result")
    private String operationResult;

    @ApiModelProperty(value = "失败次数")
    @TableField("errorNum")
    private Integer errorNum;


}
