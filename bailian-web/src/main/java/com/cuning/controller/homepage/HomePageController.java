package com.cuning.controller.homepage;

import com.cuning.bean.BailianCarousel;
import com.cuning.service.homePage.HomePageFeignService;
import com.cuning.util.RequestResult;
import com.cuning.util.ResultBuildUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created On : 2022/06/14.
 * <p>
 * Author     : lixu
 * <p>
 * Description: 首页操作入口
 */
@Slf4j
@RestController
public class HomePageController {

    @Autowired
    private HomePageFeignService homePageFeignService;

    /**
     * @author : lixu
     * @date   : 2022/06/14
     * @param  : [java.lang.Integer]
     * @return : java.util.List<com.cuning.bean.BailianCarousel>
     * @description : 查询轮播图
     */
    @ApiOperation(value = "查询轮播图",notes = "根据id，查询轮播图详情")
    @GetMapping("/queryCarousel")
    public RequestResult<List<BailianCarousel>> queryCarouselList(@RequestParam(required = false,defaultValue = "0") Integer rank){
        return homePageFeignService.queryCarousel(rank);
    }


}
