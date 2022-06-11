package com.cuning.controller;

import com.cuning.bean.user.User;
import com.cuning.service.UserService;
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
    public String modPersonInfo(@RequestBody User user){
        if (userService.modUserInfo(user)) {
            return "修改成功！";
        }
        return "修改失败！";
    }

    @PostMapping("/modPwd")
    public Map<String,String> modPassword(@RequestBody User user, @RequestParam String password, @RequestParam String newPassword,@RequestParam String newPasswordAgain) {
        // 返回结果map
        Map<String,String> resultMap = new HashMap<>();

        if (StringUtils.isEmpty(password) || StringUtils.isEmpty(newPassword) || StringUtils.isEmpty(newPasswordAgain)) {
            resultMap.put("errCode","200");
            resultMap.put("errMsg","密码不能为空！");
            return resultMap;
        }

        if (!newPasswordAgain.equals(newPassword)) {
            resultMap.put("errCode","200");
            resultMap.put("errMsg","两次输入的新密码不一致！");
            return resultMap;
        }
        resultMap = userService.modPassword(user, password, newPassword,newPasswordAgain);

        return resultMap;
    }
}
