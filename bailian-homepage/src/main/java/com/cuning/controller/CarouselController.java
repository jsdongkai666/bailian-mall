package com.cuning.controller;


import com.cuning.bean.BailianCarousel;
import com.cuning.constant.CommonConstant;
import com.cuning.service.CarouselService;
import com.cuning.util.RequestResult;
import com.cuning.util.ResultBuildUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * Created On : 2022/06/09.
 * <p>
 * Author     : lixu
 * <p>
 * Description: 轮播图操作入口
 */
@Slf4j
@RestController
@Api(tags = "轮播图操作入口")
public class CarouselController {

    @Autowired(required = false)
    private CarouselService carouselService;

    /**
     * @author : lixu
     * @date   : 2022/06/09
     * @param  : []
     * @return : java.util.List<com.cuning.bean.Carousel>
     * @description : 查询轮播图
     */
    @ApiOperation(value = "查询轮播图",notes = "根据id，查询轮播图详情")
    @GetMapping("/queryCarousel")
    public RequestResult<List<BailianCarousel>> queryCarousel(@RequestParam(required = false,defaultValue = "0") Integer rank){

        // 调用接口查询轮播图详情
        List<BailianCarousel> carouselList = carouselService.selectCarouselList(rank);
        log.info("------ 轮播图详情：{} ------",carouselList);

        return ResultBuildUtil.success(carouselList);
    }

    /**
     * @author : lixu
     * @date   : 2022/06/09
     * @param  : [com.cuning.bean.bailianCarousel]
     * @return : void
     * @description : 添加轮播图
     */
    @PostMapping("/addCarousel")
    @ApiOperation(value = "添加轮播图",notes = "添加轮播图详情")
    public RequestResult<Map<String,String>> addCarousel(@RequestBody BailianCarousel bailianCarousel){

        // 返回结果Map集合
        Map<String,String> resultMap = new HashMap<>();

        // 调用接口添加轮播图
        if (carouselService.addCarousel(bailianCarousel)){
            resultMap.put("code", CommonConstant.UNIFY_RETURN_SUCCESS_CODE);
            resultMap.put("Msg",CommonConstant.UNIFY_RETURN_SUCCESS_MSG);
            return ResultBuildUtil.success(resultMap);
        }
        resultMap.put("code",CommonConstant.UNIFY_RETURN_FAIL_CODE);
        resultMap.put("Msg",CommonConstant.UNIFY_RETURN_FAIL_MSG);
        return ResultBuildUtil.fail(resultMap);
    }

    /**
     * @author : lixu
     * @date   : 2022/06/09
     * @param  : [com.cuning.bean.bailianCarousel]
     * @return : void
     * @description : 删除轮播图
     */
    @PostMapping("/delCarousel")
    @ApiOperation(value = "删除轮播图",notes = "批量删除轮播图详情")
    public RequestResult<Map<String,String>> delCarousel(@RequestParam List<Integer> ids){

        // 返回结果Map集合
        Map<String,String> resultMap = new HashMap<>();

        // 调用接口删除轮播图
        if (carouselService.deleteCarousel(ids)){
            resultMap.put("code", CommonConstant.UNIFY_RETURN_SUCCESS_CODE);
            resultMap.put("Msg",CommonConstant.UNIFY_RETURN_SUCCESS_MSG);
            return ResultBuildUtil.success(resultMap);
        }

        resultMap.put("code",CommonConstant.UNIFY_RETURN_FAIL_CODE);
        resultMap.put("Msg",CommonConstant.UNIFY_RETURN_FAIL_MSG);
        return ResultBuildUtil.fail(resultMap);
    }

    /**
     * @author : lixu
     * @date   : 2022/06/09
     * @param  : [com.cuning.bean.bailianCarousel]
     * @return : void
     * @description : 修改轮播图
     */
    @PostMapping("/modCarousel")
    @ApiOperation(value = "修改轮播图",notes = "根据id，修改轮播图详情")
    public RequestResult<Map<String,String>> modCarousel(@RequestBody BailianCarousel bailianCarousel){

        // 返回结果Map集合
        Map<String,String> resultMap = new HashMap<>();

        // 调用接口修改轮播图
        if (carouselService.updateCarousel(bailianCarousel)){
            resultMap.put("code", CommonConstant.UNIFY_RETURN_SUCCESS_CODE);
            resultMap.put("Msg",CommonConstant.UNIFY_RETURN_SUCCESS_MSG);
            return ResultBuildUtil.success(resultMap);
        }

        resultMap.put("code",CommonConstant.UNIFY_RETURN_FAIL_CODE);
        resultMap.put("Msg",CommonConstant.UNIFY_RETURN_FAIL_MSG);
        return ResultBuildUtil.fail(resultMap);
    }

}
