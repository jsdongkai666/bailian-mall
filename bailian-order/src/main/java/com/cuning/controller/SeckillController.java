package com.cuning.controller;

import com.cuning.bean.seckill.BailianSeckill;
import com.cuning.bean.shoppingOrder.BailianOrder;
import com.cuning.service.SeckillService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created On : 2022/6/16.
 * <p>
 * Author     : WangDeFeng
 * <p>
 * Description: SeckillController
 */
@RestController
@Api(tags = "抢购操作入口")
public class SeckillController {


    @Autowired
    private SeckillService seckillService;

    @PostMapping("/insertShop")
    @ApiOperation(value = "插入抢购商品")
    public Boolean insertShop(@RequestBody BailianSeckill bailianSeckill){
        return seckillService.insertSeckillShop(bailianSeckill);
    }

    @PostMapping("/deleteShop")
    @ApiOperation(value = "删除抢购商品")
    public Boolean deleteShop(@RequestParam String goodsId){
        return seckillService.deleteSeckillShop(goodsId);
    }

    @PostMapping("/updateShop")
    @ApiOperation(value = "修改抢购商品")
    public Boolean updateShop(@RequestBody BailianSeckill bailianSeckill){
        return seckillService.updateSeckillShop(bailianSeckill);
    }

    @GetMapping("/selectShop")
    @ApiOperation(value = "搜索抢购商品")
    public BailianSeckill selectShop(@RequestParam String goodsName){
        return seckillService.selectSeckillShop(goodsName);
    }
}
