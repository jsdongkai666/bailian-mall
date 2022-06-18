package com.cuning.controller;


import com.cuning.bean.goods.BailianGoodsInfo;
import com.cuning.constant.CommonConstant;
import com.cuning.constant.GoodsConstant;
import com.cuning.service.GoodsInfoService;
import com.cuning.util.RedisUtils;
import com.cuning.util.RequestResult;
import com.cuning.util.ResultBuildUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.experimental.var;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created On : 2022/06/10.
 * <p>
 * Author     : lixu
 * <p>
 * Description: 用户足迹操作入口
 */
@Slf4j
@RestController
@Api(tags = "用户足迹操作入口")
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
    @ApiOperation(value = "用户足迹查询",notes = "根据用户名，查询该用户的足迹")
    public List<BailianGoodsInfo> queryGoodsFootPrint(@RequestParam("userId") String userId){

        // 从redis中获取当前用户浏览过的商品id
        List<Object> list = new ArrayList<>(redisUtils.zrevrange(GoodsConstant.USER_FOOT_PRINT + userId , 0, -1));

        // list集合，存放用户浏览过的商品详情
        List<BailianGoodsInfo> bailianGoodsInfoList = new ArrayList<>();

        // 获取id的list集合，并遍历
        for (int i = 0; i < list.size(); i++) {
            // 通过id，查询商品详情
            BailianGoodsInfo bailianGoodsInfo = goodsInfoService.queryGoodsInfoById(list.get(i).toString());
            bailianGoodsInfoList.add(i,bailianGoodsInfo);

        }

        log.info("------ 用户：{}，商品足迹：{} ------",userId,bailianGoodsInfoList);
        return bailianGoodsInfoList;
    }

    /**
     * @author : lixu
     * @date   : 2022/06/10
     * @param  : [javax.servlet.http.HttpServletRequest]
     * @return : java.util.List<com.cuning.bean.BailianGoodsInfo>
     * @description : 删除用户足迹
     */
    @PostMapping("/delGoodsFootPrint")
    @ApiOperation(value = "删除用户足迹",notes = "根据用户以及商品id，删除用户足迹")
    public Map<String,String> delGoodsFootPrint(@RequestParam("userId")String userId, @RequestParam("goodsId") String goodsId) {


        // 返回结果集合
        Map<String,String> resultMap = new HashMap<>();

        // 判断该用户是否有足迹可清除
        if (redisUtils.zcard(GoodsConstant.USER_FOOT_PRINT + userId) == 0) {
            log.info("------ 暂无用户足迹可清除 ------");
            resultMap.put("errCode", CommonConstant.UNIFY_RETURN_FAIL_CODE);
            resultMap.put("errMsg","暂无用户足迹可清除");
            return resultMap;
        }

        // 删除商品足迹
        if(redisUtils.zrem(GoodsConstant.USER_FOOT_PRINT + userId, goodsId) >+ 0) {
            log.info("------ 用户足迹删除成功 ------");
            resultMap.put("code", CommonConstant.UNIFY_RETURN_SUCCESS_CODE);
            resultMap.put("msg","用户足迹删除成功");
            return resultMap;
        }

        // 删除失败
        log.info("------ 用户足迹删除失败 ------");
        resultMap.put("errCode", CommonConstant.UNIFY_RETURN_FAIL_CODE);
        resultMap.put("errMsg","用户足迹删除失败");
        return resultMap;
    }

    /**
     * @author : lixu
     * @date   : 2022/06/18
     * @param  : [java.lang.String]
     * @return : java.util.Map<java.lang.String,java.lang.String>
     * @description : 一键清空用户足迹
     */
    @PostMapping("/clearGoodsFootPrint")
    @ApiOperation(value = "清除用户足迹",notes = "将所有的用户足迹清除")
    public Map<String,String> clearGoodsFootPrint(@RequestParam("userId")String userId) {

        // 返回结果集合
        Map<String,String> resultMap = new HashMap<>();

        // 判断该用户是否有足迹可清除
        if (redisUtils.zcard(GoodsConstant.USER_FOOT_PRINT + userId) == 0) {
            log.info("------ 暂无用户足迹可清除 ------");
            resultMap.put("errCode", CommonConstant.UNIFY_RETURN_FAIL_CODE);
            resultMap.put("errMsg","暂无用户足迹可清除");
            return resultMap;
        }

        // 清除该用户所有的足迹
        if (redisUtils.zremoveRange(GoodsConstant.USER_FOOT_PRINT + userId,0,-1) > 0) {
            log.info("------ 用户足迹清除成功 ------");
            resultMap.put("code", CommonConstant.UNIFY_RETURN_SUCCESS_CODE);
            resultMap.put("msg","用户足迹清除成功");
            return resultMap;
        }

        // 清除失败
        log.info("------ 用户足迹清除失败 ------");
        resultMap.put("errCode", CommonConstant.UNIFY_RETURN_FAIL_CODE);
        resultMap.put("errMsg","用户足迹清除失败");
        return resultMap;
    }
}
