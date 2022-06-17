package com.cuning.controller;

import com.cuning.bean.user.User;
import com.cuning.constant.CommonConstant;
import com.cuning.service.UserService;
import com.cuning.util.RequestResult;
import com.cuning.util.ResultBuildUtil;
import com.cuning.util.SnowFlake;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created On : 2022/06/11.
 * <p>
 * Author     : lixu
 * <p>
 * Description: 个人信息操作入口
 */
@Slf4j
@RestController
@Api(tags = "个人信息操作入口")
public class PersonalInfoController {

    @Autowired
    private UserService userService;

    @Autowired
    private SnowFlake snowFlake;

    /**
     * @author : lixu
     * @date   : 2022/06/16
     * @param  : [java.lang.String]
     * @return : com.cuning.util.RequestResult<com.cuning.bean.user.User>
     * @description : 查看个人资料
     */
    @GetMapping("/queryPersonInfo")
    @ApiOperation(value = "查看个人资料",notes = "用户个人资料展示")
    public User queryPersonInfo(@RequestParam("userId") String userId){

        User user = userService.selectPersonInfoByUserId(userId);
        user.setUserPassword(null);
        return user;

    }

    /**
     * @author : lixu
     * @date   : 2022/06/11
     * @param  : [com.cuning.bean.user.User]
     * @return : java.lang.String
     * @description : 修改个人资料
     */
    @PostMapping("/modPersonInfo")
    @ApiOperation(value = "修改个人资料",notes = "用户个人资料修改，性别，生日，昵称等")
    public boolean modPersonInfo(@RequestBody User user){

        if (userService.updatePersonInfo(user)) {
            return true;
        }

        return false;
    }

    /**
     * @author : lixu
     * @date   : 2022/06/15
     * @param  : [com.cuning.bean.user.User, java.lang.String, java.lang.String, java.lang.String]
     * @return : com.cuning.util.RequestResult<java.lang.String>
     * @description : 修改密码
     */
    @PostMapping("/modPwd")
    @ApiOperation(value = "修改密码",notes = "用户修改密码，必须有旧密码，新密码和确认新密码")
    public RequestResult<String> modPassword(@RequestBody User user,
                                                         @RequestParam("password") String password,
                                                         @RequestParam("newPassword") String newPassword,
                                                         @RequestParam("newPasswordAgain") String newPasswordAgain) {


        if (!newPasswordAgain.equals(newPassword)) {
            return ResultBuildUtil.fail("两次输入的新密码不一致！");
        }

        return userService.modPassword(user, password, newPassword, newPasswordAgain);
    }

}
