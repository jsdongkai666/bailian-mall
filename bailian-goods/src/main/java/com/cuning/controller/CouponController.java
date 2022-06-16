package com.cuning.controller;

import com.cuning.bean.coupon.BailianCoupon;
import com.cuning.service.CouponService;
import com.cuning.util.ResultBuildUtil;
import com.cuning.util.SnowFlake;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author dengteng
 * @title: CouponController
 * @projectName cuning-bailian
 * @description: 优惠券控制入口
 * @date 2022/6/14
 */
@Slf4j
@RestController
@Api(tags = "优惠卷操作入口")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @Autowired
    private SnowFlake snowFlake;

    @PostMapping("/newCoupon")
    @ApiOperation("新增优惠卷")
    public Map<String, Object> newCoupon(@RequestBody BailianCoupon bailianCoupon) {
        Map<String, Object> map = new HashMap<>();
        Double[] number = {50.0, 100.0, 200.0};
        List<Double> fullPrice = Arrays.asList(number);
        List<BailianCoupon> bailianCouponList = new ArrayList<>();
        Calendar calendar = new GregorianCalendar();
        bailianCoupon.setGrantStartTime(new Date());
        calendar.setTime(bailianCoupon.getGrantStartTime());
        calendar.add(calendar.DATE, 10);
        bailianCoupon.setGrantEndTime(calendar.getTime());
        calendar.add(calendar.DATE, 1);
        bailianCoupon.setEffectiveStartTime(calendar.getTime());
        calendar.add(calendar.DATE, 30);
        bailianCoupon.setEffectiveEndTime(calendar.getTime());
        bailianCoupon.setStatus(1);
        bailianCoupon.setRepeatQuantity(1);
        if (bailianCoupon.getCouponType().intValue() == 2) {
            for (int i = 1; i <= fullPrice.size(); i++) {
                bailianCoupon.setId("60" + Long.toString(snowFlake.nextId()).substring(9, 19));
                if (fullPrice.get(i - 1).doubleValue() < bailianCoupon.getCouponAmount()) {
                    continue;
                }
                bailianCoupon.setFullAmount(fullPrice.get(i - 1));
                BailianCoupon bailianCoupon1 = new BailianCoupon();
                BeanUtils.copyProperties(bailianCoupon, bailianCoupon1);
                bailianCouponList.add(bailianCoupon1);
            }
            if (bailianCouponList.size() == 0) {
                map.put("code", "200");
                map.put("msg", "新增失败");
                return map;
            }
        } else if (bailianCoupon.getCouponType().intValue() == 1) {
            bailianCoupon.setId("60" + Long.toString(snowFlake.nextId()).substring(9, 19));
            bailianCoupon.setFullAmount(0.0);
            bailianCouponList.add(bailianCoupon);
        } else if (bailianCoupon.getCouponType().intValue() == 3) {
            bailianCoupon.setId("60" + Long.toString(snowFlake.nextId()).substring(9, 19));
            bailianCouponList.add(bailianCoupon);
        }else {
            map.put("code", "500");
            map.put("msg", "新增失败");
            return map;
        }
        map.put("code", "200");
        map.put("msg", couponService.insertNewCoupon(bailianCouponList));

        return map;
    }


    @PostMapping("/deleteCoupon")
    @ApiOperation("删除优惠卷")
    public Map<String, Object> deleteCoupon(@RequestParam String id) {
        Map<String, Object> map = new HashMap<>();
        if(couponService.deleteCoupon(id)){
            map.put("code", "200");
            map.put("msg", "删除成功");
        }
        return map;
    }

    @GetMapping("/selectCoupon")
    @ApiOperation("查询优惠卷")
    public Map<String, Object> selectCoupon(@RequestParam String id) {
        Map<String, Object> map = new HashMap<>();
            map.put("code", "200");
            map.put("msg", couponService.selectCoupon(id));
        return map;
    }

    @PostMapping("/updateCoupon")
    @ApiOperation("更新优惠卷")
    public Map<String, Object> updateCoupon(@RequestBody BailianCoupon bailianCoupon) {
        Map<String, Object> map = new HashMap<>();
        if(couponService.updateCoupon(bailianCoupon)){
            map.put("code", "200");
            map.put("msg", "更新成功");
            return map;
        }
        map.put("code", "500");
        map.put("msg", "更新失败");
        return map;
    }

}
