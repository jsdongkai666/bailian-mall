package com.cuning.controller.goods;

import com.cuning.annotation.CheckToken;
import com.cuning.bean.user.User;
import com.cuning.service.GoodsFeignService;
import com.cuning.util.JwtUtil;
import com.cuning.util.PageSupport;
import com.cuning.util.RequestResult;
import com.cuning.util.ResultBuildUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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

    @CheckToken
    @ApiOperation(value = "用户设置到货提醒",notes = "传入用户id和商品id为用户设置该商品的到货提醒，商品补货时，会发送到货提醒")
    @GetMapping("/setArrivalReminders")
    public RequestResult<Map<String, String>> setArrivalRemindersByUserId(HttpServletRequest request, @RequestParam("goodsId") String goodsId) throws Exception {
        User token = JwtUtil.parseJWT(request.getHeader("token"));
        Map<String, String> map = goodsFeignService.setArrivalReminders(token.getUserId(), goodsId);
        return ResultBuildUtil.success(map);
    }

    @ApiOperation(value = "商品补货", notes = "为商品补货之后，会向设置到货提醒的用户发送信息")
    @GetMapping("/replenishment")
    public RequestResult<Map<String, String>> replenishment(@RequestParam("goodsId")String goodsId,@RequestParam("stockNum")Integer stockNum){
        Map<String, String> map = goodsFeignService.replenishment(goodsId, stockNum);
        return ResultBuildUtil.success(map);
    }

    /**
    * @Param: [javax.servlet.http.HttpServletRequest, java.lang.String]
    * @return: com.cuning.util.RequestResult<java.util.Map<java.lang.String,java.lang.String>>
    * @Author: dengteng
    * @Date: 2022/6/16
    * @Description: 用户收藏商品
    */
    @ApiOperation(value = "商品收藏", notes = "输入商品id收藏商品，再次访问取消收藏")
    @GetMapping("/collectGoods")
    public RequestResult<Map<String, String>> collectGoods(HttpServletRequest request,@RequestParam("goodsId")String goodsId) throws Exception {
        User token = JwtUtil.parseJWT(request.getHeader("token"));
        Map<String, String> map = goodsFeignService.collectGoods(token.getUserId(), goodsId);
        return ResultBuildUtil.success(map);
    }

    @ApiOperation(value = "获取用户收藏信息列表", notes = "分页获取用户收藏商品信息")
    @GetMapping("/getUserCollectList")
    public RequestResult<Map<String, PageSupport>> getUserCollectList(HttpServletRequest request,@RequestParam("pageNo")String pageNo,@RequestParam("pageSize")String pageSize) throws Exception {

        User token = JwtUtil.parseJWT(request.getHeader("token"));
        Map<String, PageSupport> userCollectList = goodsFeignService.getUserCollectList(token.getUserId(), pageNo, pageSize);
        return ResultBuildUtil.success(userCollectList);
    }


}
