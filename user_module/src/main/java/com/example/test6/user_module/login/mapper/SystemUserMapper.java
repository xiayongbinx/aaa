package com.example.test6.user_module.login.mapper;

import com.example.test6.user_module.login.model.SystemUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.test6.user_module.login.vo.UserLoginResultVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* <p>
* 用户表 Mapper 接口
* </p>
*
* @author duyu
* @since 2021-05-23
*/
@Mapper
public interface SystemUserMapper extends BaseMapper<SystemUser> {


    SystemUser user_login_test(SystemUser systemUser);
    /**
     * @Author wpp
     * @Description //TODO 用户注册接口
     * @Date 15:09 2021/5/23
     * @Param [user]
     * @return com.example.test6.user_module.login.vo.UserLoginResultVO
     **/
    SystemUser user_regist_test(SystemUser user);
     Integer  user_CHeck_regist_test  (@Param("username") String username,@Param("password") String password,@Param("emial") String emial);



}