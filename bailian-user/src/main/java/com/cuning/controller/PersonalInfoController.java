package com.cuning.controller;

import com.cuning.bean.user.User;
import com.cuning.constant.CommonConstant;
import com.cuning.service.UserService;
import com.cuning.util.RequestResult;
import com.cuning.util.ResultBuildUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
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

    /**
     * @author : lixu
     * @date   : 2022/06/11
     * @param  : [com.cuning.bean.user.User]
     * @return : java.lang.String
     * @description : 修改个人资料
     */
    @PostMapping("/modPersonInfo")
    @ApiOperation(value = "修改个人资料",notes = "用户个人资料修改，性别，生日，昵称等")
    public RequestResult<String> modPersonInfo(@RequestBody User user){


        if (userService.modUserInfo(user)) {
            return ResultBuildUtil.success("修改成功！");
        }

        return ResultBuildUtil.fail("修改失败！");
    }

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
