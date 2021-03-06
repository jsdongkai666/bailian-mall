package com.cuning.service;


import com.cuning.bean.goods.BailianGoodsInfo;
import com.cuning.service.impl.RelatedFeignServiceImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "bailian-goods",fallback = RelatedFeignServiceImpl.class)
public interface RelatedFeignService {

    @GetMapping("/queryGoodsFootPrint")
    List<BailianGoodsInfo> selectFootPrintGoods(@RequestParam("userId") String userId);

    @GetMapping("/queryGoodsByGoodsCategoryId")
    List<BailianGoodsInfo> selectGoodsByCategoryId(@RequestParam("categoryId") Integer categoryId);

    @GetMapping("/queryGoodsCategoryIds")
    List<Integer> selectGoodsCategoryIds();

    @GetMapping("/queryGoodsById")
    BailianGoodsInfo selectGoodsById(@RequestParam("goodsId") Integer goodsId);
}
