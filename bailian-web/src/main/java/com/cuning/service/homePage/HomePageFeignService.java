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

    @GetMapping("/queryCarousel")
    List<BailianCarousel> queryCarousel(@RequestParam(name = "rank",required = false,defaultValue = "0") Integer rank);

    @PostMapping("/addCarousel")
    boolean addCarousel(@RequestBody BailianCarousel bailianCarousel,@RequestParam("userId") String userId);

    @PostMapping("/delCarousel")
    boolean delCarousel(@RequestBody BailianCarousel bailianCarousel);

    @PostMapping("/modCarousel")
    boolean modCarousel(@RequestBody BailianCarousel bailianCarousel,@RequestParam("userId") String userId);

    @GetMapping("/goodsRelated")
    List<BailianGoodsInfo> GoodsRelated(@RequestParam("userId") String userId);


}
