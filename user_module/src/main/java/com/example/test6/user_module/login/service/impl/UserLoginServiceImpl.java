package com.example.test6.user_module.login.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.example.test6.user_module.login.mapper.SystemUserMapper;
import com.example.test6.user_module.login.mapper.SystemUserOperationLogMapper;
import com.example.test6.user_module.login.model.SystemUser;
import com.example.test6.user_module.login.model.SystemUserOperationLog;
import com.example.test6.user_module.login.service.UserLoginService;
import com.example.test6.user_module.login.vo.UserLoginResultVO;
import com.example.test6.user_module.login.vo.UserRegistParamsVO;
import com.example.test6.user_module.login.vo.UserRegistResultVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;


@Service
public class UserLoginServiceImpl implements UserLoginService {

    @Resource
    SystemUserMapper systemUserMapper;

    @Resource
    SystemUserOperationLogMapper systemUserOperationLogMapper;

    @Override
    public boolean selectByuser(SystemUser user) {
        QueryWrapper<SystemUser> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username",user.getUsername());
        userQueryWrapper.eq("password",user.getPassword());
        SystemUser systemUser = systemUserMapper.selectOne(userQueryWrapper);
        if (systemUser!=null){
            return true;
        }
        return false;
    }

    @Override
    public UserLoginResultVO user_login_test(SystemUser user) {
        UserLoginResultVO resultVO = new UserLoginResultVO();
        SystemUser systemUser = systemUserMapper.user_login_test(user);
        if (systemUser==null){
            resultVO.setCode(0);
            resultVO.setMessage("账号或密码错误！");
        }else {
            String isDj = systemUser.getIsDj();
            resultVO.setCode(1);
            resultVO.setMessage("登陆成功");
            if (isDj.equals("1")){
                resultVO.setCode(2);
                resultVO.setMessage("用户已被冻结，请联系管理员解冻！");
            }
        }
        SystemUserOperationLog operationLog = new SystemUserOperationLog();
        operationLog.setOperationTime(new Date());
        operationLog.setUsername(user.getUsername());
        operationLog.setOperationType("login");
        String uuid = UUID.randomUUID().toString().replace("-", "");
        operationLog.setId(uuid);
        addUserLog(operationLog);
        return resultVO;
    }

    @Override
    @Transactional
    public UserRegistResultVO user_regist_test(UserRegistParamsVO user) {
        //结果封装类初始化
        UserRegistResultVO resultVO = new UserRegistResultVO();
        //查询用户是否已存在
        QueryWrapper<SystemUser> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username",user.getUsername());
        SystemUser select_user = systemUserMapper.selectOne(userQueryWrapper);
//        Integer test = systemUserMapper.user_CHeck_regist_test(user);
        if (select_user!=null){
            //返回结果封装
            resultVO.setCode(0);
            resultVO.setMessage("用户已存在！");
            return resultVO;
        }
        //新建一条用户记录
        SystemUser systemUser = new SystemUser();
        systemUser.setEmail(user.getEmail());
        systemUser.setUsername(user.getUsername());
        systemUser.setPassword(user.getPassword());
        systemUser.setIsDj("1");
        systemUser.setCreateTime(new Date());
//        systemUserMapper.user_regist_test(systemUser);
        systemUserMapper.insert(systemUser);

        //添加用户操作日志
        SystemUserOperationLog operationLog = new SystemUserOperationLog();
        operationLog.setOperationTime(new Date());
        operationLog.setUsername(user.getUsername());
        operationLog.setOperationType("regist");
        String uuid = UUID.randomUUID().toString().replace("-", "");
        operationLog.setId(uuid);
        addUserLog(operationLog);
        //返回结果封装
        resultVO.setCode(1);
        resultVO.setMessage("注册成功！");
        return resultVO;
    }


    /**
     * @Author Administrator
     * @Description //TODO 用户进行操作时记录日志
     * @Date 2:03 2021/5/23
     * @Param [log]
     * @return void
     **/
    private void addUserLog(SystemUserOperationLog log){
        systemUserOperationLogMapper.insert(log);
    }
}
