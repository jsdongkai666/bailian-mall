package com.cuning.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cuning.bean.user.User;
import com.cuning.constant.CommonConstant;
import com.cuning.util.MD5Util;
import com.cuning.util.RedisUtils;
import com.cuning.util.SnowFlake;
import com.cuning.mapper.UserMapper;
import com.cuning.service.UserService;
import com.cuning.vo.UserVO;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author tengjiaozhai
 * @Description TODO
 * @createTime 2022年06月09日 15:11:00
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired(required = false)
    private UserMapper userMapper;

    @Autowired
    private SnowFlake snowFlake;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

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
        if (!code.equalsIgnoreCase(user.getCode()) ||
                StringUtils.isEmpty(user.getCode()) ||
                StringUtils.isEmpty(code)){
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

    @Override
    public boolean modUserInfo(User user) {
        return userMapper.updateById(user) > 0;
    }



    @Override
    public Map<String,String> modPassword(User user, String password, String newPassword,String newPasswordAgain) {

        // 返回结果map
        Map<String,String> resultMap = new HashMap<>();

        // 根据用户id，查询该用户的密码
        User user1 = userMapper.selectById(user.getUserId());
        String userOldPassword = user1.getUserPassword();
        if (!(MD5Util.MD5Upper(password,"kgc")).equals(userOldPassword)) {
            resultMap.put("errCode","200");
            resultMap.put("errMsg","原密码不正确，请重新输入！");
            return resultMap;
        }
        String newPassMD5 = MD5Util.MD5Upper(newPassword, "kgc");
        if(newPassMD5.equals(userOldPassword)) {
            resultMap.put("errCode","200");
            resultMap.put("errMsg","新密码不能与原密码一样，请重新输入！");
            return resultMap;
        }

        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("user_password",newPassMD5).eq("user_id",user.getUserId());
        userMapper.update(user,updateWrapper);

        resultMap.put("errCode","200");
        resultMap.put("errMsg","密码修改成功！");

        return resultMap;
    }
    public boolean isChecked(User user) {

        if (user.getCheckStatus() == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean todayCheck(User user) {

        Date today = new Date();


        Date lastCheckDate = user.getLastCheckDate();
        boolean isContinuous = false;
        if (lastCheckDate != null) {
            isContinuous = dateIsContinuous(today, lastCheckDate);
        }

        if (isContinuous) {
            user.setCheckCounts(user.getCheckCounts() + 1);
        } else {
            user.setCheckCounts( 1);
        }

        user.setCheckStatus(1);
        user.setLastCheckDate(today);
        user.setUserPoints(user.getUserPoints() + user.getCheckCounts());

        boolean update = this.updateById(user);

        if (update) {
            Calendar calendar = Calendar.getInstance();
            String year = String.valueOf(calendar.get(Calendar.YEAR));
            String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
            String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
            String key = year + month + day;
            redisUtils.lSet(key,user.getUserId());
            return true;
        }

        return false;
    }

    @Override
    public boolean cronSetCheckStatus() {
        return userMapper.updateUserCheckStatus() > 0;
    }

    @Override
    public List<String> getCheckDateList(User user) {

        List<String> result = new ArrayList<>();

        Date today = new Date();
        int year = today.getYear();
        int month = today.getMonth();
        int days = getDays(year, month);


        for (int i = 1; i <= days; i++) {
            Calendar calendar = Calendar.getInstance();
            String yearstr = String.valueOf(calendar.get(Calendar.YEAR));
            String monthString = String.valueOf(calendar.get(Calendar.MONTH) + 1);
            String key = yearstr + "" + monthString + "" + i;
            System.out.println(key);
            List<Object> objects = redisUtils.lGet(key, 0, -1);
            if (objects.contains(user.getUserId())) {
                result.add(yearstr + "年" + monthString + "月" + i + "日");
            }
        }

        return result;
    }

    /**
     * @Param: [java.util.Date, java.util.Date]
     * @return: boolean
     * @Author: dengteng
     * @Date: 2022/6/11
     * @Description: 判断两个日期是否连续（同一个月内是否连续，不同月默认为不连续）
     */
    private boolean dateIsContinuous(Date today, Date lastDay) {

        // 年
        if (today.getYear() != lastDay.getYear()) {
            return false;
        }
        // 月
        if (today.getMonth() != lastDay.getMonth()) {
            return false;
        }
        // 日
        if ((today.getDay() - lastDay.getDay()) != 1) {
            return false;
        }

        return  true;
    }


   /**
   * @Param: [int, int]
   * @return: int
   * @Author: dengteng
   * @Date: 2022/6/11
   * @Description: 计算当前月有几天
   */
    public int getDays(int year, int month) {
        int days = 0;
        if (month != 2) {
            switch (month) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    days = 31;
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    days = 30;

            }
        } else {
            // 闰年
            if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0)
                days = 29;
            else
                days = 28;
        }
        return days;
    }


}
