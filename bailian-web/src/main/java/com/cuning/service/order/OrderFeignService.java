package com.cuning.service.order;

import com.cuning.bean.shoppingOrder.BailianOrder;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="bailian-order")
public interface OrderFeignService {

    @PostMapping("/insertOrder")
    Boolean insertOrder(@RequestBody BailianOrder bailianOrder);
}
