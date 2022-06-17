package com.cuning.controller;

import com.cuning.bean.user.User;
import com.cuning.bean.user.VipMember;
import com.cuning.constant.CommonConstant;
import com.cuning.constant.TimeZone;
import com.cuning.service.UserService;
import com.cuning.util.JwtUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tengjiaozhai
 * @Description TODO
 * @createTime 2022年06月14日 18:40:00
 */
@RestController
@Slf4j
public class MemberController {

    @Autowired
    private UserService userService;

    @PostMapping("/rechargeMember")
    @ApiOperation(value = "充值会员",notes = "30 积分购买等级1 50积分等级2 100积分等级3\n" +
            "等级1 98折 等级2 95折 等级3 9折")
    public Map<String,Object> rechargeMember(@RequestBody User user, @RequestParam Integer vipLevel) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<>();
        // 获取会员枚举对象
        VipMember vipMember = VipMember.fromLevel(vipLevel);
        if (vipMember == null){
            resultMap.put(CommonConstant.UNIFY_RETURN_FAIL_MSG,"不存在该会员无法购买");
            return resultMap;
        }
        // 30 积分购买等级1 50积分等级2 100积分等级3
        if (vipLevel == vipMember.getVipLevel() && user.getUserPoints() < vipMember.getVipPoint()){
            resultMap.put(CommonConstant.UNIFY_RETURN_FAIL_MSG,"积分不足无法购买，请充值后再尝试");
            return resultMap;
        }
        log.info("结果:{}",vipLevel > user.getVipLevel());
        // 充值会员
        if (vipLevel > user.getVipLevel() || ObjectUtils.isEmpty(user.getVipDate() )){
            // 新会员 升级会员 一个道理
            LocalDateTime localDateTime = LocalDateTime.now().plusMonths(1L);
            // 当前时间加上一个月 转成时间戳存入
            Date vipDate = Date.from(localDateTime.atZone(TimeZone.TIMEZONE).toInstant());
            user.setUserPoints(user.getUserPoints() - vipMember.getVipPoint());
            user.setVipLevel(vipLevel);
            user.setVipDate(vipDate);
            userService.updateById(user);
            resultMap.put(CommonConstant.UNIFY_RETURN_SUCCESS_MSG,"充值成功");
        }
        else {
            // 等级高的必须过期后才能购买等级低的 等级低的升级后之前作废重新计算
            // 续费正常延期
            if (vipLevel == user.getVipLevel()){
                // 正常续费
                LocalDateTime localDateTime = user.getVipDate().toInstant()
                        .atZone(TimeZone.TIMEZONE).toLocalDateTime().plusMonths(1L);
                Date vipDate = Date.from(localDateTime.atZone(TimeZone.TIMEZONE).toInstant());
                user.setUserPoints(user.getUserPoints() - vipMember.getVipPoint());
                user.setVipLevel(vipLevel);
                user.setVipDate(vipDate);
                userService.updateById(user);
                resultMap.put(CommonConstant.UNIFY_RETURN_SUCCESS_MSG,"续费成功");
            }
            // 降级会员不允许
            else if (user.getVipLevel() < vipLevel){
                resultMap.put(CommonConstant.UNIFY_RETURN_FAIL_MSG,"你当前会员等级高于你要买的会员等级，补课进行购买");
            }

        }
        // 更新token
        String token = JwtUtil.createToken(user);
        resultMap.put("token",token);
        return  resultMap;
    }

    @PostMapping("/checkMember")
    @ApiOperation("校验会员是否过期 true-为过期 false-不过期")
    public Boolean checkMember(@RequestBody User user) throws Exception {
        return user.getVipDate().before(new Date());

    }
}
