package com.cuning.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cuning.bean.goods.BailianGoodsCategory;
import com.cuning.bean.goods.BailianGoodsInfo;
import com.cuning.service.GoodsCategoryService;
import com.cuning.vo.GoodsCategoryVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * @Created on : 2022/6/10 0010
 * <p>
 * @Author     : Administrator
 * <p>
 * @Description: GoodsCagetoryController
 **/
@Slf4j
@RestController
public class GoodsCagetoryController {

    @Autowired(required = false)
    private GoodsCategoryService goodsCategoryService;

    /***
     * @author : Administrator
     * @date   : 2022/6/10 0010
     * @param  : [com.cuning.bean.goods.BailianGoodsCategory]
     * @return : com.cuning.bean.goods.BailianGoodsCategory
     * @description : 新增分类
     */
    @PostMapping("/saveGoodsCategory")
    public BailianGoodsCategory saveGoodsCategory(@RequestParam String categoryName, @RequestParam Integer categoryRank,
                                                  @RequestParam Integer categoryLevel, @RequestParam Integer parentId,
                                                  @RequestParam String userId) {
        return goodsCategoryService.saveGoodsCategory(categoryName, categoryRank, categoryLevel, parentId, userId);
    }

    /***
     * @author : Administrator
     * @date   : 2022/6/10 0010
     * @param  : [com.cuning.bean.goods.BailianGoodsCategory]
     * @return : com.cuning.bean.goods.BailianGoodsCategory
     * @description : 修改分类
     */
    @PostMapping("/updateGoodsCategory")
    public Boolean updateGoodsCategory(@RequestParam Integer categoryId, @RequestParam String categoryName,
                                       @RequestParam Integer categoryRank, @RequestParam String userId) {
        return goodsCategoryService.updateGoodsCategory(categoryId, categoryName, categoryRank, userId);
    }

    /***
     * @author : Administrator
     * @date   : 2022/6/10 0010
     * @param  : [java.lang.Integer]
     * @return : boolean
     * @description : 删除分类
     */
    @PostMapping("/deleteGoodsCategory")
    public boolean deleteGoodsCategory(@RequestParam Integer categoryId) {
        return goodsCategoryService.deleteGoodsCategory(categoryId);
    }

    /***
     * @author : Administrator
     * @date   : 2022/6/10 0010
     * @param  : [java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer]
     * @return : com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.cuning.bean.goods.BailianGoodsCategory>
     * @description : 分页查询分类
     */
    @GetMapping("/queryGoodsCategory")
    public Page<BailianGoodsCategory> queryGoodsCategoryPage(@RequestParam Integer pageNo, @RequestParam Integer pageSize,
                                                             @RequestParam Integer categoryLevel, @RequestParam Integer parentId) {
        return goodsCategoryService.queryGoodsCategoryByCategoryLevelAndParentId(pageNo, pageSize, categoryLevel, parentId);
    }

    /***
     * @author : Administrator
     * @date   : 2022/6/11 0011
     * @param  : []
     * @return : java.util.List<com.cuning.vo.GoodsCategoryVO>
     * @description : 首页分类显示
     */
    @GetMapping("/queryCategory")
    public List<GoodsCategoryVO> queryCategory() {
        return goodsCategoryService.queryCategory();
    }

    /***
     * @author : Administrator
     * @date   : 2022/6/11 0011
     * @param  : [java.lang.Integer, java.lang.Integer, java.lang.String]
     * @return : com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.cuning.bean.goods.BailianGoodsInfo>
     * @description : 根据分类名分页查询商品详情
     */
    @GetMapping("/queryGoodsInfoByCategory")
    public Page<BailianGoodsInfo> queryGoodsInfoByCategory(@RequestParam Integer pageNo, @RequestParam Integer pageSize, @RequestParam String categoryName, @RequestParam Boolean flag) {
        return goodsCategoryService.queryGoodsInfoByCategory(pageNo, pageSize, categoryName, flag);
    }

    /***
     * @author : Administrator
     * @date   : 2022/6/17 0017
     * @param  : [java.lang.Integer]
     * @return : com.cuning.bean.goods.BailianGoodsCategory
     * @description : 根据分类id查询分类表
     */
    @GetMapping("/queryCategoryById")
    public BailianGoodsCategory queryCategoryById(@RequestParam Integer categoryId) {
        return goodsCategoryService.queryCategoryById(categoryId);
    }

    /***
     * @author : Administrator
     * @date   : 2022/6/17 0017
     * @param  : []
     * @return : java.util.List<com.cuning.bean.goods.BailianGoodsCategory>
     * @description : 查询分类表中所有分类名
     */
    @PostMapping("/queryGoodsCategoryAll")
    public List<String> queryGoodsCategory() {
        return goodsCategoryService.queryGoodsCategory();
    }

    /***
     * @author : Administrator
     * @date   : 2022/6/17 0017
     * @param  : [java.lang.Integer]
     * @return : java.util.List<com.cuning.bean.goods.BailianGoodsCategory>
     * @description : 根据分类等级查询分类表
     */
    @GetMapping("/queryCategoryByLevel")
    public List<Integer> queryCategoryByLevel(@RequestParam Integer categoryLevel) {
        return goodsCategoryService.queryCategoryByLevel(categoryLevel);
    }
}
