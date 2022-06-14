package com.cuning.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cuning.bean.goods.BailianGoodsInfo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created On : 2022/6/10.
 * <p>
 * Author     : WangDeFeng
 * <p>
 * Description: BalianGoodsService
 */
@FeignClient(name = "bailian-goods")
public interface BalianGoodsService {

    @GetMapping("/queryGoodsPage")
    Page<BailianGoodsInfo> invokeQueryGoodsInfoPage(@RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize, @RequestParam("goodsName") String goodsName);

}
