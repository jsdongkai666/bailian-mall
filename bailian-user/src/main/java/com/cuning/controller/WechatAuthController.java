package com.cuning.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cuning.bean.user.User;
import com.cuning.config.WechatAuthConfig;
import com.cuning.service.UserService;
import com.cuning.util.*;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author tengjiaozhai
 * @Description TODO
 * @createTime 2022年05月25日 14:53:00
 */
@Slf4j
@RestController
public class WechatAuthController {

    @Autowired
    private WechatAuthConfig wechatAuthConfig;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private SnowFlake snowFlake;


    @GetMapping("/weChatAuthCodeUrl")
    @ApiOperation("请求回调地址")
    public Map<String, Object> generateWeChatAuthCodeUrl(){
        Map<String, Object> resultMap = new HashMap<>();

        StringBuilder wxauthCodeUrl = new StringBuilder(wechatAuthConfig.getCodeUri());
        wxauthCodeUrl.append("?appid=").append(wechatAuthConfig.getAppId())
                .append("&redirect_uri=").append(wechatAuthConfig.getRedirectUri())
                .append("&respones_type=code")
                .append("&scope=snsapi_userinfo")
                .append("&state=").append(UUID.randomUUID().toString().substring(0,8))
                .append("#wechat_redirect");
        // 返回结果
        resultMap.put("redirectUrl",wxauthCodeUrl);
        return resultMap;
    }

    /**
     * @description 上一步用户访问code请求地址，同意授权，微信官方会根据请求中提供的回调地址
     * @author  tengjiaozhai
     * @updateTime 2022/5/25 15:20 No such property: code for class: Script1
     */
    @RequestMapping("/wxauthCodeBack")
    public Map<String,Object>  wechatAuthCodeBack(HttpServletRequest request){

        // 如果用户同意授权，页面将跳转至
        // code说明： code作为access_toke票据

        String wxauthCode = request.getParameter("code");

        // 从请求中，获取微信官方放回的state值
        String wxauthSate = request.getParameter("state");

        log.info("--- 2 微信官方一部回调接口： {}  ---",wxauthCode);

        // 定义接口返回数据
        Map<String, Object> resultMap = new HashMap<>();

        resultMap.put("code",wxauthCode);

        resultMap.put("state",wxauthSate);

        // 通过code换区网页access_token

        // 通过code换取网页授权access_token

        //获取 code 后，请求以下链接获取access_token：
        // https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code

        // 创建请求地址拼接对象
        StringBuilder accsessTokenUrl = new StringBuilder(wechatAuthConfig.getAccessTokenUri());
        accsessTokenUrl.append("?appid=").append(wechatAuthConfig.getAppId())
                .append("&secret=").append(wechatAuthConfig.getAppSecret())
                .append("&code=").append(wxauthCode)
                .append("&grant_type=authorization_code");
        log.info("------ 3 通过微信官方返回的code，请求获取accessToken的完整地址:{} ------", accsessTokenUrl );

        // 通过code，请求获取access_token，结果是同步返回（不再是异步回调）
        // 要求：必须是程序中发送请求到微信官方
        // 程序中,发送http情求，到微信官方，获取结果

        String accessTokeJson = HttpClient4Util.getResponse4GetAsString(accsessTokenUrl.toString(),"utf-8");
        // 程序中，解析微信官方返回的json字符串

        JSONObject accessJsonObject = JSON.parseObject(accessTokeJson);


        // 判断请求结果是否是正确的，如果错误，直接结束
        if (!StringUtils.isEmpty(accessJsonObject.getString("errcode"))){
            resultMap.put("errCode",accessJsonObject.getString("errcode"));
            resultMap.put("errMsg",accessJsonObject.getString("errmsg"));

            return resultMap;
        }
        // 获取用户需要的参数
        String accessToken = accessJsonObject.getString("access_token");
        String openId = accessJsonObject.getString("openid");

        // 存入redis key使用openid,value access_token
        redisTemplate.opsForValue().set(openId,accessToken);

        log.info("--- 4.拉取用户信息（需要scope 为 snsapi_userinfo） ---");

        // 官方实例：

        //
        StringBuilder userInfoUrl = new StringBuilder(wechatAuthConfig.getUserInfoUri());
        userInfoUrl.append("?access_token=").append(accessToken)
                .append("&openid=").append(openId)
                .append("&lang=zh_CN");


        // 程序中发送情求发送到微信官方，拉取用户信息，同步返回结果json字符串结果
        String userInfoResultJson = HttpClient4Util.getResponse4GetAsString(userInfoUrl.toString(),"utf-8");
        // 按逗号拆分返回结果
        String[] split = userInfoResultJson.split(",");
        Map<String, String> userInfo = new HashMap<>();
        // 返回结果再拆分存入map
        for (String s : split) {
            String[] splitAgain = s.split(":");
            // 存入map 去除多余的 \ " { }
            String key = splitAgain[0].replace("\\","").replace("\"","").replace("{","");
            userInfo.put(key,
                    splitAgain[1].replace("\\","").replace("\"","").replace("}",""));
            // 头像特殊 有三个
            if (key.equals("headimgurl")){
                log.info(key);
                userInfo.put(key,
                        splitAgain[1].replace("\\","").replace("\"","").replace("}","")+
                        ":"+splitAgain[2].replace("\\","").replace("\"",""));
            }

        }
        log.info("用户信息{}，",userInfo);
        // 存入redis openid
        redisTemplate.opsForValue().set("openid",userInfo.get("openid"),5, TimeUnit.MINUTES);

        User insert = User.builder().userOpenid(userInfo.get("openid"))
                .userHeadImg(userInfo.get("headimgurl"))
                .userName(userInfo.get("nickname")).build();

        // 插入新用户
        // openid 只能对应一个用户
        LambdaQueryWrapper<User> qw = new LambdaQueryWrapper<>();
        qw.eq(User::getUserOpenid,userInfo.get("openid"));
        User one = userService.getOne(qw);
        // 没有相同的openid 才能插入
        if (one == null){
            insert.setUserId(Long.toString(snowFlake.nextId()));
            if(userService.save(insert)){
                log.info("成功插入新用户");
            }else {
                log.info("插入失败");
            }
        }



        // 返回拉取的用户详情信息
        resultMap.put("userInfo",userInfo);

        // 判断微信已经登陆过 不需要再调用手机登录接口
        if (one.getUserTel()!=null){
            resultMap.put("existTel",true);
        }else {
            resultMap.put("existTel",false);
        }
        redisTemplate.opsForHash().putAll("resultMap",resultMap);
        // 接口返回
        return resultMap;

    }

    @GetMapping("checkAccessToken")
    @ApiOperation("校验凭证")
    public RequestResult<String> checkAccessToken(@RequestParam String openId){
        StringBuilder accessTokenCheck = new StringBuilder(wechatAuthConfig.getAccessTokenCheck());
        accessTokenCheck.append("?access_token=").append(redisTemplate.opsForValue().get(openId))
                .append("&openid=").append(openId);


        log.info("------ 1 检验授权凭证（access_token）是否有效，请求校验accessToken的完整地址:{} ------", accessTokenCheck );

        String checkJson = HttpClient4Util.getResponse4GetAsString(accessTokenCheck.toString(), "utf-8");

        JSONObject jsonObject = JSON.parseObject(checkJson);

        if (jsonObject.getString("errcode").equals("4003")) {
            log.info("errcode： {}",jsonObject.getString("errcode"));
            return ResultBuildUtil.success("检验无效");
        }else {
            log.info("errcode： {}",jsonObject.getString("errcode"));
            return  ResultBuildUtil.success("检验有效");
        }
    }





}
