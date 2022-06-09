package com.cuning.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cuning.bean.user.User;
import com.cuning.constant.CommonConstant;
import com.cuning.util.MD5Util;
import com.cuning.util.RedisUtils;
import com.cuning.util.SnowFlake;
import com.cuning.mapper.UserMapper;
import com.cuning.service.UserService;
import com.cuning.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tengjiaozhai
 * @Description TODO
 * @createTime 2022年06月09日 15:11:00
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SnowFlake snowFlake;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public User executeRegister(String userName, String userPassword) {
        // 查看是否注册过(用户名)
        LambdaQueryWrapper<User> qw = new LambdaQueryWrapper<>();
        qw.eq(User::getUserName,userName);
        User condition = userMapper.selectOne(qw);
        if (!ObjectUtils.isEmpty(condition)){
            log.info("--- 用户已存在 ---");
            return null;
        }
        User result = new User();
        // 生成ID
        result.setUserId(Long.toString(snowFlake.nextId()));
        // 设置用户名
        result.setUserName(userName);
        result.setUserPassword(MD5Util.MD5Upper(userPassword,"kgc"));
        // 插入用户
        userMapper.insert(result);
        return result;
    }

    @Override
    public Map<String,Object> executeLogin(UserVO user) {
        HashMap<String, Object> resultMap = new HashMap<>();
        // 从redis获取验证码
        String code = (String)redisUtils.get("captcha");
        // 验证码错误
        if (!code.equalsIgnoreCase(user.getCode()) || StringUtils.isEmpty(user.getCode())){
            resultMap.put(CommonConstant.UNIFY_RETURN_FAIL_CODE,"验证码不正确，请重新输入！");
            log.info("验证码不正确");
            return  resultMap;
        }

        // 登录校验
        LambdaQueryWrapper<User> qw = new LambdaQueryWrapper<>();
        qw.eq(User::getUserName,user.getUserName());
        User result = userMapper.selectOne(qw);
        String valid = MD5Util.MD5Upper(user.getUserPassword(), "kgc");
        // 结果为空或者密码不一样判读为用户名或者密码不正确
        if (result == null || !valid.equalsIgnoreCase(result.getUserPassword())){
            resultMap.put(CommonConstant.UNIFY_RETURN_FAIL_CODE,"用户名或者密码不正确");
            log.info("用户名或者密码不正确");
            return  resultMap;
        }
        resultMap.put(CommonConstant.UNIFY_RETURN_SUCCESS_CODE,result);
        log.info("登录成功");
        return resultMap;
    }
}
