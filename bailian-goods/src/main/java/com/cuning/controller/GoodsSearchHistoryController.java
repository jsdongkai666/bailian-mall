package com.cuning.controller;

import com.cuning.constant.GoodsConstant;
import com.cuning.util.RedisUtils;
import com.cuning.util.RequestResult;
import com.cuning.util.ResultBuildUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


/**
 * Created On : 2022/06/13.
 * <p>
 * Author     : lixu
 * <p>
 * Description: 商品搜索历史记录操作入口
 */
@Slf4j
@RestController
@Api(tags = "商品搜索记录操作入口")
public class GoodsSearchHistoryController {

    @Autowired
    private RedisUtils redisUtils;


    @GetMapping("/SearchHistory")
    @ApiOperation(value = "查询搜索记录",notes = "从redis中取出存好的搜索记录，一共展示十条数据")
    public RequestResult<List<Object>> searchHistory(@RequestParam("userId") String userId){

        // 搜索记录列表
        List<Object> searchHistoryList = new ArrayList<>(redisUtils.zrevrange(GoodsConstant.GOODS_SEARCH_HISTORY + userId, 0, -1));

        log.info("------ 用户：{}，搜索记录：{} ------",userId,searchHistoryList);

        return ResultBuildUtil.success(searchHistoryList);
    }

    @PostMapping("/delSearchHistory")
    @ApiOperation(value = "删除搜索记录",notes = "将搜索记录清空")
    public RequestResult<String> delSearchHistory(@RequestParam("userId")String userId){

        // 清除搜索记录
        try {
            redisUtils.zremoveRange(GoodsConstant.GOODS_SEARCH_HISTORY + userId,0,-1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        log.info("------ 搜索记录清除成功 ------");

        return ResultBuildUtil.success("删除成功！");
    }
}
