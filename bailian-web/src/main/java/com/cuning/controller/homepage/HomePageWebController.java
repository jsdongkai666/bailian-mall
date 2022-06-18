package com.cuning.controller.homepage;

import com.cuning.annotation.CheckToken;
import com.cuning.bean.BailianCarousel;
import com.cuning.bean.goods.BailianGoodsInfo;
import com.cuning.bean.user.User;
import com.cuning.service.GoodsInfoFeignService;
import com.cuning.service.goods.GoodsFeignService;
import com.cuning.service.homePage.HomePageFeignService;
import com.cuning.util.JwtUtil;
import com.cuning.util.RequestResult;
import com.cuning.util.ResultBuildUtil;
import com.cuning.vo.GoodsDetailsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created On : 2022/06/14.
 * <p>
 * Author     : lixu
 * <p>
 * Description: 首页操作入口
 */
@Slf4j
@RestController
@Api(tags = "首页操作入口")
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

    public RequestResult queryCarouselList(@ApiParam(name = "rank",value = "排序",defaultValue = "0")@RequestParam(name = "rank",required = false,defaultValue = "0") Integer rank){

        // 查询轮播图列表
        List<BailianCarousel> bailianCarousels = homePageFeignService.queryCarousel(rank);

        // 判断轮播图是否为空
        if (bailianCarousels.isEmpty()) {
            return ResultBuildUtil.fail("没有查询到轮播图！");
        }

        return ResultBuildUtil.success(bailianCarousels);
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
    @CheckToken
    public RequestResult<String> addCarousel(HttpServletRequest request,
                                     @ApiParam(name = "carouselUrl",value = "轮播图图片地址")@RequestParam(value = "carouselUrl",required = false) String carouselUrl,
                                     @ApiParam(name = "redirectUrl",value = "跳转地址")@RequestParam(value = "redirectUrl",required = false) String redirectUrl,
                                     @ApiParam(name = "carouselRank",value = "轮播图排序规则 0-升序，1-降序")@RequestParam(value = "carouselRank",required = false,defaultValue = "0") Integer carouselRank) throws Exception {

        // 从token中获取用户信息
        User user = JwtUtil.parseJWT(request.getHeader("token"));
        assert user != null;
        String userId = user.getUserId();

        // 判断用户是否异常
        if (userId == null || userId.equals("")) {
            return ResultBuildUtil.fail("用户数据异常，请重新登录！");
        }

        // 新建一个实体，添加轮播图信息
        BailianCarousel bailianCarousel = new BailianCarousel();
        bailianCarousel.setCarouselUrl(carouselUrl);
        bailianCarousel.setRedirectUrl(redirectUrl);
        bailianCarousel.setCarouselRank(carouselRank);

        // 判断轮播图是否添加成功
        if (homePageFeignService.addCarousel(bailianCarousel, userId)) {
            return ResultBuildUtil.success("添加成功！");
        }

        return ResultBuildUtil.fail("添加失败");
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

        // 判断轮播图是否删除成功
        if (homePageFeignService.delCarousel(ids)) {
            return ResultBuildUtil.success("删除成功！");
        }

        return ResultBuildUtil.fail("删除失败");

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
    @CheckToken
    public RequestResult<String> modCarousel(HttpServletRequest request,
                                             @ApiParam(name = "carouselId",value = "轮播图id")@RequestParam(value = "carouselId",required = false) String carouselId,
                                             @ApiParam(name = "carouselUrl",value = "轮播图图片地址")@RequestParam(value = "carouselUrl",required = false) String carouselUrl,
                                             @ApiParam(name = "redirectUrl",value = "跳转地址")@RequestParam(value = "redirectUrl",required = false) String redirectUrl,
                                             @ApiParam(name = "carouselRank",value = "轮播图排序")@RequestParam(value = "carouselRank",required = false) Integer carouselRank,
                                             @ApiParam(name = "isDeleted",value = "删除标识字段(0-未删除 1-已删除)")@RequestParam(value = "isDeleted",required = false) Integer isDeleted) throws Exception {

        // 从token中获取用户信息
        User user = JwtUtil.parseJWT(request.getHeader("token"));
        assert user != null;
        String userId = user.getUserId();

        // 判断用户是否异常
        if (userId == null || userId.equals("")) {
            return ResultBuildUtil.fail("用户数据异常，请重新登录！");
        }

        // 新建一个实体，修改轮播图信息
        BailianCarousel bailianCarousel = new BailianCarousel();
        bailianCarousel.setCarouselId(carouselId);
        bailianCarousel.setCarouselUrl(carouselUrl);
        bailianCarousel.setRedirectUrl(redirectUrl);
        bailianCarousel.setIsDeleted(isDeleted);
        bailianCarousel.setCarouselRank(carouselRank);

        // // 判断轮播图是否修改成功
        if (homePageFeignService.modCarousel(bailianCarousel,userId)) {
            return ResultBuildUtil.success("修改成功！");
        }

        return ResultBuildUtil.fail("修改失败");
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
    public RequestResult goodsDetailsMap(HttpServletRequest request,
                                         @ApiParam(name = "goodsId",value = "商品id")@RequestParam("goodsId") String goodsId) throws Exception {
        // 从token中获取用户信息
        User user = JwtUtil.parseJWT(request.getHeader("token"));
        assert user != null;
        String userId = user.getUserId();

        // 判断用户是否异常
        if (userId == null || userId.equals("")) {
            return ResultBuildUtil.fail("用户数据异常，请重新登录！");
        }

        // 判断商品id是否存在
        List<String> goodsIdList = goodsFeignService.queryGoodsIdList();
        boolean flag = false;
        for (String s : goodsIdList) {
            if (s.equals(goodsId)) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            return ResultBuildUtil.fail("该商品不存在！");
        }

        // 判断返回的是否是失败的信息，如果是，则返回fail
        Map<String, Object> stringObjectMap = goodsFeignService.goodsDetailsMap(userId, goodsId);
        if (stringObjectMap.containsKey("errCode")) {
            return ResultBuildUtil.fail(stringObjectMap);
        }

        // 否则返回正确的信息
        return ResultBuildUtil.success(stringObjectMap);
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
    public RequestResult queryGoodsFootPrint(HttpServletRequest request) throws Exception {
        // 从token中获取用户信息
        User user = JwtUtil.parseJWT(request.getHeader("token"));
        assert user != null;
        String userId = user.getUserId();

        // 判断用户是否异常
        if (userId == null || userId.equals("")) {
            return ResultBuildUtil.fail("用户数据异常，请重新登录！");
        }

        // 判断用户足迹是否存在
        List<BailianGoodsInfo> bailianGoodsInfos = goodsFeignService.queryGoodsFootPrint(userId);
        if (bailianGoodsInfos.isEmpty()) {
            return ResultBuildUtil.fail("该用户没有足迹");
        }
        return ResultBuildUtil.success(bailianGoodsInfos);
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
    public RequestResult delGoodsFootPrint(HttpServletRequest request,
                                                   @ApiParam(name = "goodsId",value = "商品id")@RequestParam("goodsId") String goodsId) throws Exception {
        // 从token中获取用户信息
        User user = JwtUtil.parseJWT(request.getHeader("token"));
        assert user != null;
        String userId = user.getUserId();

        // 判断用户是否异常
        if (userId == null || userId.equals("")) {
            return ResultBuildUtil.fail("用户数据异常，请重新登录！");
        }

        Map<String, String> map = goodsFeignService.delGoodsFootPrint(userId,goodsId);

        // 判断清除用户足迹是否成功
        if (map.containsKey("errCode")){
            return ResultBuildUtil.fail(map);
        }

        return ResultBuildUtil.success(map);
    }

    /**
     * @author : lixu
     * @date   : 2022/06/18
     * @param  : [java.lang.String]
     * @return : java.util.Map<java.lang.String,java.lang.String>
     * @description : 一键清空用户足迹
     */
    @PostMapping("/clearGoodsFootPrint")
    @ApiOperation(value = "清除用户足迹",notes = "一键清空用户足迹")
    public RequestResult clearGoodsFootPrint(HttpServletRequest request) throws Exception {

        // 从token中获取用户信息
        User user = JwtUtil.parseJWT(request.getHeader("token"));
        assert user != null;
        String userId = user.getUserId();

        // 判断用户是否异常
        if (userId == null || userId.equals("")) {
            return ResultBuildUtil.fail("用户数据异常，请重新登录！");
        }

        Map<String, String> map = goodsFeignService.clearGoodsFootPrint(userId);
        // 判断清除用户足迹是否成功
        if (map.containsKey("errCode")){
            return ResultBuildUtil.fail(map);
        }

        return ResultBuildUtil.success(map);

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
    public RequestResult GoodsRelated(HttpServletRequest request) throws Exception {
        // 从token中获取用户信息
        User user = JwtUtil.parseJWT(request.getHeader("token"));
        assert user != null;
        String userId = user.getUserId();

        // 判断用户是否异常
        if (userId == null || userId.equals("")) {
            return ResultBuildUtil.fail("用户数据异常，请重新登录！");
        }

        // 判断猜你喜欢的商品是否为空
        List<BailianGoodsInfo> bailianGoodsInfos = homePageFeignService.GoodsRelated(userId);
        if (bailianGoodsInfos.isEmpty()) {
            return ResultBuildUtil.fail("商品未加载成功！");
        }

        return ResultBuildUtil.success(bailianGoodsInfos);
    }

    /**
     * @author : lixu
     * @date   : 2022/06/14
     * @param  : [java.lang.String, java.lang.String]
     * @return : com.cuning.util.RequestResult<java.util.List<java.lang.Object>>
     * @description : 搜索记录
     */
    @GetMapping("/searchHistory")
    @ApiOperation(value = "商品搜索记录",notes = "将商品搜索记录存入redis中，再次搜索将靠前显示，一共展示十条数据")
    @CheckToken
    public RequestResult searchHistory(HttpServletRequest request,
                                       @ApiParam(name = "searchKey",value = "搜索关键字")@RequestParam("searchKey") String searchKey) throws Exception {
        // 从token中获取用户信息
        User user = JwtUtil.parseJWT(request.getHeader("token"));
        assert user != null;
        String userId = user.getUserId();

        // 判断用户是否异常
        if (userId == null || userId.equals("")) {
            return ResultBuildUtil.fail("用户数据异常，请重新登录！");
        }

        //判断搜索记录是否存在
        List<Object> objects = goodsFeignService.searchHistory(userId, searchKey);
        if (objects.isEmpty()) {
            return ResultBuildUtil.fail("暂无搜索记录!");
        }

        return ResultBuildUtil.success(objects);
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
    public RequestResult delSearchHistory(HttpServletRequest request) throws Exception {
        // 从token中获取用户信息
        User user = JwtUtil.parseJWT(request.getHeader("token"));
        assert user != null;
        String userId = user.getUserId();


        // 判断用户是否异常
        if (userId == null || userId.equals("")) {
            return ResultBuildUtil.fail("用户数据异常，请重新登录！");
        }

        Map<String, String> map = goodsFeignService.delSearchHistory(userId);
        // 判断删除搜索记录是否成功
        if (map.containsKey("errCode")){
            return ResultBuildUtil.fail(map);
        }

        return ResultBuildUtil.success(map);
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
    public RequestResult printHotWord(){

        List<Object> objects = goodsFeignService.printHotWord();
        if (objects.isEmpty()) {
            return ResultBuildUtil.fail("热词不存在！");
        }

        return ResultBuildUtil.success(objects);
    }


}
