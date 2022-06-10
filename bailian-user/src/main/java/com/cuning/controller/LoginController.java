package com.cuning.controller;

import com.cuning.bean.user.User;
import com.cuning.constant.CommonConstant;
import com.cuning.service.UserService;
import com.cuning.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tengjiaozhai
 * @Description TODO
 * @createTime 2022年06月09日 14:58:00
 */
@RequestMapping("/user")
@RestController
@Slf4j
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     * @description 注册用户
     * @author  tengjiaozhai
     * @updateTime 2022/6/9 15:14 No such property: code for class: Script1
     */
    @GetMapping("/register")
    @ApiOperation("用户注册")
    public Map<String, Object> RegisterUser(@RequestParam String userName,@RequestParam String userPassword){

        HashMap<String, Object> resultMap = new HashMap<>();
        User result = userService.executeRegister(userName,userPassword);
        // 判断是否插入成功
        if (result != null){
            result.setUserPassword(null);
            resultMap.put(CommonConstant.UNIFY_RETURN_SUCCESS_MSG,result);
            return resultMap;
        }
        resultMap.put(CommonConstant.UNIFY_RETURN_FAIL_MSG,"用户已经存在");
        return  resultMap;
    }

    @GetMapping("/login")
    @ApiOperation("用户登录")
    // @RequestParam String userName,@RequestParam String userPassword,@RequestParam String captcha
    // @RequestBody UserVO userVO
    public Map<String, Object> LoginUser(@RequestParam String userName,@RequestParam String userPassword,@RequestParam String captcha){
        HashMap<String, Object> resultMap = new HashMap<>();
        UserVO userVO = new UserVO();
        UserVO build = userVO.builder().userName(userName).userPassword(userPassword).code(captcha).build();
        Map<String, Object> resultSets = userService.executeLogin(build);
        // 判断是否插入成功
        Object value = resultSets.get(CommonConstant.UNIFY_RETURN_FAIL_CODE);
        if (value != null){
            resultMap.put(CommonConstant.UNIFY_RETURN_FAIL_MSG,value);
            return resultMap;
        }
        resultMap.put(CommonConstant.UNIFY_RETURN_SUCCESS_MSG,resultSets.get(CommonConstant.UNIFY_RETURN_SUCCESS_CODE));
        return  resultMap;
    }

    @GetMapping("/tel/login")
    @ApiOperation("用户手机登录")
    // @RequestParam String userName,@RequestParam String userPassword,@RequestParam String captcha
    // @RequestBody UserVO userVO
    public Map<String, Object> telLoginUser(@RequestParam String tel,@RequestParam String captcha){
        HashMap<String, Object> resultMap = new HashMap<>();
        Map<String, Object> resultSets = userService.executeTelLogin(tel,captcha);
        // 判断是否登录成功
        Object value = resultSets.get(CommonConstant.UNIFY_RETURN_FAIL_CODE);
        if (value != null){
            resultMap.put(CommonConstant.UNIFY_RETURN_FAIL_MSG,value);
            return resultMap;
        }
        resultMap.put(CommonConstant.UNIFY_RETURN_SUCCESS_MSG,resultSets.get(CommonConstant.UNIFY_RETURN_SUCCESS_CODE));
        return  resultMap;
    }

    @GetMapping("/test")
    @ApiOperation("测试")
    public String test(@RequestParam String test){
        return test;
    }
}
