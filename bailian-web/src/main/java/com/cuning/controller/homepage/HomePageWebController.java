package com.cuning.controller.homepage;

import com.cuning.annotation.CheckToken;
import com.cuning.bean.BailianCarousel;
import com.cuning.bean.goods.BailianGoodsInfo;
import com.cuning.bean.user.User;
import com.cuning.service.goods.GoodsFeignService;
import com.cuning.service.homePage.HomePageFeignService;
import com.cuning.util.JwtUtil;
import com.cuning.util.RequestResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    public RequestResult<List<BailianCarousel>> queryCarouselList(@ApiParam(name = "rank",value = "排序",defaultValue = "0")@RequestParam(name = "rank",required = false,defaultValue = "0") Integer rank){
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
    public RequestResult<String> delCarousel(@ApiParam(name = "ids",value = "轮播图id")@RequestParam("ids") List<String> ids){
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
    @CheckToken
    public RequestResult<BailianGoodsInfo> goodsDetailsMap(HttpServletRequest request,
                                                           @ApiParam(name = "goodsId",value = "商品id")@RequestParam("goodsId") String goodsId) throws Exception {
        User user = JwtUtil.parseJWT(request.getHeader("token"));
        assert user != null;
        String userId = user.getUserId();
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
    @CheckToken
    public List<BailianGoodsInfo> queryGoodsFootPrint(HttpServletRequest request) throws Exception {
        User user = JwtUtil.parseJWT(request.getHeader("token"));
        assert user != null;
        String userId = user.getUserId();
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
    @CheckToken
    public RequestResult<String> delGoodsFootPrint(HttpServletRequest request,
                                                   @ApiParam(name = "goodsId",value = "商品id")@RequestParam("goodsId") List<String> goodsId) throws Exception {
        User user = JwtUtil.parseJWT(request.getHeader("token"));
        assert user != null;
        String userId = user.getUserId();
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
    @CheckToken
    public RequestResult<List<BailianGoodsInfo>> GoodsRelated(HttpServletRequest request) throws Exception {
        User user = JwtUtil.parseJWT(request.getHeader("token"));
        assert user != null;
        String userId = user.getUserId();
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
    @CheckToken
    public RequestResult<List<Object>> searchHistory(HttpServletRequest request,
                                                     @ApiParam(name = "searchKey",value = "搜索关键字")@RequestParam("searchKey") String searchKey) throws Exception {
        User user = JwtUtil.parseJWT(request.getHeader("token"));
        assert user != null;
        String userId = user.getUserId();
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
    @CheckToken
    public RequestResult<String> delSearchHistory(HttpServletRequest request) throws Exception {
        User user = JwtUtil.parseJWT(request.getHeader("token"));
        assert user != null;
        String userId = user.getUserId();
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
