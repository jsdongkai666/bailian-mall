package com.cuning.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cuning.bean.coupon.BailianCoupon;
import com.cuning.bean.coupon.BailianCouponUser;
import com.cuning.bean.goods.BailianGoodsInfo;
import com.cuning.bean.shoppingOrder.BailianOrderItem;
import com.cuning.constant.CommonConstant;
import com.cuning.constant.CouponEnums;
import com.cuning.mapper.CouponUserMapper;
import com.cuning.service.CouponService;
import com.cuning.service.CouponUserService;
import com.cuning.service.GoodsInfoService;
import com.cuning.service.OrderItemService;
import com.cuning.util.SnowFlake;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author dengteng
 * @title: CouponUserServiceImpl
 * @projectName cuning-bailian
 * @description: TODO
 * @date 2022/6/14
 */
@Service
public class CouponUserServiceImpl extends ServiceImpl<CouponUserMapper, BailianCouponUser> implements CouponUserService {

    @Autowired(required = false)
    private CouponUserMapper couponUserMapper;

    @Autowired
    private CouponService couponService;

    @Autowired
    private SnowFlake snowFlake;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private GoodsInfoService goodsInfoService;



    @Override
    public boolean getCoupon(String userId, String couponId) {
        BailianCoupon coupon = couponService.getById(couponId);
        BailianCouponUser userCoupon = new BailianCouponUser();
        // id字段名相同，id重新赋值
        BeanUtils.copyProperties(coupon,userCoupon);
        userCoupon.setId(Long.toString(snowFlake.nextId()));
        userCoupon.setUserId(userId);
        userCoupon.setStatus("2");
        userCoupon.setCodeNo(Long.toString(snowFlake.nextId()));
        userCoupon.setCouponTempId(couponId);

        return this.save(userCoupon);
    }

    @Override
    public List<BailianCouponUser> getUserCouponList(String userId, String status) {

        QueryWrapper<BailianCouponUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(BailianCouponUser::getUserId,userId);
        if(!StringUtils.isEmpty(status)){
            queryWrapper.lambda().eq(BailianCouponUser::getStatus,status);
        }
        List<BailianCouponUser> list = this.list(queryWrapper);
        List<BailianCouponUser> collect = list.stream().map(item -> {
            item.setStatusMsg(CouponEnums.getMsgByCode(item.getStatus()));
            return item;
        }).collect(Collectors.toList());
        return collect;
    }

    @Override
    public boolean editUserCouponStatus() {
        return couponUserMapper.updateCouponUserStatus() > 1;
    }

    @Override
    public List<BailianCouponUser> getUserCouponListByGoods(String userId, String orderItemId) {

        BailianOrderItem orderItem = orderItemService.getById(orderItemId);
        // 商品
        BailianGoodsInfo goodsInfo = goodsInfoService.getById(orderItem.getGoodsId());

        // 优惠券
        QueryWrapper<BailianCouponUser> couponUserQueryWrapper = new QueryWrapper<>();
        couponUserQueryWrapper.lambda().eq(BailianCouponUser::getUserId,userId).eq(BailianCouponUser::getStatus,CouponEnums.UNUSED.getCode());
        List<BailianCouponUser> couponUserList = this.list(couponUserQueryWrapper);


        //double totalPrice = orderItem.getSellingPrice() * orderItem.getGoodsCount();

        List<BailianCouponUser> result = new ArrayList<>();
        for (BailianCouponUser couponUser : couponUserList) {
            BailianCoupon coupon = couponService.getById(couponUser.getCouponTempId());
            // 通用券
            if (coupon.getCategoryId() == 0){
                // 直减券
                if (coupon.getCouponType() == 1) {
                    result.add(couponUser);
                    //orderItem.setPriceAfterDiscount(orderItem.getTotalPrice() - coupon.getCouponAmount());
                }
                // 满减券，商品价格需要大于满减的价格
                if (coupon.getCouponType() == 2 && orderItem.getTotalPrice() > coupon.getFullAmount()) {
                    result.add(couponUser);
                    //orderItem.setPriceAfterDiscount(orderItem.getTotalPrice() - coupon.getCouponAmount());
                }
                // 打折券，商品价格需要大于使用条件
                if (coupon.getCouponType() == 3 && orderItem.getTotalPrice() > coupon.getFullAmount()) {
                    result.add(couponUser);
                    //orderItem.setPriceAfterDiscount(orderItem.getTotalPrice() * coupon.getCouponAmount());
                }
            }// 类券
            else if (goodsInfo.getGoodsCategoryId().equals(coupon.getCategoryId())){
                // 直减券
                if (coupon.getCouponType() == 1) {
                    result.add(couponUser);
                    //orderItem.setPriceAfterDiscount(orderItem.getTotalPrice() - coupon.getCouponAmount());
                }
                // 满减券，商品价格需要大于满减的价格
                if (coupon.getCouponType() == 2 && orderItem.getTotalPrice() > coupon.getFullAmount()) {
                    result.add(couponUser);
                    //orderItem.setPriceAfterDiscount(orderItem.getTotalPrice() - coupon.getCouponAmount());
                }
                // 打折券，商品价格需要大于使用条件
                if (coupon.getCouponType() == 3 && orderItem.getTotalPrice() > coupon.getFullAmount()) {
                    result.add(couponUser);
                    //orderItem.setPriceAfterDiscount(orderItem.getTotalPrice() * coupon.getCouponAmount());
                }
            }
        }

        return result;
    }

    @Override
    public Map<String, String> useCoupon(String userId, String orderItemId, String couponId) {

        BailianOrderItem orderItem = orderItemService.getById(orderItemId);
        BailianCouponUser couponUser = this.getById(couponId);
        BailianCoupon coupon = couponService.getById(couponUser.getCouponTempId());

        boolean flag = false;
        // 直减券
        if (coupon.getCouponType() == 1 ) {
            flag = true;
            orderItem.setPriceAfterDiscount(orderItem.getTotalPrice() - coupon.getCouponAmount());
        }
        // 满减券，商品价格需要大于满减的价格
        if (coupon.getCouponType() == 2 && orderItem.getTotalPrice() > coupon.getFullAmount()) {
            flag = true;
            orderItem.setPriceAfterDiscount(orderItem.getTotalPrice() - coupon.getCouponAmount());
        }
        // 打折券，商品价格需要大于使用条件
        if (coupon.getCouponType() == 3  && orderItem.getTotalPrice() > coupon.getFullAmount()) {
            flag = true;
            orderItem.setPriceAfterDiscount(orderItem.getTotalPrice() * coupon.getCouponAmount());
        }
        orderItem.setCouponId(couponId);
        couponUser.setUseTime(new Date());
        couponUser.setStatus("3");
        Map<String, String> result = new HashMap<>();
        if (flag && orderItemService.updateById(orderItem) && this.updateById(couponUser)) {
            result.put("code", CommonConstant.UNIFY_RETURN_SUCCESS_CODE);
            result.put("msg","使用成功");
            return result;
        }
        result.put("code", CommonConstant.UNIFY_RETURN_FAIL_CODE);
        result.put("msg","使用失败");

        return result;
    }
}
