package com.cuning.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cuning.bean.user.User;
import com.cuning.vo.UserVO;

import java.util.Map;

/**
 * @author tengjiaozhai
 * @Description TODO
 * @createTime 2022年06月09日 15:11:00
 */
public interface UserService extends IService<User> {


    User executeRegister(String userName, String userPassword);

    Map<String,Object> executeLogin(UserVO user);

    Map<String, Object> executeTelLogin(String tel, String captcha);

    /**
    * @Param: [java.lang.String]
    * @return: boolean
    * @Author: dengteng
    * @Date: 2022/6/11
    * @Description: 判断用户今日是否已经签到
    */
    boolean isChecked(String userId);

    /**
    * @Param: [java.lang.String]
    * @return: com.cuning.bean.user.User
    * @Author: dengteng
    * @Date: 2022/6/11
    * @Description: 今日签到
    */
    User todayCheck(String userId);

    /**
    * @Param: []
    * @return: void
    * @Author: dengteng
    * @Date: 2022/6/11
    * @Description:  定时任务设置用户签到状态为未签到
    */
    boolean cronSetCheckStatus();

}
