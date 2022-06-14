package com.cuning.controller.homepage;

import com.cuning.bean.BailianCarousel;
import com.cuning.bean.goods.BailianGoodsInfo;
import com.cuning.service.goods.GoodsFeignService;
import com.cuning.service.homePage.HomePageFeignService;
import com.cuning.util.RequestResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created On : 2022/06/14.
 * <p>
 * Author     : lixu
 * <p>
 * Description: 首页操作入口
 */
@Slf4j
@RestController
@Api(tags = "首页前端操作入口")
public class HomePageWebController {

    @Autowired(required = false)
    private HomePageFeignService homePageFeignService;

    @Autowired(required = false)
    private GoodsFeignService goodsFeignService;

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

    /**
     * @author : lixu
     * @date   : 2022/06/14
     * @param  : [com.cuning.bean.BailianCarousel]
     * @return : com.cuning.util.RequestResult<java.util.Map<java.lang.String,java.lang.String>>
     * @description : 添加轮播图
     */
    @PostMapping("/addCarousel")
    @ApiOperation(value = "添加轮播图",notes = "添加轮播图详情")
    public RequestResult<String> addCarousel(@RequestBody BailianCarousel bailianCarousel){
        return homePageFeignService.addCarousel(bailianCarousel);
    }

    /**
     * @author : lixu
     * @date   : 2022/06/14
     * @param  : [java.util.List<java.lang.Integer>]
     * @return : com.cuning.util.RequestResult<java.util.Map<java.lang.String,java.lang.String>>
     * @description : 删除轮播图
     */
    @PostMapping("/delCarousel")
    @ApiOperation(value = "删除轮播图",notes = "批量删除轮播图详情")
    public RequestResult<String> delCarousel(@RequestParam List<String> ids){
        return homePageFeignService.delCarousel(ids);
    }

    /**
     * @author : lixu
     * @date   : 2022/06/14
     * @param  : [com.cuning.bean.BailianCarousel]
     * @return : com.cuning.util.RequestResult<java.util.Map<java.lang.String,java.lang.String>>
     * @description : 修改轮播图
     */
    @PostMapping("/modCarousel")
    @ApiOperation(value = "修改轮播图",notes = "根据id，修改轮播图详情")
    public RequestResult<String> modCarousel(@RequestBody BailianCarousel bailianCarousel){
        return homePageFeignService.modCarousel(bailianCarousel);
    }

    /**
     * @author : lixu
     * @date   : 2022/06/14
     * @param  : [java.lang.String, java.lang.Integer]
     * @return : com.cuning.util.RequestResult<com.cuning.bean.goods.BailianGoodsInfo>
     * @description : 商品详情查询
     */
    @ApiOperation(value = "商品详情查询",notes = "根据id，查询商品详情，将结果存入redis")
    @GetMapping("/goodsDetails")
    public RequestResult<BailianGoodsInfo> goodsDetailsMap(@RequestParam("userId") String userId, @RequestParam("goodsId") Integer goodsId){
        return goodsFeignService.goodsDetailsMap(userId,goodsId);
    }

    /**
     * @author : lixu
     * @date   : 2022/06/14
     * @param  : [java.lang.String]
     * @return : com.cuning.util.RequestResult<java.util.List<com.cuning.bean.goods.BailianGoodsInfo>>
     * @description : 用户足迹查询
     */
    @GetMapping("/queryGoodsFootPrint")
    @ApiOperation(value = "用户足迹查询",notes = "根据用户名，查询该用户的足迹")
    public List<BailianGoodsInfo> queryGoodsFootPrint(@RequestParam("userId") String userId){
        return goodsFeignService.queryGoodsFootPrint(userId);
    }

    /**
     * @author : lixu
     * @date   : 2022/06/14
     * @param  : [java.lang.String, java.util.List<java.lang.Integer>]
     * @return : com.cuning.util.RequestResult<java.lang.String>
     * @description : 删除用户足迹
     */
    @PostMapping("/delGoodsFootPrint")
    @ApiOperation(value = "删除用户足迹",notes = "根据用户以及商品id，删除用户足迹")
    public RequestResult<String> delGoodsFootPrint(@RequestParam("userId")String userId, @RequestParam("goodsId") List<Integer> goodsId){
        return goodsFeignService.delGoodsFootPrint(userId, goodsId);
    }


    /**
     * @author : lixu
     * @date   : 2022/06/14
     * @param  : [java.lang.String]
     * @return : com.cuning.util.RequestResult<java.util.List<com.cuning.bean.goods.BailianGoodsInfo>>
     * @description : 猜你喜欢
     */
    @GetMapping("/goodsRelated")
    @ApiOperation(value = "猜你喜欢",notes = "根据用户的足迹，猜你喜欢")
    public RequestResult<List<BailianGoodsInfo>> GoodsRelated(@RequestParam("userId")String userId){
        return homePageFeignService.GoodsRelated(userId);
    }

    /**
     * @author : lixu
     * @date   : 2022/06/14
     * @param  : [java.lang.String, java.lang.String]
     * @return : com.cuning.util.RequestResult<java.util.List<java.lang.Object>>
     * @description : 搜索记录
     */
    @GetMapping("/SearchHistory")
    @ApiOperation(value = "商品搜索记录",notes = "将商品搜索记录存入redis中，再次搜索将靠前显示，一共展示十条数据")
    public RequestResult<List<Object>> searchHistory(@RequestParam("userId") String userId,@RequestParam("searchKey") String searchKey){
        return goodsFeignService.searchHistory(userId, searchKey);
    }

    /**
     * @author : lixu
     * @date   : 2022/06/14
     * @param  : [java.lang.String]
     * @return : com.cuning.util.RequestResult<java.util.Map<java.lang.String,java.lang.String>>
     * @description : 删除搜索记录
     */
    @PostMapping("/delSearchHistory")
    @ApiOperation(value = "删除搜索记录",notes = "将搜索记录清空")
    public RequestResult<String> delSearchHistory(@RequestParam("userId")String userId){
        return goodsFeignService.delSearchHistory(userId);
    }

    /**
     * @author : lixu
     * @date   : 2022/06/14
     * @param  : []
     * @return : com.cuning.util.RequestResult<java.util.List<java.lang.Object>>
     * @description : 热词
     */
    @GetMapping("/printHotWord")
    @ApiOperation(value = "热词",notes = "展示十条热词记录")
    RequestResult<List<Object>> printHotWord(){
        return goodsFeignService.printHotWord();
    }


}
