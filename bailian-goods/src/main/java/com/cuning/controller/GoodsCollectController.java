package com.cuning.controller;

import com.cuning.bean.goods.BailianGoodsInfo;
import com.cuning.constant.CommonConstant;
import com.cuning.service.GoodsInfoService;
import com.cuning.util.PageSupport;
import com.cuning.util.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dengteng
 * @title: GoodsCollectController
 * @projectName cuning-bailian
 * @description: 商品收藏
 * @date 2022/6/13
 */
@Slf4j
@RestController
@Api(tags = "商品收藏操作入口")
public class GoodsCollectController {

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private GoodsInfoService goodsInfoService;

    /**
     * @Param: [java.lang.String, java.lang.String]
     * @return: java.util.Map<java.lang.String, java.lang.String>
     * @Author: dengteng
     * @Date: 2022/6/13
     * @Description: 收藏商品
     */
    @ApiOperation("收藏商品")
    @GetMapping("/collectGoods")
    public Map<String, String> collectGoods(@RequestParam("userId") String userId, @RequestParam("goodsId") String goodsId) {
        Map<String, String> result = new HashMap<>();
        redisUtils.incr(userId + ":" + goodsId, 1);
        redisUtils.expire(userId + ":" + goodsId, 60);

        BailianGoodsInfo byId = goodsInfoService.getById(goodsId);

        if (byId == null) {
            result.put("code", CommonConstant.UNIFY_RETURN_FAIL_CODE);
            result.put("msg", "商品信息不存在！");
            return result;
        }

        if (Integer.valueOf(redisUtils.get(userId + ":" + goodsId).toString()) > 4) {
            result.put("code", CommonConstant.UNIFY_RETURN_SUCCESS_CODE);
            result.put("msg", "操作过于频繁，请稍后再试！");
            return result;
        }

        return goodsInfoService.collectGoods(userId, goodsId);

    }


    /**
    * @Param: []
    * @return: java.util.Map<java.lang.String,java.lang.Object>
    * @Author: dengteng
    * @Date: 2022/6/13
    * @Description: 获取用户收藏列表
    */
    @ApiOperation("获取用户收藏列表")
    @GetMapping("/getUserCollectList")
    public Map<String, PageSupport> getUserCollectList(@RequestParam("userId") String userId,
                                                       @RequestParam(name = "pageNo",required = false,defaultValue = "1")String pageNo,
                                                       @RequestParam(name = "pageSize",required = false,defaultValue = "5")String pageSize){
        Map<String,  PageSupport> result = new HashMap<>();

        PageSupport collectListByUserId = goodsInfoService.getCollectListByUserId(userId,pageNo,pageSize);

        result.put("data", collectListByUserId);
        return  result;
    }

}
