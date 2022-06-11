package com.cuning.service;

import com.cuning.bean.BailianGoodsInfo;
import com.cuning.service.impl.RelatedServiceImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "bailian-goods",fallback = RelatedServiceImpl.class)
public interface RelatedService {

    @GetMapping("/queryGoodsFootPrint")
    List<BailianGoodsInfo> selectFootPrintGoods(@RequestParam("userId") String userId);

    @GetMapping("/queryGoodsByGoodsCategoryId")
    List<BailianGoodsInfo> selectGoodsByCategoryId(@RequestParam("categoryId") Integer categoryId);

    @GetMapping("/queryGoodsCategoryIds")
    List<Integer> selectGoodsCategoryIds();

    @GetMapping("/queryGoodsById")
    BailianGoodsInfo selectGoodsById(@RequestParam("goodsId") Integer goodsId);
}
