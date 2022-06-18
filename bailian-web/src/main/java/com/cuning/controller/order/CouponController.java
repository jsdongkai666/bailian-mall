package com.cuning.controller.order;

import com.cuning.annotation.CheckToken;
import com.cuning.bean.coupon.BailianCoupon;
import com.cuning.bean.coupon.BailianCouponUser;
import com.cuning.bean.user.User;
import com.cuning.service.coupon.CouponFeignService;
import com.cuning.util.JwtUtil;
import com.cuning.util.RequestResult;
import com.cuning.util.ResultBuildUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author dengteng
 * @title: CouponController
 * @projectName cuning-bailian
 * @description: TODO
 * @date 2022/6/16
 */
@Api(tags = "优惠券管理入口")
@Slf4j
@RestController
public class CouponController {

    @Autowired
    private CouponFeignService couponFeignService;


    /**
    * @Param: []
    * @return: com.cuning.util.RequestResult<java.util.List<com.cuning.bean.coupon.BailianCoupon>>
    * @Author: dengteng
    * @Date: 2022/6/16
    * @Description: 获取所有优惠券列表
    */
    @CheckToken
    @ApiOperation(value = "获取所有优惠券列表信息",notes = "获取所有优惠券信息，用户可以选择优惠券进行领取")
    @GetMapping("/getCouponList")
    public RequestResult<List<BailianCoupon>> getCouponlist(){
        List<BailianCoupon> couponList = couponFeignService.getCouponList();
        return ResultBuildUtil.success(couponList);
    }

    /**
    * @Param: []
    * @return: com.cuning.util.RequestResult<java.util.Map<java.lang.String,java.lang.String>>
    * @Author: dengteng
    * @Date: 2022/6/16
    * @Description: 用户领取优惠券
    */
    @CheckToken
    @ApiOperation(value = "用户领取优惠券",notes = "根据所有优惠券查询的信息，用户选择领取优惠券")
    @GetMapping("/getCoupon")
    public RequestResult<Map<String, String>> userGetCoupon(HttpServletRequest request, @RequestParam("couponId")String couponId) throws Exception {
        User token = JwtUtil.parseJWT(request.getHeader("token"));
        Map<String, String> coupon = couponFeignService.getCoupon(token.getUserId(), couponId);
        return ResultBuildUtil.success(coupon);
    }

    /**
    * @Param: [javax.servlet.http.HttpServletRequest, java.lang.String]
    * @return: com.cuning.util.RequestResult<java.util.List<com.cuning.bean.coupon.BailianCouponUser>>
    * @Author: dengteng
    * @Date: 2022/6/16
    * @Description: 获取用户优惠券列表
    */
    @CheckToken
    @ApiOperation(value = "获取用户优惠券列表",notes = "根据用户查询用户的优惠券列表，可以查看优惠券的不同状态")
    @GetMapping("/getUserCouponList")
    public RequestResult<List<BailianCouponUser>> getUserCouponList(HttpServletRequest request,@RequestParam(required = false,name = "status")String status) throws Exception {
        User token = JwtUtil.parseJWT(request.getHeader("token"));
        List<BailianCouponUser> userCouponList = couponFeignService.getUserCouponList(token.getUserId(), status);
        return ResultBuildUtil.success(userCouponList);
    }

    /**
    * @Param: [javax.servlet.http.HttpServletRequest, java.lang.String]
    * @return: com.cuning.util.RequestResult<java.util.Map<java.lang.String,java.lang.Object>>
    * @Author: dengteng
    * @Date: 2022/6/16
    * @Description: 获取当前商品可用的优惠券
    */
    @ApiOperation(value = "获取当前商品可用的优惠券列表",notes = "返回当前商品满足的优惠券列表信息")
    @GetMapping("/getGoodsCoupon")
    public RequestResult<Map<String, Object>> getGoodsCoupon(HttpServletRequest request,@RequestParam("orderItemId")String orderItemId) throws Exception {
        User token = JwtUtil.parseJWT(request.getHeader("token"));
        Map<String, Object> goodsCoupon = couponFeignService.getGoodsCoupon(token.getUserId(), orderItemId);
        return ResultBuildUtil.success(goodsCoupon);
    }

    /**
    * @Param: [javax.servlet.http.HttpServletRequest, java.lang.String, java.lang.String]
    * @return: com.cuning.util.RequestResult<java.util.Map<java.lang.String,java.lang.Object>>
    * @Author: dengteng
    * @Date: 2022/6/16
    * @Description: 使用优惠券
    */
    @ApiOperation(value = "使用优惠券",notes = "对单个商品使用优惠券")
    @GetMapping("/userCoupon")
    public RequestResult<Map<String, String>> userCoupon(HttpServletRequest request,@RequestParam("orderItemid")String orderItemid,@RequestParam("couponId")String couponId) throws Exception {
        User token = JwtUtil.parseJWT(request.getHeader("token"));
        Map<String, String> stringStringMap = couponFeignService.useCoupon(token.getUserId(), orderItemid, couponId);
        return ResultBuildUtil.success(stringStringMap);
    }


}
