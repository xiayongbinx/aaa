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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
        //查询用户是否已存在
        QueryWrapper<SystemUser> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username",user.getUsername());
        SystemUser select_user = systemUserMapper.selectOne(userQueryWrapper);
        if (select_user==null){
            resultVO.setCode(0);
            resultVO.setMessage("用户不存在！");
            return resultVO;
        }else if (select_user.getIsDj().equals("0")){
            resultVO.setCode(2);
            resultVO.setMessage("用户已被冻结，请联系管理员解冻！");
            return resultVO;
        }
        //初始化日志封装参数类
        SystemUserOperationLog operationLog = new SystemUserOperationLog();

        SystemUser systemUser = systemUserMapper.user_login_test(user);
        if (systemUser==null){
            resultVO.setCode(0);
            resultVO.setMessage("账号或密码错误！");
            //获取用户一小时之内登录日志
            Date date = new Date();//获取当前时间
            Calendar calendar = Calendar.getInstance(); //创建Calendar 的实例
//        calendar.add(Calendar.YEAR, -1);//当前时间减去一年，即一年前的时间
//        calendar.add(Calendar.MONTH, -1);//当前时间减去一个月，即一个月前的时间
//        calendar.add(Calendar.DAY_OF_MONTH,-1); //当前时间减去一天，即一天前的时间
            calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) - 1);// 让日期加1
//        System.out.println(calendar.get(Calendar.DATE));// 加1之后的日期Top
//        calendar.getTimeInMillis();//返回当前时间的毫秒数
            QueryWrapper<SystemUserOperationLog> logQueryWrapper = new QueryWrapper<>();
            logQueryWrapper.eq("username",user.getUsername());
            logQueryWrapper.eq("operation_type","login");
            logQueryWrapper.orderByDesc("operation_time");
            logQueryWrapper.gt("operation_time",calendar.getTime());
            logQueryWrapper.lt("operation_time",date);
            List<SystemUserOperationLog> userOperationLogs = systemUserOperationLogMapper.selectList(logQueryWrapper);
            if (userOperationLogs.size()>0){
                //获取用户一小时之内最新一条登录日志
                SystemUserOperationLog this_operationLog = userOperationLogs.get(0);
                String operationResult = this_operationLog.getOperationResult();
                Integer errorNum = this_operationLog.getErrorNum();
                if (null!=operationResult && "0".equals(operationResult)){
                    //本次登陆账号密码有误，一小时之内次数达到5次
                    errorNum+= 1;
                    if (errorNum==5){
                        //更新用户状态为冻结
                        SystemUser user1 = new SystemUser();
                        user1.setId(select_user.getId());
                        user1.setIsDj("0");
                        systemUserMapper.updateById(user1);
                        resultVO.setCode(2);
                        resultVO.setMessage("用户已被冻结，请联系管理员解冻！");
                    }
                }
                operationLog.setErrorNum(errorNum);
                operationLog.setOperationResult("0");
            }else {
                operationLog.setErrorNum(1);
                operationLog.setOperationResult("0");
            }
        }else {
            resultVO.setCode(1);
            resultVO.setMessage("登陆成功");
            operationLog.setOperationResult("1");
            operationLog.setErrorNum(0);
        }
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
