package com.cuning.service.goods;

import com.cuning.bean.goods.BailianGoodsInfo;
import com.cuning.util.RequestResult;
import com.cuning.vo.GoodsDetailsVO;
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

    @GetMapping("/goodsDetails")
    Map<String,Object> goodsDetailsMap(@RequestParam("userId") String userId, @RequestParam("goodsId") String goodsId);

    @PostMapping("/delGoodsFootPrint")
    Map<String,String> delGoodsFootPrint(@RequestParam("userId")String userId, @RequestParam("goodsId") String goodsId);

    @PostMapping("/clearGoodsFootPrint")
    Map<String,String> clearGoodsFootPrint(@RequestParam("userId")String userId);

    @GetMapping("/queryGoodsFootPrint")
    List<BailianGoodsInfo> queryGoodsFootPrint(@RequestParam("userId") String userId);

    @GetMapping("/searchHistory")
    List<Object> searchHistory(@RequestParam("userId") String userId,@RequestParam("searchKey") String searchKey);

    @PostMapping("/delSearchHistory")
    Map<String,String> delSearchHistory(@RequestParam("userId")String userId);

    @GetMapping("/printHotWord")
    List<Object> printHotWord();

    @GetMapping("/queryGoodsIdList")
    List<String> queryGoodsIdList();
}
