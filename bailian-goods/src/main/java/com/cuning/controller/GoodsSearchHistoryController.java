package com.cuning.controller;

import com.cuning.bean.goods.BailianGoodsInfo;
import com.cuning.constant.CommonConstant;
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

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    @ApiOperation(value = "商品搜索记录",notes = "将商品搜索记录存入redis中，再次搜索将靠前显示，一共展示十条数据")
    public RequestResult<List<Object>> searchHistory(@RequestParam("userId") String userId,
                                     @RequestParam("searchKey") String searchKey){

        // 将时间设为权重值
        String score = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        // 将该用户的搜索记录存入redis中，并设置权重
        redisUtils.zadd(GoodsConstant.GOODS_SEARCH_HISTORY + userId,searchKey, Double.parseDouble(score));

        // 无视用户，将搜索记录存入热词中
        redisUtils.zadd(GoodsConstant.GOODS_HOT_WORDS,searchKey, redisUtils.zincrby(GoodsConstant.GOODS_HOT_WORDS,searchKey,1));

        // 搜索记录列表
        List<Object> searchHistoryList = new ArrayList<>(redisUtils.zrevrange(GoodsConstant.GOODS_SEARCH_HISTORY + userId, 0, -1));


        // zcard返回成员个数
        if(redisUtils.zcard(GoodsConstant.GOODS_SEARCH_HISTORY + userId) > 10) {
            // 数量满10，将权重值最低的删除
            redisUtils.zremoveRange(GoodsConstant.GOODS_SEARCH_HISTORY + userId, 0, 0);
            searchHistoryList.remove(searchHistoryList.size() - 1);
        }

        log.info("------ 用户：{}，搜索记录：{} ------",userId,searchHistoryList);

        return ResultBuildUtil.success(searchHistoryList);
    }

    @PostMapping("/delSearchHistory")
    @ApiOperation(value = "删除搜索记录",notes = "将搜索记录清空")
    public RequestResult<String> delSearchHistory(@RequestParam("userId")String userId){

        // 返回结果Map集合
        Map<String,String> resultMap = new HashMap<>();

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
