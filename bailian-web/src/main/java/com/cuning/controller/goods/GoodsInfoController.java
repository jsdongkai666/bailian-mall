package com.cuning.controller.goods;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cuning.bean.goods.BailianGoodsInfo;
import com.cuning.bean.user.User;
import com.cuning.service.GoodsInfoFeignService;
import com.cuning.util.JwtUtil;
import com.cuning.util.RequestResult;
import com.cuning.util.ResultBuildUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/*
 * @Created on : 2022/6/14 0014
 * <p>
 * @Author     : Administrator
 * <p>
 * @Description: GoodsInfoController
 **/
@RestController
@Api(tags = "商品模块")
@Slf4j
public class GoodsInfoController {

    @Autowired
    private GoodsInfoFeignService goodsInfoService;

    @PostMapping("/addGoods")
    @ApiOperation(value = "新增商品")
    public RequestResult<String> saveGoods(HttpServletRequest request, @RequestBody BailianGoodsInfo goodsInfo) throws Exception {
        User user = JwtUtil.parseJWT(request.getHeader("token"));
        if (goodsInfoService.saveGoods(goodsInfo, user.getUserId()) != null) {
            return ResultBuildUtil.success("商品添加成功！");
        }
        return ResultBuildUtil.fail("商品添加失败！");
    }

    @GetMapping("/queryGoodsById")
    @ApiOperation(value = "根据id查询商品")
    public RequestResult queryGoodsById(@RequestParam String goodsId) {
        BailianGoodsInfo bailianGoodsInfo = goodsInfoService.queryGoodsById(goodsId);
        if (bailianGoodsInfo != null) {
            return ResultBuildUtil.success(bailianGoodsInfo);
        }
        return ResultBuildUtil.fail("商品不存在！");
    }

    @PostMapping("/updateGoods")
    @ApiOperation(value = "修改商品")
    public RequestResult<String> updateGoodsInfo(HttpServletRequest request, @RequestBody BailianGoodsInfo goodsInfo) throws Exception {
        User user = JwtUtil.parseJWT(request.getHeader("token"));
        if (goodsInfoService.updateGoodsInfo(goodsInfo, user.getUserId()) != null) {
            return ResultBuildUtil.success("商品修改成功！");
        }
        return ResultBuildUtil.fail("商品修改失败！");
    }

    @PostMapping("/updateStatus")
    @ApiOperation(value = "商品上下架")
    public RequestResult<String> updateGoodsSellStatus(@RequestParam String goodsId, @RequestParam Byte goodsSellStatus) {
        BailianGoodsInfo bailianGoodsInfo = goodsInfoService.queryGoodsById(goodsId);
        if (bailianGoodsInfo == null) {
            return ResultBuildUtil.fail("商品不存在！");
        }
        if (goodsSellStatus < 0 || goodsSellStatus > 1) {
            return ResultBuildUtil.fail("商品状态码值只能为0或1");
        }

        if (bailianGoodsInfo.getGoodsSellStatus().equals(goodsSellStatus)) {
            if (goodsSellStatus == 0) {
                return ResultBuildUtil.fail("商品已下架，不能重复操作！");
            }
            return ResultBuildUtil.fail("商品已上架，不能重复操作！");
        }
        if (goodsInfoService.updateGoodsSellStatus(goodsId, goodsSellStatus)) {
            return ResultBuildUtil.success("商品上架状态修改成功！");
        }
        return ResultBuildUtil.success("商品上架状态修改失败！");
    }

    @GetMapping("/deleteGoods")
    @ApiOperation(value = "删除商品")
    public RequestResult<String> deleteGoodsInfo(@RequestParam String goodsId) {
        if (goodsInfoService.queryGoodsById(goodsId) == null) {
            return ResultBuildUtil.fail("商品不存在，无法删除！");
        }
        if (goodsInfoService.deleteGoodsInfo(goodsId)) {
            return ResultBuildUtil.success("商品删除成功！");
        }
        return ResultBuildUtil.fail("商品删除失败！");
    }

    @GetMapping("/queryGoodsPage")
    @ApiOperation(value = "分页查询商品")
    public RequestResult<Page<BailianGoodsInfo>> queryGoodsInfoPage(@RequestParam Integer pageNo, @RequestParam Integer pageSize) {
        Page<BailianGoodsInfo> page = goodsInfoService.queryGoodsInfoPage(pageNo, pageSize);
        if (page.getTotal() > 0) {
            return ResultBuildUtil.success(page);
        }
        return ResultBuildUtil.fail(page);
    }
}
