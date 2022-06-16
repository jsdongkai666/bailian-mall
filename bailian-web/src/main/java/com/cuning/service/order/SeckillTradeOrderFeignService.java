package com.cuning.service.order;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * 远程调用订单中心接口
 */

@FeignClient(name="bailian-order")
public interface SeckillTradeOrderFeignService {

    //远程调用订单中心生成交易订单接口，使用feign
    @PostMapping("/createTradeOrder")
    String invokeCreateTradeOrderFeignService(@RequestParam("userId") String userId,
                                              @RequestParam("prodId") String prodId, @RequestParam("buyCount") Integer buyCount);

    //远程调用订单中心，查询交易订单接口，使用feign
    @GetMapping("/queryTradeOrder")
    Map<String, String> invokeQueryTradeOrderFeign(@RequestParam("userId") String userId,
                                                   @RequestParam("orderId") String orderId);

    @GetMapping("/payTradeOrder")
    Map<String, String> invokePayTradeOrderFeign(@RequestParam("userId") String userId,
                                                   @RequestParam("prodId") String prodId,
                                                   @RequestParam("orderId") String orderId);

    @GetMapping("/aliPayTradeOrder")
    String invokeAliPayTradeOrderFeign(@RequestParam("userId") String userId,
                                                 @RequestParam("prodId") String prodId,
                                                 @RequestParam("orderId") String orderId);
}
