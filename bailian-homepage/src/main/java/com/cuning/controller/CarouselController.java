package com.cuning.controller;

import com.cuning.bean.bailianCarousel;
import com.cuning.service.CarouselService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.sql.Wrapper;
import java.util.List;

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
    @ApiOperation(value = "查询轮播图",notes = "查询轮播图详情")
    @GetMapping("/queryCarousel")
    public List<bailianCarousel> queryCarousel(){

        // 调用接口查询轮播图详情
        List<bailianCarousel> carouselList = carouselService.list();
        log.info("------ 轮播图详情：{} ------",carouselList);

        return carouselList;
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
    public String addCarousel(@RequestBody bailianCarousel bailianCarousel){

        // 调用接口添加轮播图
        if (carouselService.save(bailianCarousel)){
            return "添加成功";
        }

        return "添加失败";
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
    public String delCarousel(@RequestParam List<Integer> ids){

        // 调用接口删除轮播图
        if (carouselService.removeByIds(ids)){
            return "删除成功";
        }

        return "删除失败";
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
    public String modCarousel(@RequestBody bailianCarousel bailianCarousel){

        // 调用接口修改轮播图
        if (carouselService.updateById(bailianCarousel)){
            return "修改成功";
        }

        return "修改失败";
    }
}
