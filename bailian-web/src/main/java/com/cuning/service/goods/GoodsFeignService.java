package com.cuning.service.goods;

import com.cuning.bean.goods.BailianGoodsInfo;
import com.cuning.util.RequestResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * Created On : 2022/06/14.
 * <p>
 * Author     : lixu
 * <p>
 * Description: 商品远程调用接口
 */
@FeignClient(value = "bailian-goods")
public interface GoodsFeignService {

    @ApiOperation(value = "商品详情查询",notes = "根据id，查询商品详情，将结果存入redis")
    @GetMapping("/goodsDetails")
    RequestResult<BailianGoodsInfo> goodsDetailsMap(@RequestParam("userId") String userId, @RequestParam("goodsId") Integer goodsId);

    @PostMapping("/delGoodsFootPrint")
    @ApiOperation(value = "删除用户足迹",notes = "根据用户以及商品id，删除用户足迹")
    RequestResult<String> delGoodsFootPrint(@RequestParam("userId")String userId, @RequestParam("goodsId") List<Integer> goodsId);

    @GetMapping("/queryGoodsFootPrint")
    @ApiOperation(value = "用户足迹查询",notes = "根据用户名，查询该用户的足迹")
    List<BailianGoodsInfo> queryGoodsFootPrint(@RequestParam("userId") String userId);

    @GetMapping("/SearchHistory")
    @ApiOperation(value = "商品搜索记录",notes = "将商品搜索记录存入redis中，再次搜索将靠前显示，一共展示十条数据")
    RequestResult<List<Object>> searchHistory(@RequestParam("userId") String userId,@RequestParam("searchKey") String searchKey);

    @PostMapping("/delSearchHistory")
    @ApiOperation(value = "删除搜索记录",notes = "将搜索记录清空")
    RequestResult<String> delSearchHistory(@RequestParam("userId")String userId);

    @GetMapping("/printHotWord")
    @ApiOperation(value = "热词",notes = "展示十条热词记录")
    RequestResult<List<Object>> printHotWord();
}
