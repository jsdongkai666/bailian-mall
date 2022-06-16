package com.cuning.service.coupon;

import com.cuning.bean.coupon.BailianCoupon;
import com.cuning.bean.coupon.BailianCouponUser;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author dengteng
 * @title: CouponFeignService
 * @projectName cuning-bailian
 * @description: TODO
 * @date 2022/6/16
 */
@FeignClient(value = "bailian-goods")
public interface CouponFeignService {

    /**
    * @Param: []
    * @return: java.util.List<com.cuning.bean.coupon.BailianCoupon>
    * @Author: dengteng
    * @Date: 2022/6/16
    * @Description: 获取可以领取的优惠券
    */
    @GetMapping("/getCouponList")
    List<BailianCoupon> getCouponList();
    /**
    * @Param: [java.lang.String, java.lang.String]
    * @return: java.util.Map<java.lang.String,java.lang.String>
    * @Author: dengteng
    * @Date: 2022/6/16
    * @Description: 用户领取优惠券信息
    */
    @GetMapping("/getCoupon")
    Map<String, String> getCoupon(@RequestParam("userId")String userId, @RequestParam("couponId")String couponId);

    /**
    * @Param: [java.lang.String, java.lang.String]
    * @return: java.util.List<com.cuning.bean.coupon.BailianCouponUser>
    * @Author: dengteng
    * @Date: 2022/6/16
    * @Description: 获取用户优惠券列表信息
    */
    @GetMapping("/getUserCouponList")
    List<BailianCouponUser> getUserCouponList(@RequestParam("userId")String userId, @RequestParam(required = false,name = "status")String status);

    /**
    * @Param: [java.lang.String, java.lang.String]
    * @return: java.util.Map<java.lang.String,java.lang.Object>
    * @Author: dengteng
    * @Date: 2022/6/16
    * @Description: 获取当前商品可用的优惠券列表
    */
    @GetMapping("/getGoodsCoupon")
    Map<String, Object> getGoodsCoupon(@RequestParam("userId")String userId,@RequestParam("orderItemid")String orderItemid);

    /**
    * @Param: [java.lang.String, java.lang.String, java.lang.String]
    * @return: java.util.Map<java.lang.String,java.lang.String>
    * @Author: dengteng
    * @Date: 2022/6/16
    * @Description: 对当前商品使用指定优惠券
    */
    @GetMapping("/userCoupon")
    Map<String, String> useCoupon(@RequestParam("userId")String userId,@RequestParam("orderItemid")String orderItemid,@RequestParam("couponId")String couponId);
}
