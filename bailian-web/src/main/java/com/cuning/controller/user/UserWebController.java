package com.cuning.controller.user;

import com.alibaba.fastjson.JSON;
import com.cuning.bean.user.User;
import com.cuning.constant.CommonConstant;


import com.cuning.service.user.UserWebService;
import com.cuning.util.JwtUtil;
import com.cuning.util.RedisUtils;
import com.cuning.util.RequestResult;
import com.cuning.util.ResultBuildUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author tengjiaozhai
 * @Description TODO
 * @createTime 2022年06月12日 15:10:00
 */
@Slf4j
@RestController
@RequestMapping("/web")
@Api(tags = "用户管理")
public class UserWebController {

    @Autowired
    private UserWebService userWebService;

    @Autowired
    private RedisUtils redisUtils;

    @GetMapping("/user/register")
    @ApiOperation("用户注册")
    public RequestResult<String>  RegisterUser(@RequestParam("userName") String userName,
                                                          @RequestParam("userPassword") String userPassword){
        Map<String, Object> map = userWebService.RegisterUser(userName, userPassword);
        if (map.get(CommonConstant.UNIFY_RETURN_SUCCESS_MSG) != null){
            return ResultBuildUtil.success("注册成功");
        }
        return ResultBuildUtil.fail(map.get(CommonConstant.UNIFY_RETURN_FAIL_MSG).toString());
    }

    @GetMapping("/user/login")
    @ApiOperation("用户登录")
    public RequestResult<String> LoginUser(@RequestParam("userName") String userName,
                                         @RequestParam("userPassword") String userPassword,
                                         @RequestParam("captcha") String captcha,
                                           HttpServletRequest request) throws ParseException {
        Map<String, Object> map = userWebService.LoginUser(userName, userPassword, captcha);
        if (map.get(CommonConstant.UNIFY_RETURN_SUCCESS_MSG) != null){
            LinkedHashMap user = (LinkedHashMap) map.get(CommonConstant.UNIFY_RETURN_SUCCESS_MSG);
            User build = User.builder().userId(user.get("userId").toString())
                    .userPoints((Integer) user.get("userPoints")).vipLevel((Integer) user.get("vipLevel")).build();
            if (user.get("userName") != null){
                build.setUserName(user.get("userName").toString());
            }
            if (user.get("userPassword") != null){
                build.setUserPassword(user.get("userPassword").toString());
            }
            if (user.get("userTel") != null){
                build.setUserTel(user.get("userTel").toString());
            }
            if (user.get("userBirth") != null){
                build.setUserBirth(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(user.get("userBirth").toString()));
            }
            if (user.get("userOpenid") != null){
                build.setUserOpenid(user.get("userOpenid").toString());
            }
            if (user.get("userMail") != null){
                build.setUserMail(user.get("userMail").toString());
            }
            if (user.get("userSex") != null){
                build.setUserSex(user.get("userSex").toString());
            }
            if (user.get("userHeadImg") != null){
                build.setUserHeadImg(user.get("userHeadImg").toString());
            }
            if (user.get("vipDate") != null){
                build.setVipDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(user.get("vipDate").toString()));
            }
            String token = JwtUtil.createToken(build);
            log.info("create-token:{}",token);
            redisUtils.set("token", token);
            request.setAttribute("token",JSON.toJSON(token));
            log.info("token--{}",request.getSession().getAttribute("token"));
            return ResultBuildUtil.success(token);
        }
        return ResultBuildUtil.fail(map.get(CommonConstant.UNIFY_RETURN_FAIL_MSG).toString());
    }

    @GetMapping("/user/tel/login")
    @ApiOperation("用户手机登录")
    public RequestResult<String> telLoginUser(@RequestParam("tel") String tel,
                                            @RequestParam("captcha") String captcha,
                                              HttpServletRequest request) throws ParseException {

        // 用正则判断手机号
        if (!Pattern.matches("^1[3-9]\\d{9}$",tel)){
            return ResultBuildUtil.fail("手机号格式不正确，请重新输入");
        }

        Map<String, Object> map = userWebService.telLoginUser(tel,  captcha);
        if (map.get(CommonConstant.UNIFY_RETURN_SUCCESS_MSG) != null){
            LinkedHashMap user = (LinkedHashMap) map.get(CommonConstant.UNIFY_RETURN_SUCCESS_MSG);
            User build = User.builder().userId(user.get("userId").toString())
                    .userPoints((Integer) user.get("userPoints")).vipLevel((Integer) user.get("vipLevel")).build();
            if (user.get("userName") != null){
                build.setUserName(user.get("userName").toString());
            }
            if (user.get("userPassword") != null){
                build.setUserPassword(user.get("userPassword").toString());
            }
            if (user.get("userTel") != null){
                build.setUserTel(user.get("userTel").toString());
            }
            if (user.get("userBirth") != null){
                build.setUserBirth(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(user.get("userBirth").toString()));
            }
            if (user.get("userOpenid") != null){
                build.setUserOpenid(user.get("userOpenid").toString());
            }
            if (user.get("userMail") != null){
                build.setUserMail(user.get("userMail").toString());
            }
            if (user.get("userSex") != null){
                build.setUserSex(user.get("userSex").toString());
            }
            if (user.get("userHeadImg") != null){
                build.setUserHeadImg(user.get("userHeadImg").toString());
            }
            if (user.get("vipDate") != null){
                build.setVipDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(user.get("vipDate").toString()));
            }
            String token = JwtUtil.createToken(build);
            request.setAttribute("token",token);
            redisUtils.set("token",token);
            return ResultBuildUtil.success(token);
        }
        return ResultBuildUtil.fail(map.get(CommonConstant.UNIFY_RETURN_FAIL_MSG).toString());
    }




    @GetMapping("/weChatAuthCodeUrl")
    @ApiOperation("请求回调地址")

    public RequestResult<Map<String, Object>> generateWeChatAuthCodeUrl(){
        return ResultBuildUtil.success(userWebService.generateWeChatAuthCodeUrl());
    }

    @GetMapping("/verifyToken")
    @ApiOperation("测试用例 - 解析token")

    public User parseJWT(HttpServletRequest request) throws Exception {

        return JwtUtil.parseJWT(request.getHeader("token"));
    }


    @GetMapping("/toggleTel")
    @ApiOperation("测试微信登录后是否需要转手机登录 false-需要 true-不需要")
    public String toggleTel(){
        return redisUtils.hmget("resultMap").get("existTel").toString();
    }

    @GetMapping("/logout")
    @ApiOperation("退出登录")
    public RequestResult<String> logout(HttpServletRequest request){
        redisUtils.del("token");
        return ResultBuildUtil.success("退出登录成功");
    }

}
