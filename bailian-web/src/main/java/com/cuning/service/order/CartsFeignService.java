package com.cuning.service.order;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cuning.bean.shoppingcarts.BailianCartProducts;
import com.cuning.bean.shoppingcarts.BailianCarts;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(name="bailian-order")
public interface CartsFeignService {

    //远程调用查询购物车接口
    @GetMapping("/getCartsList")
    List<BailianCarts> getCartListPageInfo(@RequestParam("pageNo") Integer pageNo,
                                           @RequestParam("pageSize") Integer pageSize, @RequestParam("userId") String userId);

    @GetMapping("/getCartsById")
    BailianCarts getCartsDetailById(@RequestParam("userId") String UserId);

    @PostMapping("/addCartsList")
    boolean addCartsByUserId(@RequestBody BailianCarts bailianCarts);

    @PostMapping("/addCartsDetail")
    boolean addCartsDetail(@RequestBody BailianCartProducts bailianCartProducts);

    @PostMapping("/deleteCartsByIds")
    boolean deleteCartsByIds(@RequestParam("ids") List<String> ids);

    @PostMapping("/updateCartsInfo")
    boolean updateCartsInfo(@RequestParam("id") String id,@RequestParam("buyCount") Integer buyCount);
}
