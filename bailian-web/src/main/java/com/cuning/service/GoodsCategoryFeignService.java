package com.cuning.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cuning.bean.goods.BailianGoodsCategory;
import com.cuning.bean.goods.BailianGoodsInfo;
import com.cuning.vo.GoodsCategoryVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/*
 * @Created on : 2022/6/14 0014
 * <p>
 * @Author     : Administrator
 * <p>
 * @Description: GoodsCategoryService
 **/
@FeignClient(name = "bailian-goods")
public interface GoodsCategoryFeignService {

    /***
     * @author : Administrator
     * @date   : 2022/6/18 0018
     * @param  : [java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.String]
     * @return : com.cuning.bean.goods.BailianGoodsCategory
     * @description : 新增分类
     */
    @PostMapping("/saveGoodsCategory")
    BailianGoodsCategory saveGoodsCategory(@RequestParam("categoryName") String categoryName, @RequestParam("categoryRank") Integer categoryRank,
                                           @RequestParam("categoryLevel") Integer categoryLevel, @RequestParam("parentId") Integer parentId,
                                           @RequestParam("userId") String userId);

    /***
     * @author : Administrator
     * @date   : 2022/6/18 0018
     * @param  : [java.lang.Integer, java.lang.String, java.lang.Integer, java.lang.String]
     * @return : java.lang.Boolean
     * @description : 修改分类
     */
    @PostMapping("/updateGoodsCategory")
    Boolean updateGoodsCategory(@RequestParam("categoryId") Integer categoryId, @RequestParam("categoryName") String categoryName,
                                @RequestParam("categoryRank") Integer categoryRank, @RequestParam("userId") String userId);

    /***
     * @author : Administrator
     * @date   : 2022/6/18 0018
     * @param  : [java.lang.Integer]
     * @return : java.lang.Boolean
     * @description : 删除分类
     */
    @PostMapping("/deleteGoodsCategory")
    Boolean deleteGoodsCategory(@RequestParam("categoryId") Integer categoryId);

    /***
     * @author : Administrator
     * @date   : 2022/6/18 0018
     * @param  : [java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer]
     * @return : com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.cuning.bean.goods.BailianGoodsCategory>
     * @description : 根据分类等级和父分类id，分页查询分类
     */
    @GetMapping("/queryGoodsCategory")
    Page<BailianGoodsCategory> queryGoodsCategoryByCategoryLevelAndParentId(@RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize,
                                                                            @RequestParam("categoryLevel") Integer categoryLevel, @RequestParam("parentId") Integer parentId);

    /***
     * @author : Administrator
     * @date   : 2022/6/18 0018
     * @param  : []
     * @return : java.util.List<com.cuning.vo.GoodsCategoryVO>
     * @description :查询首页调用的分类信息
     */
    @GetMapping("/queryCategory")
    List<GoodsCategoryVO> queryCategory();

    /***
     * @author : Administrator
     * @date   : 2022/6/18 0018
     * @param  : [java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.Boolean]
     * @return : com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.cuning.bean.goods.BailianGoodsInfo>
     * @description : 据首页分类名，分页查询商品详情，支持降序升序排序
     */
    @GetMapping("/queryGoodsInfoByCategory")
    Page<BailianGoodsInfo> queryGoodsInfoByCategory(@RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize,
                                                    @RequestParam("categoryName") String categoryName, @RequestParam("flag") Boolean flag);

    /***
     * @author : Administrator
     * @date   : 2022/6/18 0018
     * @param  : [java.lang.Integer]
     * @return : com.cuning.bean.goods.BailianGoodsCategory
     * @description :根据分类id查询分类信息
     */
    @GetMapping("/queryCategoryById")
    BailianGoodsCategory queryCategoryById(@RequestParam("categoryId") Integer categoryId);

    /***
     * @author : Administrator
     * @date   : 2022/6/18 0018
     * @param  : []
     * @return : java.util.List<java.lang.String>
     * @description : 查询所有分类的分类名
     */
    @PostMapping("/queryGoodsCategoryAll")
    List<String> queryGoodsCategory();

    /***
     * @author : Administrator
     * @date   : 2022/6/18 0018
     * @param  : [java.lang.Integer]
     * @return : java.util.List<java.lang.Integer>
     * @description : 根据分类等级查询所有分类的父分类id
     */
    @GetMapping("/queryCategoryByLevel")
    List<Integer> queryCategoryByLevel(@RequestParam("categoryLevel") Integer categoryLevel);
}
