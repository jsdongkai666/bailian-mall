package com.cuning.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cuning.bean.goods.BailianGoodsCommentary;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "bailian-order")
public interface OrderFeignService {

    @GetMapping("/selectOrderCount")
    @ApiOperation(value = "查询商品销量", notes = "商品销售数量查询")
    Integer selectOrderCount(@RequestParam("goodsId") String goodsId);

    @GetMapping("/queryCommentaryCount")
    Integer queryCommentaryCount(@RequestParam("goodsId") String goodsId);

    @GetMapping("/queryGoodsCommentary")
    Page<BailianGoodsCommentary> queryGoodsCommentary(@RequestParam("pageNo") Integer pageNo,
                                                      @RequestParam("pageSize") Integer pageSize,
                                                      @RequestParam("goodsId") String goodsId,
                                                      @RequestParam("commentaryType") Integer commentaryType);

}
