package com.cuning.controller.goods;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cuning.bean.goods.BailianGoodsCommentary;
import com.cuning.bean.shoppingOrder.BailianOrderItem;
import com.cuning.bean.user.User;
import com.cuning.service.GoodsCommentaryFeignService;
import com.cuning.service.GoodsInfoFeignService;
import com.cuning.util.JwtUtil;
import com.cuning.util.RequestResult;
import com.cuning.util.ResultBuildUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/*
 * @Created on : 2022/6/15 0015
 * <p>
 * @Author     : Administrator
 * <p>
 * @Description: GoodsCommentaryController
 **/

@RestController
@Api(tags = "评论模块")
@Slf4j
public class GoodsCommentaryController {

    @Autowired
    private GoodsCommentaryFeignService goodsCommentaryFeignService;

    @Autowired
    private GoodsInfoFeignService goodsInfoFeignService;

    @GetMapping("/queryGoodsCommentary")
    @ApiOperation(value = "分页查询商品评价")
    public RequestResult queryGoodsCommentary(@RequestParam Integer pageNo, @RequestParam Integer pageSize, @RequestParam String goodsId, @RequestParam Integer commentaryType){
        Page<BailianGoodsCommentary> page = goodsCommentaryFeignService.queryGoodsCommentary(pageNo,pageSize,goodsId,commentaryType);
        if (commentaryType<0 || commentaryType >3){
            return ResultBuildUtil.fail("评论类型超出取值范围！");
        }
        if (goodsInfoFeignService.queryGoodsById(goodsId) == null){
            return ResultBuildUtil.fail("商品不存在！");
        }
        if (page.getTotal() >0){
            if (pageNo<=0 || pageNo >page.getPages()){
                return ResultBuildUtil.fail("页码超出取值范围！");
            }
            if (pageSize <=0){
                return ResultBuildUtil.fail("页面数据数量不能小于1");
            }
            return ResultBuildUtil.success(page);
        }
        return ResultBuildUtil.fail("该商品没有此类型的评论！");
    }

    @PostMapping("/saveGoodsCommentary")
    @ApiOperation(value = "评价商品")
    public RequestResult saveGoodsCommentary(HttpServletRequest request, @RequestParam String orderNo, @RequestParam String goodsId,
                                                     @RequestParam Integer commentaryLevel, @RequestParam String goodsCommentary, @RequestParam String commentaryUrl) throws Exception{
        User user = JwtUtil.parseJWT(request.getHeader("token"));
        if (user.getUserName().isEmpty()){
            return ResultBuildUtil.fail("用户信息获取失败！");
        }
        if (goodsCommentaryFeignService.seneitiveWord(goodsCommentary)){
            return ResultBuildUtil.fail("评价内容包含敏感词，评论失败！");
        }
        if (commentaryLevel<1 || commentaryLevel >5){
            return ResultBuildUtil.fail("评论星级超出取值范围！");
        }
        String img = user.getUserHeadImg();
        if (StringUtils.isEmpty(img)){
            img = "https://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83epBORvJHTictQOt1bUGW2os3MLrZRicNhoRvKUMlXycQhPyFYoRQuwL9pwtQO5nY6LgqSHN8obIUmlA/132";
        }
        Map<String,String> map = goodsCommentaryFeignService.saveGoodsCommentary(user.getUserId(), user.getUserName(), img, orderNo,goodsId,commentaryLevel,goodsCommentary,commentaryUrl);
        if (map.get("code").equals("200")){
            return ResultBuildUtil.success(map);
        }
        return ResultBuildUtil.fail(map);
    }

    @GetMapping("/deleteGoodsCommentary")
    @ApiOperation(value = "删除评价")
    public RequestResult deleteGoodsCommentary(HttpServletRequest request,@RequestParam String orderNo,@RequestParam String goodsId) throws Exception{
        User user = JwtUtil.parseJWT(request.getHeader("token"));
        if (goodsInfoFeignService.queryGoodsById(goodsId) == null){
            return ResultBuildUtil.fail("商品不存在");
        }
        Map<String,String> map = goodsCommentaryFeignService.deleteGoodsCommentary(user.getUserId(),orderNo,goodsId);
        if (map.get("code").equals("200")){
            return ResultBuildUtil.success(map);
        }
        return ResultBuildUtil.fail(map);
    }

    @GetMapping("/queryGoodsCommentaryType")
    @ApiOperation(value = "查看评价详情")
    public RequestResult queryGoodsCommentaryType(HttpServletRequest request, @RequestParam Integer pageNo, @RequestParam Integer pageSize, @RequestParam Integer commentaryType) throws Exception{
        User user = JwtUtil.parseJWT(request.getHeader("token"));
        if (commentaryType<0 || commentaryType > 1){
            return ResultBuildUtil.fail("超出取值范围！");
        }
        Page<BailianOrderItem> page = goodsCommentaryFeignService.queryGoodsCommentaryType(user.getUserId(), pageNo,pageSize,commentaryType);
        if (page == null || page.getTotal() <=0){
            return ResultBuildUtil.fail("没有待评论或已评论的商品！");
        }
            if (pageNo<=0 || pageNo >page.getPages()){
                return ResultBuildUtil.fail("页码超出取值范围！");
            }
            if (pageSize <=0){
                return ResultBuildUtil.fail("页面数据数量不能小于1");
            }
            return ResultBuildUtil.success(page);
    }
}
