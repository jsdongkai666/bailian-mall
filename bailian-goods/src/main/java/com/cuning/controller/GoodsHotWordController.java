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
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created On : 2022/06/14.
 * <p>
 * Author     : lixu
 * <p>
 * Description: 热词操作入口
 */
@Slf4j
@RestController
@Api(tags = "热词操作入口")
public class GoodsHotWordController {

    @Autowired
    private RedisUtils redisUtils;

    /**
     * @author : lixu
     * @date   : 2022/06/15
     * @param  : []
     * @return : com.cuning.util.RequestResult<java.util.List<java.lang.Object>>
     * @description : 查询热词
     */
    @GetMapping("/printHotWord")
    @ApiOperation(value = "热词",notes = "展示十条热词记录")
    public List<Object> printHotWord(){

        // 从redis中取出十条热词
        ArrayList<Object> hotWordList = new ArrayList<>(redisUtils.zrevrange(GoodsConstant.GOODS_HOT_WORDS, 0, 9));
        log.info("------ 热词：{} ------",hotWordList);

        // 返回热词列表
        return hotWordList;
    }
}
