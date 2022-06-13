package com.cuning.controller;


import com.cuning.bean.goods.BailianGoodsInfo;
import com.cuning.service.GoodsInfoService;
import com.cuning.util.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * Created On : 2022/06/10.
 * <p>
 * Author     : lixu
 * <p>
 * Description: 商品详情操作入口
 */
@Slf4j
@RestController
@Api(tags = "商品详情操作入口")
public class GoodsDetailsController {

    @Autowired
    private GoodsInfoService goodsInfoService;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * @author : lixu
     * @date   : 2022/06/10
     * @param  : [javax.servlet.http.HttpServletRequest, java.lang.Integer]
     * @return : com.cuning.bean.BailianGoodsInfo
     * @description : 商品详情查询
     */
    @ApiOperation(value = "商品详情查询",notes = "根据id，查询商品详情，将结果存入redis")
    @GetMapping("/goodsDetails")
    public BailianGoodsInfo goodsDetailsMap(HttpServletRequest request, @RequestParam Integer goodsId){

        // 获取session用户
        String userId = request.getParameter("userId");

        // 调用接口查询商品详情
        BailianGoodsInfo goodsDetail = goodsInfoService.queryGoodsInfoById(goodsId);

        // 将时间设为权重值
        String score = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        //log.info("------ 当前时间 ------：{}",score);

        // 当前用户浏览过的商品的id存入redis中，并设置权重
        redisUtils.zadd(userId,goodsDetail.getGoodsId().toString(),Double.parseDouble(score));

        // zcard返回成员个数
        if(redisUtils.zcard(userId) > 20) {
            // 数量满20，将权重值最低的删除
            redisUtils.zremoveRange(userId,0, 0);
        }
        // 商品详情
        log.info("------ 商品详情：{} ------",goodsDetail);

        // 返回商品详情实体
        return goodsDetail;
    }
}
