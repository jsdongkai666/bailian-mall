package com.cuning.util;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.cuning.bean.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

/**
 * @author tengjiaozhai
 * @Description TODO
 * @createTime 2022年06月11日 14:50:00
 */
@Component
@Slf4j
public class JwtUtil {

    /**
     * 生成token
     */
    public static String createToken(User user) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 7); //默认令牌过期时间7天
        JWTCreator.Builder builder = JWT.create();
        builder.withClaim("userId", user.getUserId())
                .withClaim("userName", user.getUserName())
                .withClaim("useTel", user.getUserTel())
                .withClaim("userOpenid", user.getUserOpenid())
                .withClaim("userMail",user.getUserMail())
                .withClaim("userSex",user.getUserSex())
                .withClaim("userHeadImg",user.getUserHeadImg())
                .withClaim("userPoints",user.getUserPoints())
                .withClaim("vipDate",user.getVipDate())
                .withClaim("vipLevel",user.getVipLevel());

        return builder.withExpiresAt(calendar.getTime())
                // admin加密
                .sign(Algorithm.HMAC256("admin"));
    }

    /**
     * 校验token
     */
    public static DecodedJWT verifyToken(String token) {
        if (token==null){
            log.info("token不能为空");
        }
        String password = "admin";
        //获取登录用户真正的密码假如数据库查出来的是123456
        JWTVerifier build = JWT.require(Algorithm.HMAC256(password)).build();
        return build.verify(token);
    }

    /**
     * 解析token 解码
     */
    public static User parseJWT(String token) throws Exception {

        try {
            DecodedJWT jwt = JWT.decode(token);
            Map<String, Claim> claims =(Collections.unmodifiableMap(jwt.getClaims()));
            User user = User.builder().userId(claims.get("userId").asString())
                    .userPoints(claims.get("userPoints").asInt()).vipLevel( claims.get("vipLevel").asInt()).build();

            if (claims.get("userName")!=null){
                user.setUserName(claims.get("userName").asString());
            }

            if (claims.get("userTel")!=null){
                user.setUserTel(claims.get("userTel").asString());
            }
            if (claims.get("userOpenid")!=null){
                user.setUserOpenid(claims.get("userOpenid").asString());
            }
            if (claims.get("userMail")!=null){
                user.setUserMail(claims.get("userMail").asString());
            }
            if (claims.get("userSex")!=null){
                user.setUserSex(claims.get("userSex").asString());
            }
            if (claims.get("userHeadImg")!=null){
                user.setUserHeadImg(claims.get("userHeadImg").asString());
            }
            if (claims.get("vipDate") != null){
                Date vipDate = claims.get("vipDate").asDate();
                user.setVipDate(vipDate);
            }
            return user;
        } catch (JWTDecodeException e) {
            return null;
        }

    }
}


