package com.cuning.service.homePage;

import com.cuning.bean.BailianCarousel;
import com.cuning.bean.goods.BailianGoodsInfo;
import com.cuning.util.RequestResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * Created On : 2022/06/14.
 * <p>
 * Author     : lixu
 * <p>
 * Description: 主页远程调用接口
 */
@FeignClient(value = "bailian-homepage")
public interface HomePageFeignService {

    @ApiOperation(value = "查询轮播图",notes = "根据id，查询轮播图详情")
    @GetMapping("/queryCarousel")
    RequestResult<List<BailianCarousel>> queryCarousel(@RequestParam(name = "rank",required = false,defaultValue = "0") Integer rank);

    @PostMapping("/addCarousel")
    @ApiOperation(value = "添加轮播图",notes = "添加轮播图详情")
    RequestResult<String> addCarousel(@RequestBody BailianCarousel bailianCarousel);

    @PostMapping("/delCarousel")
    @ApiOperation(value = "删除轮播图",notes = "批量删除轮播图详情")
    RequestResult<String> delCarousel(@RequestParam("ids") List<String> id);

    @PostMapping("/modCarousel")
    @ApiOperation(value = "修改轮播图",notes = "根据id，修改轮播图详情")
    RequestResult<String> modCarousel(@RequestBody BailianCarousel bailianCarousel);

    @GetMapping("/goodsRelated")
    @ApiOperation(value = "猜你喜欢",notes = "根据用户的足迹，猜你喜欢")
    RequestResult<List<BailianGoodsInfo>> GoodsRelated(@RequestParam("userId") String userId);



}
