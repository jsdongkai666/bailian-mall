package com.cuning.controller.goods;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cuning.bean.goods.BailianGoodsCommentary;
import com.cuning.bean.shoppingOrder.BailianOrderItem;
import com.cuning.bean.user.User;
import com.cuning.service.GoodsCommentaryFeignService;
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

    @GetMapping("/queryGoodsCommentary")
    @ApiOperation(value = "分页查询商品评价")
    public RequestResult<Page<BailianGoodsCommentary>> queryGoodsCommentary(@RequestParam Integer pageNo, @RequestParam Integer pageSize, @RequestParam String goodsId, @RequestParam Integer commentaryType){
        Page<BailianGoodsCommentary> page = goodsCommentaryFeignService.queryGoodsCommentary(pageNo,pageSize,goodsId,commentaryType);
        if (page.getTotal() >0){
            return ResultBuildUtil.success(page);
        }
        return ResultBuildUtil.fail(page);
    }

    @PostMapping("/saveGoodsCommentary")
    @ApiOperation(value = "评价商品")
    public RequestResult<String> saveGoodsCommentary(HttpServletRequest request, @RequestParam String orderNo, @RequestParam String goodsId,
                                                     @RequestParam Integer commentaryLevel, @RequestParam String goodsCommentary, @RequestParam String commentaryUrl) throws Exception{
        User user = JwtUtil.parseJWT(request.getHeader("token"));
        if (goodsCommentaryFeignService.seneitiveWord(goodsCommentary)){
            return ResultBuildUtil.fail("评价内容包含敏感词，评论失败！");
        }
        String img = user.getUserHeadImg();
        if (StringUtils.isEmpty(img)){
            img = "https://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83epBORvJHTictQOt1bUGW2os3MLrZRicNhoRvKUMlXycQhPyFYoRQuwL9pwtQO5nY6LgqSHN8obIUmlA/132";
        }
        Boolean flag  = goodsCommentaryFeignService.saveGoodsCommentary(user.getUserId(), user.getUserName(), img, orderNo,goodsId,commentaryLevel,goodsCommentary,commentaryUrl);
        if (flag){
            return ResultBuildUtil.success("评论成功!");
        }
        return ResultBuildUtil.success("评论失败！");
    }

    @GetMapping("/deleteGoodsCommentary")
    @ApiOperation(value = "删除评价")
    public RequestResult<String> deleteGoodsCommentary(HttpServletRequest request,@RequestParam String orderNo,@RequestParam String goodsId) throws Exception{
        User user = JwtUtil.parseJWT(request.getHeader("token"));
        if (goodsCommentaryFeignService.deleteGoodsCommentary(user.getUserId(),orderNo,goodsId)){
            return ResultBuildUtil.success("评论删除成功!");
        }
        return ResultBuildUtil.success("评论删失败!");
    }

    @GetMapping("/queryGoodsCommentaryType")
    @ApiOperation(value = "查看评价详情")
    public RequestResult<Page<BailianOrderItem>> queryGoodsCommentaryType(HttpServletRequest request, @RequestParam Integer pageNo, @RequestParam Integer pageSize, @RequestParam Integer commentaryType) throws Exception{
        User user = JwtUtil.parseJWT(request.getHeader("token"));
        Page<BailianOrderItem> page = goodsCommentaryFeignService.queryGoodsCommentaryType(user.getUserId(), pageNo,pageSize,commentaryType);
        if (page.getTotal() > 0){
            return ResultBuildUtil.success(page);
        }
        return ResultBuildUtil.fail(page);
    }
}
