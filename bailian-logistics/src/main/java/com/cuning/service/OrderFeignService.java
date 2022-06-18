package com.cuning.service;

import com.cuning.bean.shoppingOrder.BailianOrder;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created On : 2022/6/17
 * <p>
 * Author : dengteng
 * <p>
 * Description: 订单远程调用业务层
 */
@FeignClient(value = "bailian-order")
public interface OrderFeignService {

    /** 
    * @Param: [java.lang.String] 
    * @return: com.cuning.bean.shoppingOrder.BailianOrder 
    * @Author: dengteng
    * @Date: 2022/6/17 
    * @Description: 查询订单详情 
    */
    @GetMapping("/selectOrder")
    BailianOrder selectOrder(@RequestParam(value = "orderNo") String orderNo);

    /** 
    * @Param: [com.cuning.bean.shoppingOrder.BailianOrder] 
    * @return: java.lang.Boolean 
    * @Author: dengteng
    * @Date: 2022/6/17 
    * @Description: 修改订单 
    */
    @PostMapping("/updateOrder")
    Boolean updateOrder(@RequestBody BailianOrder bailianOrder);
    
    
}
