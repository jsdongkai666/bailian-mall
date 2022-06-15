package com.cuning.controller.goods;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cuning.bean.goods.BailianGoodsInfo;
import com.cuning.service.GoodsInfoFeignService;
import com.cuning.util.RequestResult;
import com.cuning.util.ResultBuildUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public RequestResult<String> saveGoods (@RequestBody BailianGoodsInfo goodsInfo){
        if (goodsInfoService.saveGoods(goodsInfo) != null){
            return ResultBuildUtil.success("商品添加成功！");
        }
        return ResultBuildUtil.fail("商品添加失败！");
    }


    @PostMapping("/updateGoods")
    @ApiOperation(value = "修改商品")
    public RequestResult<String> updateGoodsInfo(@RequestBody BailianGoodsInfo goodsInfo){
        if (goodsInfoService.updateGoodsInfo(goodsInfo) != null){
            return ResultBuildUtil.success("商品修改成功！");
        }
        return ResultBuildUtil.fail("商品修改失败！");
    }

    @GetMapping("/deleteGoods")
    @ApiOperation(value = "删除商品")
    public RequestResult<String> deleteGoodsInfo(@RequestParam Integer goodsId){
        if (goodsInfoService.deleteGoodsInfo(goodsId) != null){
            return ResultBuildUtil.success("商品删除成功！");
        }
        return ResultBuildUtil.fail("商品删除失败！");
    }

    @GetMapping("/queryGoodsPage")
    @ApiOperation(value = "分页查询商品")
    public RequestResult<Page<BailianGoodsInfo>> queryGoodsInfoPage(@RequestParam Integer pageNo, @RequestParam Integer pageSize){
        Page<BailianGoodsInfo> page = goodsInfoService.queryGoodsInfoPage(pageNo,pageSize);
        if (page != null){
            return ResultBuildUtil.success(page);
        }
        return ResultBuildUtil.fail(page);
    }
}
