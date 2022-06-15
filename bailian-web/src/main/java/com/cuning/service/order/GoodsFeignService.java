package com.cuning.service.order;

import com.cuning.bean.goods.BailianGoodsInfo;
import com.cuning.bean.shoppingcarts.BailianCarts;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name="bailian-goods")
public interface GoodsFeignService {

    //远程调用查询物品详情
    @GetMapping("/queryGoodsById")
    BailianGoodsInfo getGoodsDetail(@RequestParam("goodsId") String goodsId);
}
