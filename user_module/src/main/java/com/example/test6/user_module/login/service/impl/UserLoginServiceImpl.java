package com.example.test6.user_module.login.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.test6.user_module.login.mapper.SystemUserMapper;
import com.example.test6.user_module.login.mapper.SystemUserOperationLogMapper;
import com.example.test6.user_module.login.model.SystemUser;
import com.example.test6.user_module.login.model.SystemUserOperationLog;
import com.example.test6.user_module.login.service.UserLoginService;
import com.example.test6.user_module.login.vo.UserLoginResultVO;
import com.example.test6.user_module.login.vo.UserRegistResultVO;
import org.springframework.stereotype.Service;

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
    public void user_regist_test(SystemUser user) {
        SystemUser user1=new SystemUser();
        if(user1==null){
            systemUserMapper.user_regist_test(user1);
            user1.setIsDj("1");
            user1.setCreateTime(new Date());
        }
        SystemUserOperationLog operationLog = new SystemUserOperationLog();
        operationLog.setOperationTime(new Date());
        operationLog.setUsername(user.getUsername());
        operationLog.setOperationType("regist");
        String uuid = UUID.randomUUID().toString().replace("-", "");
        operationLog.setId(uuid);
        addUserLog(operationLog);

    }

    @Override
    public Integer user_CHeck_regist_test(String username) {

        if (systemUserMapper.user_CHeck_regist_test(username)==0){

            return 0;
        }

        return 1;
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
