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
@FeignClient(name="bailian-goods")
public interface GoodsCategoryFeignService {

    @PostMapping("/saveGoodsCategory")
    BailianGoodsCategory saveGoodsCategory(@RequestParam("categoryName") String categoryName,@RequestParam("categoryRank") Integer categoryRank,
                                           @RequestParam("categoryLevel") Integer categoryLevel,@RequestParam("parentId") Integer parentId,
                                           @RequestParam("userId") String userId);

    @PostMapping("/updateGoodsCategory")
    Boolean updateGoodsCategory(@RequestParam("categoryId") Integer categoryId, @RequestParam("categoryName") String categoryName,
                                @RequestParam("categoryRank") Integer categoryRank,@RequestParam("userId") String userId);

    @PostMapping("/deleteGoodsCategory")
    Boolean deleteGoodsCategory(@RequestParam("categoryId") Integer categoryId);

    @GetMapping("/queryGoodsCategory")
    Page<BailianGoodsCategory> queryGoodsCategoryByCategoryLevelAndParentId(@RequestParam("pageNo") Integer pageNo,@RequestParam("pageSize") Integer pageSize,
                                                                            @RequestParam("categoryLevel") Integer categoryLevel,@RequestParam("parentId") Integer parentId);

    @GetMapping("/queryCategory")
    List<GoodsCategoryVO> queryCategory();

    @GetMapping("/queryGoodsInfoByCategory")
    Page<BailianGoodsInfo> queryGoodsInfoByCategory(@RequestParam("pageNo") Integer pageNo,@RequestParam("pageSize") Integer pageSize,
                                                    @RequestParam("categoryName") String categoryName,@RequestParam("flag") Boolean flag);
}
