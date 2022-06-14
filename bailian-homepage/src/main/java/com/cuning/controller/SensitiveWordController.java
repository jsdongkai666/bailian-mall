package com.cuning.controller;




import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cuning.bean.goods.BailianGoodsInfo;
import com.cuning.bean.shoppingOrder.BailianOrder;
import com.cuning.service.BalianGoodsService;
import com.cuning.util.RequestResult;
import com.cuning.util.ResultBuildUtil;
import com.cuning.util.SensitiveWordFilterUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    private BalianGoodsService balianGoodsService;

    /**
     * @author : zhukang
     * @date   : 2022/5/17
     * @param  : [java.lang.String]
     * @return : com.kgc.sbt.util.RequestResult<java.lang.String>
     * @description : 测试搜索中的敏感词
     */
    @GetMapping("/testSensitiveWord")
    @ApiOperation(value = "敏感词过滤", notes = "搜索关键字中，增加敏感词过来，限制搜索")
    public RequestResult<List<BailianGoodsInfo>> testSensitiveWord(@RequestParam String searchKey){

        // 校验搜索关键字中，是否包含敏感词，如果包含，提示错误
        if(sensitiveWordFilterUtil.isContainSensitiveWord(searchKey)){
            log.warn("------ 命中敏感词，搜索关键字：{} ------", searchKey);
            return ResultBuildUtil.fail();
        }
        IPage<BailianGoodsInfo> pageParam =balianGoodsService.invokeQueryGoodsInfoPage(1,3,searchKey);


        return ResultBuildUtil.success(pageParam.getRecords());
    }



}
