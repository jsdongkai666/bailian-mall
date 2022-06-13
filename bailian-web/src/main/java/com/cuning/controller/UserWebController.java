package com.cuning.controller;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.impl.JWTParser;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.cuning.annotation.CheckToken;
import com.cuning.bean.user.User;
import com.cuning.constant.CommonConstant;
import com.cuning.service.UserWebService;
import com.cuning.util.JwtUtil;
import com.cuning.util.RedisUtils;
import com.cuning.util.RequestResult;
import com.cuning.util.ResultBuildUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author tengjiaozhai
 * @Description TODO
 * @createTime 2022年06月12日 15:10:00
 */
@Slf4j
@RestController
@RequestMapping("/web")
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
            User user = (User) map.get(CommonConstant.UNIFY_RETURN_SUCCESS_MSG);

            return ResultBuildUtil.success("注册成功");
        }
        return ResultBuildUtil.fail(map.get(CommonConstant.UNIFY_RETURN_FAIL_MSG).toString());
    }

    @GetMapping("/user/login")
    @ApiOperation("用户登录")
    public RequestResult<String> LoginUser(@RequestParam("userName") String userName,
                                         @RequestParam("userPassword") String userPassword,
                                         @RequestParam("captcha") String captcha,
                                           HttpServletRequest request){
        Map<String, Object> map = userWebService.LoginUser(userName, userPassword, captcha);
        if (map.get(CommonConstant.UNIFY_RETURN_SUCCESS_MSG) != null){
            LinkedHashMap user = (LinkedHashMap) map.get(CommonConstant.UNIFY_RETURN_SUCCESS_MSG);
            User build = User.builder().userId(user.get("userId").toString()).userName(user.get("userName").toString())
                    .userPassword(user.get("userPassword").toString()).userTel(user.get("userTel").toString())
                    .userOpenid(user.get("userOpenid").toString()).userMail(user.get("userMail").toString())
                    .userSex(user.get("userSex").toString()).userHeadImg(user.get("userHeadImg").toString())
                    .userPoints((Integer) user.get("userPoints")).vipLevel((Integer) user.get("vipLevel")).build();
            String token = JwtUtil.createToken(build);
            log.info("create-token:{}",token);
            redisUtils.set("token", token);
            request.setAttribute("token",JSON.toJSON(token));
            log.info("token--{}",request.getSession().getAttribute("token"));
            return ResultBuildUtil.success(token);
        }
        return ResultBuildUtil.fail("登录失败");
    }

    @GetMapping("/user/tel/login")
    @ApiOperation("用户手机登录")
    public RequestResult<String> telLoginUser(@RequestParam("tel") String tel,
                                            @RequestParam("captcha") String captcha,
                                              HttpServletRequest request){
        Map<String, Object> map = userWebService.telLoginUser(tel,  captcha);
        if (map.get(CommonConstant.UNIFY_RETURN_SUCCESS_MSG) != null){
            LinkedHashMap user = (LinkedHashMap) map.get(CommonConstant.UNIFY_RETURN_SUCCESS_MSG);
            User build = User.builder().userId(user.get("userId").toString()).userName(user.get("userName").toString())
                    .userPassword(user.get("userPassword").toString()).userTel(user.get("userTel").toString())
                    .userOpenid(user.get("userOpenid").toString()).userMail(user.get("userMail").toString())
                    .userSex(user.get("userSex").toString()).userHeadImg(user.get("userHeadImg").toString())
                    .userPoints((Integer) user.get("userPoints")).vipLevel((Integer) user.get("vipLevel")).build();
            String token = JwtUtil.createToken(build);
            request.setAttribute("token",token);
            redisUtils.set("token",token);
            return ResultBuildUtil.success(token);
        }
        return ResultBuildUtil.fail("登录失败");
    }



    @ApiOperation("手机验证码")
    @GetMapping(value = "/tel/captcha")
    public String telCaptcha(){
        return userWebService.telCaptcha();
    }

    @GetMapping("/weChatAuthCodeUrl")
    @ApiOperation("请求回调地址")

    public RequestResult<Map<String, Object>> generateWeChatAuthCodeUrl(){
        return ResultBuildUtil.success(userWebService.generateWeChatAuthCodeUrl());
    }

    @GetMapping("/verifyToken")
    @ApiOperation("测试用例 - 解析token")
    public User parseJWT(HttpServletRequest request) throws Exception {

//        try {
//            DecodedJWT jwt = JWT.decode(request.getParameter("token"));
//            Map<String, Claim> claims =(Collections.unmodifiableMap(jwt.getClaims()));
//            User user = User.builder().userId(claims.get("userId").asString()+"").userName(claims.get("userName").asString())
//                    .userPoints(claims.get("userPoints").asInt()).vipLevel( claims.get("vipLevel").asInt()).build();
//
//            if (claims.get("userTel")!=null){
//                user.setUserTel(claims.get("userTel").asString());
//            }
//            if (claims.get("userOpenid")!=null){
//                user.setUserOpenid(claims.get("userOpenid").asString());
//            }
//            if (claims.get("userMail")!=null){
//                user.setUserMail(claims.get("userMail").asString());
//            }
//            if (claims.get("userSex")!=null){
//                user.setUserSex(claims.get("userSex").asString());
//            }
//            if (claims.get("userHeadImg")!=null){
//                user.setUserHeadImg(claims.get("userHeadImg").asString());
//            }
//            return user;
//        } catch (JWTDecodeException e) {
//            return null;
//        }
        String token = request.getSession().getAttribute("token").toString();

        return JwtUtil.parseJWT(token);
    }




}
