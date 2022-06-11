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

    @Override
    public Map<String, Object> executeTelLogin(String tel, String captcha) {
        HashMap<String, Object> resultMap = new HashMap<>();
        String openid = (String)redisUtils.get("openid");
        // 获取手机验证码
        String telCaptcha =(String) redisUtils.get("telCaptcha");
        // openid存在 微信用户绑定手机号
        // 为空 单纯手机号登录

        // 把手机和验证码绑定起来
        redisUtils.set(telCaptcha,tel,60);

        if (StringUtils.isEmpty(openid)){
            if (captcha.equalsIgnoreCase(telCaptcha)){
                // 验证码正确
                // 同时验证码与手机号对应才满足条件
               if (tel.equals(redisUtils.get(telCaptcha))){
                   LambdaQueryWrapper<User> qw = new LambdaQueryWrapper<>();
                   // 查询是否存在tel 一个tel对应一个用户
                   qw.eq(User::getUserTel,tel);
                   User one = userMapper.selectOne(qw);
                   // 不存在这个手机 插入用户
                   if (one == null){
                       User insert = User.builder().userId(Long.toString(snowFlake.nextId())).userTel(tel).build();
                       int row = userMapper.insert(insert);
                       // 插入成功
                       if (row > 0){
                           log.info("插入成功");
                           resultMap.put(CommonConstant.UNIFY_RETURN_SUCCESS_CODE,insert);
                       }else {
                           log.info("插入失败");
                           resultMap.put(CommonConstant.UNIFY_RETURN_FAIL_CODE,"登录失败");
                       }

                   }
                   // 存在直接返回

                  else{
                       resultMap.put(CommonConstant.UNIFY_RETURN_SUCCESS_CODE,one);
                   }
               }
               // 不满足则手机号不正确
                else {
                   log.info("手机号和验证码不匹配，请重试");
                   resultMap.put(CommonConstant.UNIFY_RETURN_FAIL_CODE,"手机号和验证码不匹配，请重试！");
               }
            }else {
                log.info("验证码不正确，请重新输入");
                resultMap.put(CommonConstant.UNIFY_RETURN_FAIL_CODE,"验证码不正确，请重新输入");
            }

        }
        // 微信用户绑定手机号
        // 通过openid查询账号
        else {
            LambdaQueryWrapper<User> qw = new LambdaQueryWrapper<>();
            qw.eq(User::getUserOpenid,openid);
            // 微信号对应账户
            User existedUser = userMapper.selectOne(qw);

            LambdaQueryWrapper<User> qw1 = new LambdaQueryWrapper<>();
            // 查询是否存在tel 一个tel对应一个用户
            // 微信一个用户 手机一个用户 在手机号相同的情况下
            // 所以添加后面条件来过滤微信
            qw1.eq(User::getUserTel,tel).eq(User::getUserOpenid,null);
            // 手机号对应账户
            User one = userMapper.selectOne(qw1);
            // 判断验证码
            if (captcha.equalsIgnoreCase(telCaptcha)){
                // 同时验证码与手机号对应才满足条件
                if (tel.equals(redisUtils.get(telCaptcha))){
                    // 手机号不存在时可以插入
//                    if (ObjectUtils.isEmpty(one)){

                        // 判断此手机号之前是否存在 存在插入微信信息
                        if (!ObjectUtils.isEmpty(one)){
                            // 手机号已经存在 把微信信息更新进去
                            one.setUserName(existedUser.getUserName());
                            one.setUserHeadImg(existedUser.getUserHeadImg());
                            one.setUserOpenid(existedUser.getUserOpenid());
                            userMapper.updateById(one);
                            // 把微信信息和手机信息整合 删除微信信息
                            userMapper.deleteById(existedUser.getUserId());
                            log.info("微信用户-更新用户:{}",one);
                            resultMap.put(CommonConstant.UNIFY_RETURN_SUCCESS_CODE,existedUser);
                        }
                        // 判断微信用户之前是否绑定过此手机号(其他手机号 手机号为空满足条件) ||
                       else if (ObjectUtils.isEmpty(existedUser.getUserTel()) || !existedUser.getUserTel().equals(tel)){
                            // 没有绑定此手机号
                            existedUser.setUserTel(tel);
                            // 更新用户
                            int i = userMapper.updateUserById(existedUser);
                            log.info("微信用户-更新用户:{}",existedUser);
                            resultMap.put(CommonConstant.UNIFY_RETURN_SUCCESS_CODE,existedUser);
                        }
//                    }
//                    else {
//                        // 手机号已经存在 把微信信息更新进去
//                        one.setUserName(existedUser.getUserName());
//                        one.setUserHeadImg(existedUser.getUserHeadImg());
//                        one.setUserOpenid(existedUser.getUserOpenid());
//                        userMapper.updateById(one);
//                        // 把微信信息和手机信息整合 删除微信信息
//                        userMapper.deleteById(existedUser.getUserId());
//                        log.info("微信用户-更新用户:{}",one);
//                    }

                    log.info("微信用户-登录成功，无需更新");

                }
                // 不满足则手机号不正确
                else {
                    log.info("手机号和验证码不匹配，请重试");
                    resultMap.put(CommonConstant.UNIFY_RETURN_FAIL_CODE,"手机号和验证码不匹配，请重试！");
                }

            }else {
                log.info("微信用户-验证码不正确，请重新输入 ");
                resultMap.put(CommonConstant.UNIFY_RETURN_FAIL_CODE,"验证码不正确，请重新输入");
            }
            // 释放手机号和验证码对应
            redisUtils.del(telCaptcha);
            // 释放openid
            redisUtils.del("openid");
        }

        return resultMap;
    }
}
