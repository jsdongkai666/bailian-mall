package com.cuning.controller;


import com.alibaba.fastjson.JSON;
import com.cuning.bean.BailianGoodsInfo;
import com.cuning.util.EsUtil;
import com.cuning.util.SensitiveWordFilterUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
    public  List<Map<String, Object>> testSensitiveWord(@RequestParam String searchKey) throws IOException {
        List<Map<String, Object>> shopSearch = new ArrayList<>();
        // 校验搜索关键字中，是否包含敏感词，如果包含，提示错误
        if(sensitiveWordFilterUtil.isContainSensitiveWord(searchKey)){
            log.warn("------ 命中敏感词，搜索关键字：{} ------", searchKey);
            Map<String, Object> result = new HashMap<>();
            result.put("code","301");
            result.put("msg","搜索失败，命中敏感词");
            shopSearch.add(result);
            return shopSearch;
            // return "搜索失败，命中敏感词";
        }
           shopSearch = esUtil.search(searchKey, 1, 20);
        return shopSearch;
    }


}
