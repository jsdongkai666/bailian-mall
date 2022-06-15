package com.cuning.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cuning.bean.goods.BailianGoodsInfo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

/*
 * @Created on : 2022/6/14 0014
 * <p>
 * @Author     : Administrator
 * <p>
 * @Description: GoodsInfoService
 **/
@FeignClient(name="bailian-goods")
public interface GoodsInfoFeignService {

    @PostMapping("/addGoods")
    BailianGoodsInfo saveGoods (@RequestBody BailianGoodsInfo goodsInfo);

    @GetMapping("/queryGoodsPage")
    Page<BailianGoodsInfo> queryGoodsInfoPage(@RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize);

    @PostMapping("/updateGoods")
    Boolean updateGoodsInfo(@RequestBody BailianGoodsInfo goodsInfo);

    @GetMapping("/deleteGoods")
    Boolean deleteGoodsInfo(@RequestParam("goodsId") Integer goodsId);
}
