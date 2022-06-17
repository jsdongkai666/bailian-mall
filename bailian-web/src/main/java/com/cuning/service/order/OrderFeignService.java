package com.cuning.service.order;

import com.cuning.bean.shoppingOrder.BailianOrder;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="bailian-order")
public interface OrderFeignService {

    @PostMapping("/insertOrder")
    Boolean insertOrder(@RequestBody BailianOrder bailianOrder);

    @GetMapping("/getOrderDetail")
    BailianOrder getOrderDetail(@RequestParam("orderNo") String orderNo);
}
