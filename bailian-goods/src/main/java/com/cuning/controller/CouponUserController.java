package com.cuning.controller;

import com.cuning.bean.coupon.BailianCouponUser;
import com.cuning.service.CouponService;
import com.cuning.service.CouponUserService;
import com.cuning.util.RedisUtils;
import com.netflix.discovery.converters.Auto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dengteng
 * @title: CouponUserController
 * @projectName cuning-bailian
 * @description: 用户优惠券操作入口
 * @date 2022/6/15
 */
@Slf4j
@RestController
@Api(tags = "用户优惠券操作入口")
public class CouponUserController {

    @Autowired
    private CouponService couponService;

    @Autowired
    private CouponUserService couponUserService;

    @Autowired
    private RedisUtils redisUtils;

    /**
    * @Param: [java.lang.String]
    * @return: java.util.Map<java.lang.String,java.lang.String>
    * @Author: dengteng
    * @Date: 2022/6/15
    * @Description: 用户获取优惠券
    */
    @ApiOperation(value = "用户领取优惠券",notes = "输入用户id和优惠券id领取优惠券")
    @GetMapping("/getCoupon")
    public Map<String, String> getCoupon(@RequestParam("userId")String userId,@RequestParam("couponId")String couponId){
        Map<String, String> result = new HashMap<>();
        // 判断优惠券是否有效
        String isEfficient = couponService.couponIsEfficient(couponId);
        if (!isEfficient.equals("true")) {
            result.put("code","301");
            result.put("msg",isEfficient);
            return result;
        }

        // 减少优惠券数量
        if (!couponService.subCouponAuantity(couponId)) {
            result.put("code","301");
            result.put("msg","优惠券领取失败！");
            return result;
        }

        // 用户优惠券添加
        if (!couponUserService.getCoupon(userId,couponId)) {
            result.put("code","301");
            result.put("msg","优惠券领取失败！");
            return result;
        }

        result.put("code","200");
        result.put("msg","优惠券领取成功！");
        return result;
    }


    /**
    * @Param: [java.lang.String, java.lang.String]
    * @return: java.util.List<com.cuning.bean.coupon.BailianCouponUser>
    * @Author: dengteng
    * @Date: 2022/6/15
    * @Description: 用户优惠券列表
    */
    @ApiOperation(value = "获取用户优惠券列表",notes = "输入用户id和优惠券状态（可以不输入）获取用户优惠券信息")
    @GetMapping("/getUserCouponList")
    public List<BailianCouponUser> getUserCouponList(@RequestParam("userId")String userId, @RequestParam(required = false,name = "status")String status){
        List<BailianCouponUser> userCouponList = couponUserService.getUserCouponList(userId, status);
        return userCouponList;
    }

    /**
    * @Param: [java.lang.String]
    * @return: java.util.List<com.cuning.bean.coupon.BailianCouponUser>
    * @Author: dengteng
    * @Date: 2022/6/15
    * @Description: 获取商品可用的优惠券
    */
    @ApiOperation(value = "获取当前商品可用优惠券列表",notes = "输入用户id和订单商品编号编获取可用优惠券列表信息")
    @GetMapping("/getGoodsCoupon")
    public List<BailianCouponUser> getGoodsCoupon(@RequestParam("userId")String userId,@RequestParam("orderItemid")String orderItemid){
        List<BailianCouponUser> userCouponList = couponUserService.getUserCouponListByGoods(userId, orderItemid);
        return userCouponList;
    }

    /**
    * @Param: [java.lang.String, java.lang.String]
    * @return: java.util.Map<java.lang.String,java.lang.String>
    * @Author: dengteng
    * @Date: 2022/6/15
    * @Description: 用户使用优惠券
    */
    @ApiOperation(value = "用户使用优惠券",notes = "输入用户id和订单商品编号，优惠券编号确认使用优惠券")
    @GetMapping("/userCoupon")
    public Map<String, String> useCoupon(@RequestParam("userId")String userId,@RequestParam("orderItemid")String orderItemid,@RequestParam("couponId")String couponId){
        return couponUserService.useCoupon(userId, orderItemid, couponId);
    }


    /**
    * @Param: []
    * @return: void
    * @Author: dengteng
    * @Date: 2022/6/15
    * @Description: 用户优惠券状态定时任务
    */
    @Scheduled(cron = "0 0 0 * * ? ")
    //@GetMapping("/scheduled")
    public void setCheckStatus(){
        log.info("定时任务修改优惠券状态开始");
        boolean flag = false;
        if (redisUtils.lock("couponSheduled",1,60)) {
            flag = couponUserService.editUserCouponStatus();
        }
        log.info("定时任务修改优惠券状态结束，结果：{}",flag);
    }


}
