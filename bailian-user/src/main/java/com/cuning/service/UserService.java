package com.cuning.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cuning.bean.user.User;
import com.cuning.util.RequestResult;
import com.cuning.vo.UserVO;

import java.util.List;
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

    User selectPersonInfoByUserId(String userId);

    /**
     * @author : lixu
     * @date   : 2022/06/15
     * @param  : [com.cuning.bean.user.User]
     * @return : boolean
     * @description : 修改用户信息
     */
    boolean updatePersonInfo(User user);


    /**
     * @author : lixu
     * @date   : 2022/06/15
     * @param  : [com.cuning.bean.user.User, java.lang.String, java.lang.String, java.lang.String]
     * @return : com.cuning.util.RequestResult<java.lang.String>
     * @description : 修改密码
     */
    RequestResult<String> modPassword(User user, String password, String newPassword, String newPasswordAgain);
    /**
    * @Param: [java.lang.String]
    * @return: boolean
    * @Author: dengteng
    * @Date: 2022/6/11
    * @Description: 判断用户今日是否已经签到
    */
    boolean isChecked(User user);

    /**
    * @Param: [java.lang.String]
    * @return: com.cuning.bean.user.User
    * @Author: dengteng
    * @Date: 2022/6/11
    * @Description: 今日签到
    */
    boolean todayCheck(User user);

    /**
    * @Param: []
    * @return: void
    * @Author: dengteng
    * @Date: 2022/6/11
    * @Description:  定时任务设置用户签到状态为未签到
    */
    boolean cronSetCheckStatus();

    /**
    * @Param: [com.cuning.bean.user.User]
    * @return: java.util.List<java.lang.String>
    * @Author: dengteng
    * @Date: 2022/6/11
    * @Description: 获取用户当月签到记录
    */
    List<String > getCheckDateList(User user);




}
