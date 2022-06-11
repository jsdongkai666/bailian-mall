package com.cuning.controller;

import com.cuning.bean.BailianGoodsInfo;
import com.cuning.service.GoodsInfoService;
import com.cuning.util.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created On : 2022/06/10.
 * <p>
 * Author     : lixu
 * <p>
 * Description: 商品足迹操作入口
 */
@Slf4j
@RestController
@Api(tags = "商品足迹操作入口")
public class GoodsFootPrintController {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private GoodsInfoService goodsInfoService;

    /**
     * @author : lixu
     * @date   : 2022/06/10
     * @param  : [javax.servlet.http.HttpServletRequest]
     * @return : java.util.List<com.cuning.bean.BailianGoodsInfo>
     * @description : 查询用户足迹
     */
    @GetMapping("/queryGoodsFootPrint")
    @ApiOperation(value = "商品足迹查询",notes = "根据用户名，查询该用户的足迹")
    public List<BailianGoodsInfo> queryGoodsFootPrint(@RequestParam("userId") String userId){

        // 获取当前用户名
        //String userId = request.getParameter("userId");

        // 从redis中获取当前用户浏览过的商品
        Set<Object> zrevrange = redisUtils.zrevrange(userId, 0, -1);


        // list集合，存放用户浏览过的商品详情
        List<BailianGoodsInfo> bailianGoodsInfoList = new ArrayList<>();

        // 获取id的set集合，并遍历
        Object[] objects = zrevrange.toArray();
        for (int i = 0; i < objects.length; i++) {
            // 通过id，查询商品详情
            BailianGoodsInfo bailianGoodsInfo = goodsInfoService.queryGoodsInfoById(Integer.valueOf(objects[i].toString()));
            bailianGoodsInfoList.add(i,bailianGoodsInfo);
            log.info("------ 商品足迹：{} ------",bailianGoodsInfo);
        }

        return bailianGoodsInfoList;
    }

    /**
     * @author : lixu
     * @date   : 2022/06/10
     * @param  : [javax.servlet.http.HttpServletRequest]
     * @return : java.util.List<com.cuning.bean.BailianGoodsInfo>
     * @description : 删除商品足迹
     */
    @PostMapping("/delGoodsHistory")
    @ApiOperation(value = "删除商品足迹",notes = "根据用户以及商品id，删除相关商品足迹")
    public String delGoodsFootPrint(HttpServletRequest request, @RequestParam List<Integer> goodsId){

        // 获取当前用户名
        String userId = request.getParameter("userId");

        // 删除商品足迹
        try {
            goodsId.forEach(id -> redisUtils.zrem(userId,id.toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "删除成功！";
    }
}
