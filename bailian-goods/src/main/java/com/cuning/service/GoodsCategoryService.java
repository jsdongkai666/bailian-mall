package com.cuning.service; /*
 * @Created on : 2022/6/10 0010
 * <p>
 * @Author     : Administrator
 * <p>
 * @Description: GoodsCagetoryService
 **/

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cuning.bean.goods.BailianGoodsCategory;
import com.cuning.bean.goods.BailianGoodsInfo;
import com.cuning.vo.GoodsCategoryVO;


import java.util.List;


public interface GoodsCategoryService extends IService<BailianGoodsCategory> {

    /***
     * @author : Administrator
     * @date   : 2022/6/18 0018
     * @param  : [java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.String]
     * @return : com.cuning.bean.goods.BailianGoodsCategory
     * @description : 新增分类
     */
    BailianGoodsCategory saveGoodsCategory(String categoryName, Integer categoryRank, Integer categoryLevel, Integer parentId, String userId);

    /***
     * @author : Administrator
     * @date   : 2022/6/18 0018
     * @param  : [java.lang.Integer, java.lang.String, java.lang.Integer, java.lang.String]
     * @return : java.lang.Boolean
     * @description : 修改分类
     */
    Boolean updateGoodsCategory(Integer categoryId, String categoryName, Integer categoryRank, String userId);

    /***
     * @author : Administrator
     * @date   : 2022/6/18 0018
     * @param  : [java.lang.Integer]
     * @return : java.lang.Boolean
     * @description : 删除分类
     */
    Boolean deleteGoodsCategory(Integer categoryId);

    /***
     * @author : Administrator
     * @date   : 2022/6/18 0018
     * @param  : [java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer]
     * @return : com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.cuning.bean.goods.BailianGoodsCategory>
     * @description : 根据分类等级和父分类id，分页查询分类
     */
    Page<BailianGoodsCategory> queryGoodsCategoryByCategoryLevelAndParentId(Integer pageNo, Integer pageSize, Integer categoryLevel, Integer parentId);

    /***
     * @author : Administrator
     * @date   : 2022/6/18 0018
     * @param  : [java.lang.Integer, java.util.List<java.lang.Integer>]
     * @return : void
     * @description : 查询此分类id下所有的子分类id
     */
    void queryIdsList(Integer categoryId, List<Integer> ids);

    /***
     * @author : Administrator
     * @date   : 2022/6/18 0018
     * @param  : []
     * @return : java.util.List<com.cuning.vo.GoodsCategoryVO>
     * @description : 查询首页调用的分类信息
     */
    List<GoodsCategoryVO> queryCategory();

    /***
     * @author : Administrator
     * @date   : 2022/6/18 0018
     * @param  : [java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.Boolean]
     * @return : com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.cuning.bean.goods.BailianGoodsInfo>
     * @description : 根据首页分类名，分页查询商品详情，支持降序升序排序
     */
    Page<BailianGoodsInfo> queryGoodsInfoByCategory(Integer pageNo, Integer pageSize, String categoryName, Boolean flag);

    /***
     * @author : Administrator
     * @date   : 2022/6/18 0018
     * @param  : [java.lang.Integer]
     * @return : com.cuning.bean.goods.BailianGoodsCategory
     * @description : 根据分类id查询分类信息
     */
    BailianGoodsCategory queryCategoryById(Integer categoryId);

    /***
     * @author : Administrator
     * @date   : 2022/6/18 0018
     * @param  : []
     * @return : java.util.List<java.lang.String>
     * @description : 查询所有分类的分类名
     */
    List<String> queryGoodsCategory();

    /***
     * @author : Administrator
     * @date   : 2022/6/18 0018
     * @param  : [java.lang.Integer]
     * @return : java.util.List<java.lang.Integer>
     * @description : 根据分类等级查询所有分类的父分类id
     */
    List<Integer> queryCategoryByLevel(Integer categoryLevel);

}
