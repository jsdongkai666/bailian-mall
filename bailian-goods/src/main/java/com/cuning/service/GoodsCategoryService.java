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

    BailianGoodsCategory saveGoodsCategory(String categoryName,Integer categoryRank,Integer categoryLevel,Integer parentId,String userId);

    Boolean updateGoodsCategory(Integer categoryId,String categoryName,Integer categoryRank,String userId);

    Boolean deleteGoodsCategory(Integer categoryId);

    Page<BailianGoodsCategory> queryGoodsCategoryByCategoryLevelAndParentId(Integer pageNo,Integer pageSize,Integer categoryLevel, Integer parentId);

    void queryIdsList(Integer categoryId,List<Integer> ids);

    List<GoodsCategoryVO> queryCategory();

    Page<BailianGoodsInfo> queryGoodsInfoByCategory(Integer pageNo,Integer pageSize,String categoryName,Boolean flag);

    BailianGoodsCategory queryCategoryById(Integer categoryId);

    List<BailianGoodsCategory> queryGoodsCategory();

    List<Integer> queryCategoryByLevel(Integer categoryLevel);

}
