package com.cuning.controller.goods;

import com.cuning.service.GoodsFeignService;
import com.cuning.util.RequestResult;
import com.cuning.util.ResultBuildUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author dengteng
 * @title: GoodsController
 * @projectName cuning-bailian
 * @description: 商品管理操作入口
 * @date 2022/6/14
 */
@Slf4j
@RestController
@Api(tags = "商品管理操作入口")
public class GoodsController {

    @Autowired
    private GoodsFeignService goodsFeignService;

    @ApiOperation(value = "用户设置到货提醒",notes = "传入用户id和商品id为用户设置该商品的到货提醒，商品补货时，会发送到货提醒")
    @GetMapping("/setArrivalReminders")
    public RequestResult<Map<String, String>> setArrivalRemindersByUserId(@RequestParam("userId") String userId,@RequestParam("goodsId") String goodsId){
        Map<String, String> map = goodsFeignService.setArrivalReminders(userId, goodsId);
        return ResultBuildUtil.success(map);
    }

    @ApiOperation(value = "商品补货", notes = "为商品补货之后，会向设置到货提醒的用户发送信息")
    @GetMapping("/replenishment")
    public RequestResult<Map<String, String>> replenishment(@RequestParam("goodsId")String goodsId,@RequestParam("stockNum")Integer stockNum){
        Map<String, String> map = goodsFeignService.replenishment(goodsId, stockNum);
        return ResultBuildUtil.success(map);
    }

}
