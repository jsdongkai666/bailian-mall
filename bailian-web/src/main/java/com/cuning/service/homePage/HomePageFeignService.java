package com.cuning.service.homePage;

import com.cuning.bean.BailianCarousel;
import com.cuning.util.RequestResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created On : 2022/06/14.
 * <p>
 * Author     : lixu
 * <p>
 * Description: HomePageService
 */
@FeignClient(value = "bailian-homepage")
public interface HomePageFeignService {

    @ApiOperation(value = "查询轮播图",notes = "根据id，查询轮播图详情")
    @GetMapping("/queryCarousel")
    RequestResult<List<BailianCarousel>> queryCarousel(@RequestParam(required = false,defaultValue = "0") Integer rank);


}
