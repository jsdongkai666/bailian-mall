package com.cuning.controller;


import com.cuning.bean.goods.BailianGoodsInfo;
import com.cuning.constant.GoodsConstant;
import com.cuning.service.OrderFeignService;
import com.cuning.service.GoodsInfoService;
import com.cuning.util.RedisUtils;
import com.cuning.util.RequestResult;
import com.cuning.util.ResultBuildUtil;
import com.cuning.vo.GoodsDetailsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

    @Autowired
    private OrderFeignService orderFeignService;

    /**
     * @author : lixu
     * @date   : 2022/06/10
     * @param  : [javax.servlet.http.HttpServletRequest, java.lang.Integer]
     * @return : com.cuning.bean.BailianGoodsInfo
     * @description : 商品详情查询
     */
    @ApiOperation(value = "商品详情查询",notes = "根据id，查询商品详情，将结果存入redis")
    @GetMapping("/goodsDetails")
    public RequestResult<GoodsDetailsVO> goodsDetailsMap(@RequestParam("userId") String userId, @RequestParam("goodsId") String goodsId){

        // 调用接口查询商品详情
        BailianGoodsInfo goodsDetail = goodsInfoService.queryGoodsInfoById(goodsId);

        // 将时间设为权重值
        String score = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        //log.info("------ 当前时间 ------：{}",score);

        // 当前用户浏览过的商品的id存入redis中，并设置权重
        redisUtils.zadd(GoodsConstant.USER_FOOT_PRINT + userId,goodsDetail.getGoodsId(),Double.parseDouble(score));

        // zcard返回成员个数
        if(redisUtils.zcard(GoodsConstant.USER_FOOT_PRINT + userId) > 20) {
            // 数量满20，将权重值最低的删除
            redisUtils.zremoveRange(GoodsConstant.USER_FOOT_PRINT + userId,0, 0);
        }

        // 商品的详细情况
        GoodsDetailsVO goodsDetailsVO = new GoodsDetailsVO();
        goodsDetailsVO.setBailianGoodsInfo(goodsDetail);

        // 商品销量及商品的评价数
        Map<String,Integer> salesMap = new HashMap<>();
        salesMap.put("商品销量：",orderFeignService.selectOrderCount(goodsId));
        salesMap.put("评价数：",orderFeignService.queryCommentaryCount(goodsId));
        goodsDetailsVO.setSalesMap(salesMap);

        // 调用接口，获取评价信息
        goodsDetailsVO.setBailianGoodsCommentaryPage(orderFeignService.queryGoodsCommentary(1,3,goodsId,0));

        // 商品详情
        log.info("------ 商品详情：{} ------",goodsDetailsVO);

        // 返回商品详情实体
        return ResultBuildUtil.success(goodsDetailsVO);
    }
}
