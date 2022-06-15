package com.cuning.controller;


import com.cuning.constant.GoodsConstant;
import com.cuning.util.EsUtil;
import com.cuning.util.RedisUtils;
import com.cuning.util.SensitiveWordFilterUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created On : 2022/5/17.
 * <p>
 * Author : zhukang
 * <p>
 * Description: 敏感词测试入口
 */
@Slf4j
@RestController
@Api(tags = "敏感词测试")
public class SensitiveWordController {

    @Autowired
    private SensitiveWordFilterUtil sensitiveWordFilterUtil;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private EsUtil esUtil;

    /**
     * @author : zhukang
     * @date   : 2022/5/17
     * @param  : [java.lang.String]
     * @return : com.kgc.sbt.util.RequestResult<java.lang.String>
     * @description : 测试搜索中的敏感词
     */
    @GetMapping("/testSensitiveWord")
    @ApiOperation(value = "敏感词过滤", notes = "搜索关键字中，增加敏感词过来，限制搜索")
    public  String testSensitiveWord(@RequestParam("userId") String userId,@RequestParam String searchKey) throws IOException {

        // 校验搜索关键字中，是否包含敏感词，如果包含，提示错误
        if(sensitiveWordFilterUtil.isContainSensitiveWord(searchKey)){
            log.warn("------ 命中敏感词，搜索关键字：{} ------", searchKey);
            return "搜索失败，命中敏感词";
        }
        if(esUtil.indexIsExists()){
            esUtil.deleteIndex();
        }
        esUtil.bulkRequest();
        List<Map<String, Object>> shopSearch = esUtil.search(searchKey,1,3);

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

        return shopSearch.toString();
    }


}
