package com.cuning.controller.goods;
/*
 * @Created on : 2022/6/14 0014
 * <p>
 * @Author     : Administrator
 * <p>
 * @Description: GoodsCategoryController
 **/

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cuning.bean.goods.BailianGoodsCategory;
import com.cuning.bean.goods.BailianGoodsInfo;
import com.cuning.bean.user.User;
import com.cuning.service.GoodsCategoryFeignService;
import com.cuning.util.JwtUtil;
import com.cuning.util.RequestResult;
import com.cuning.util.ResultBuildUtil;
import com.cuning.vo.GoodsCategoryVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@Api(tags = "分类模块")
@Slf4j
public class GoodsCategoryController {

    @Autowired
    private GoodsCategoryFeignService goodsCategoryService;


    @PostMapping("/saveGoodsCategory")
    @ApiOperation(value = "添加分类")
    public RequestResult<String> saveGoodsCategory(HttpServletRequest request, @RequestParam String categoryName, @RequestParam Integer categoryRank,
                                                   @RequestParam Integer categoryLevel, @RequestParam Integer parentId) throws Exception{
        User user = JwtUtil.parseJWT(request.getHeader("token"));
        if (goodsCategoryService.saveGoodsCategory(categoryName,categoryRank,categoryLevel,parentId,user.getUserId()) != null){
            return ResultBuildUtil.success("分类添加成功！");
        }
        return ResultBuildUtil.fail("分类添加失败！");
    }

    @PostMapping("/updateGoodsCategory")
    @ApiOperation(value = "修改分类")
    public RequestResult<String> updateGoodsCategory(HttpServletRequest request,@RequestParam Integer categoryId,
                                                     @RequestParam String categoryName,@RequestParam Integer categoryRank) throws Exception{
        User user = JwtUtil.parseJWT(request.getHeader("token"));
        if (goodsCategoryService.updateGoodsCategory(categoryId,categoryName,categoryRank, user.getUserId())){
            return ResultBuildUtil.success("分类修改成功！");
        }
        return ResultBuildUtil.fail("分类修改失败！");
    }

    @PostMapping("/deleteGoodsCategory")
    @ApiOperation(value = "删除分类")
    public RequestResult<String> deleteGoodsCategory(@RequestParam Integer categoryId){
        if (goodsCategoryService.deleteGoodsCategory(categoryId) != null){
            return ResultBuildUtil.success("分类删除成功！");
        }
        return ResultBuildUtil.fail("分类删除失败！");
    }

    @GetMapping("/queryGoodsCategory")
    @ApiOperation(value = "分页查询分类")
    public RequestResult<Page<BailianGoodsCategory>> queryGoodsCategoryByCategoryLevelAndParentId(@RequestParam Integer pageNo, @RequestParam Integer pageSize,
                                                                                                  @RequestParam Integer categoryLevel, @RequestParam Integer parentId){
        Page<BailianGoodsCategory> page = goodsCategoryService.queryGoodsCategoryByCategoryLevelAndParentId(pageNo,pageSize,categoryLevel,parentId);
        if (page.getTotal()>0){
            return ResultBuildUtil.success(page);
        }
        return ResultBuildUtil.fail(page);
    }

    @GetMapping("/queryCategory")
    @ApiOperation(value = "查询所有分类")
    public RequestResult<List<GoodsCategoryVO>> queryCategory(){
        List<GoodsCategoryVO> list = new ArrayList<>();
        list = goodsCategoryService.queryCategory();
        if (list.isEmpty()){
            return ResultBuildUtil.success(list);
        }
        return ResultBuildUtil.fail(list);
    }

    @GetMapping("/queryGoodsInfoByCategory")
    @ApiOperation(value = "分类查询商品详情")
    public RequestResult<Page<BailianGoodsInfo>> queryGoodsInfoByCategory(@RequestParam Integer pageNo, @RequestParam Integer pageSize,
                                                    @RequestParam String categoryName, @RequestParam Boolean flag){
        Page<BailianGoodsInfo> page = goodsCategoryService.queryGoodsInfoByCategory(pageNo,pageSize,categoryName,flag);
        if (page.getTotal()>0){
            return ResultBuildUtil.success(page);
        }
        return ResultBuildUtil.fail(page);
    }
}
