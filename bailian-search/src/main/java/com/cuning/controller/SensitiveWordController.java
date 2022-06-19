package com.cuning.controller;


import com.cuning.bean.BailianGoodsInfo;
import com.cuning.bean.SensitiveWord;
import com.cuning.bean.User;
import com.cuning.constant.GoodsConstant;
import com.cuning.service.SensitiveWordService;
import com.cuning.util.EsUtil;
import com.cuning.util.JwtUtil;
import com.cuning.util.RedisUtils;
import com.cuning.util.SensitiveWordFilterUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
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
    private EsUtil esUtil;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private SensitiveWordService sensitiveWordService;


    @PostMapping("/delEs")
    @ApiOperation(value = "删除Es")
    public String delEs() throws IOException {
        esUtil.deleteIndex();
        return "success";
    }

    @PostMapping("/delData")
    @ApiOperation(value = "删除数据")
    public String delData(@RequestParam("id") String id) throws IOException {
        esUtil.deleteBulk(id);
        return "success";
    }


    @GetMapping("/insertEs")
    @ApiOperation(value = "插入es", notes = "从数据库导入es")
    public String insertEs() throws IOException {
        esUtil.bulkRequest();
        return "success";
    }

    @PostMapping("/insertData")
    @ApiOperation(value = "插入数据", notes = "向es插入数据")
    public String insertData(@RequestBody BailianGoodsInfo bailianGoodsInfo) throws IOException {
        esUtil.insertBulk(bailianGoodsInfo);
        return "success";
    }

    @PostMapping("/updateData")
    @ApiOperation(value = "更新数据", notes = "向es更新数据")
    public String updateData(@RequestBody BailianGoodsInfo bailianGoodsInfo) throws IOException {
        esUtil.updateDocument(bailianGoodsInfo);
        return "success";
    }



    /**
     * @author : zhukang
     * @date   : 2022/5/17
     * @param  : [java.lang.String]
     * @return : com.kgc.sbt.util.RequestResult<java.lang.String>
     * @description : 测试搜索中的敏感词
     */
    @GetMapping("/testSensitiveWord")
    @ApiOperation(value = "敏感词", notes = "搜索关键字中，增加敏感词过来，限制搜索")
    public  List<Map<String, Object>> testSensitiveWord(HttpServletRequest request, @RequestParam String searchKey) throws Exception {
        User user = JwtUtil.parseJWT(request.getHeader("token"));
        List<Map<String, Object>> shopSearch = new ArrayList<>();
        assert user != null;
        String userId = user.getUserId();
        Map<String, Object> result = new HashMap<>();
        // 判断用户是否异常
        if (userId == null || userId.equals("")) {
            result.put("code","500");
            result.put("msg","用户数据异常，请重新登录！");
            shopSearch.add(result);
            return shopSearch;
        }
        // 校验搜索关键字中，是否包含敏感词，如果包含，提示错误
        if(sensitiveWordFilterUtil.isContainSensitiveWord(searchKey)){
            log.warn("------ 命中敏感词，搜索关键字：{} ------", searchKey);
            result.put("code","301");
            result.put("msg","搜索失败，命中敏感词");
            shopSearch.add(result);
            return shopSearch;
            // return "搜索失败，命中敏感词";
        }
        shopSearch = esUtil.search(searchKey, 1, 20);
        System.out.println(shopSearch);
        if(shopSearch == null||shopSearch.size()==0){
            result.put("code","201");
            result.put("msg","搜索失败，无该商品");
            shopSearch.add(result);
            return shopSearch;
        }

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


        return shopSearch;
    }

    @PostMapping("/insertSensitiveWords")
    @ApiOperation(value = "插入敏感词")
    public Map<String,Object> insertSensitiveWords(@RequestBody SensitiveWord sensitiveWords) {
        Map<String,Object> map = new HashMap<>();
        if(sensitiveWordService.save(sensitiveWords)){
            map.put("code",200);
            map.put("msg","插入成功");
            return map;
        }
        map.put("code",500);
        map.put("msg","插入失败");
        return map;
    }



}
