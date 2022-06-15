package com.cuning.service.impl; /*
 * @Created on : 2022/6/10 0010
 * <p>
 * @Author     : Administrator
 * <p>
 * @Description: GoodsCagetoryServiceImpl
 **/

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cuning.bean.goods.BailianGoodsCategory;
import com.cuning.bean.goods.BailianGoodsInfo;
import com.cuning.mapper.GoodsCategoryMapper;
import com.cuning.mapper.GoodsInfoMapper;
import com.cuning.service.GoodsCategoryService;
import com.cuning.util.SnowFlake;
import com.cuning.vo.GoodsCategorySecondVO;
import com.cuning.vo.GoodsCategoryThirdVO;
import com.cuning.vo.GoodsCategoryVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class GoodsCategoryServiceImpl extends ServiceImpl<GoodsCategoryMapper, BailianGoodsCategory> implements GoodsCategoryService {

    @Autowired(required = false)
    private GoodsCategoryMapper goodsCategoryMapper;

    @Autowired(required = false)
    private GoodsInfoMapper goodsInfoMapper;

    @Override
    public BailianGoodsCategory saveGoodsCategory(BailianGoodsCategory goodsCategory) {
        Integer insert = goodsCategoryMapper.insert(goodsCategory);
        return goodsCategory;
    }

    @Override
    public Boolean updateGoodsCategory(BailianGoodsCategory goodsCategory) {
        return goodsCategoryMapper.updateById(goodsCategory) > 0;
    }

    @Override
    public Boolean deleteGoodsCategory(Integer categoryId) {
        List<Integer> idsList = new ArrayList<>();
        this.queryIdsList(categoryId,idsList);
        idsList.add(categoryId);
        return this.removeByIds(idsList);
    }

    @Override
    public Page<BailianGoodsCategory> queryGoodsCategoryByCategoryLevelAndParentId(Integer pageNo, Integer pageSize, Integer categoryLevel, Integer parentId) {
        Page<BailianGoodsCategory> page = new Page<>(pageNo,pageSize);
        return goodsCategoryMapper.selectPage(page,new QueryWrapper<BailianGoodsCategory>().eq("category_level",categoryLevel).eq("parent_id",parentId).orderByAsc("category_rank"));
    }

    @Override
    public void queryIdsList(Integer categoryId, List<Integer> ids) {
       QueryWrapper<BailianGoodsCategory> wrapper =new QueryWrapper<>();
       wrapper.eq("parent_id",categoryId).select("category_id");
       List<BailianGoodsCategory> list = goodsCategoryMapper.selectList(wrapper);
       list.stream().forEach(item ->{
           ids.add(item.getCategoryId());
           this.queryIdsList(item.getCategoryId(),ids);
       });
    }

    @Override
    public List<GoodsCategoryVO> queryCategory() {
        List<BailianGoodsCategory> firstGoodsCategory = goodsCategoryMapper.selectList(new QueryWrapper<BailianGoodsCategory>().eq("category_level",1).orderByDesc("category_rank").last("limit 0,10"));
        List<GoodsCategoryVO> first = new ArrayList<>();
        if (!firstGoodsCategory.isEmpty()) {
            for (BailianGoodsCategory bailianGoodsCategory : firstGoodsCategory) {
                GoodsCategoryVO firstvo = new GoodsCategoryVO();
                BeanUtils.copyProperties(bailianGoodsCategory, firstvo);
                // 二级分类
                List<BailianGoodsCategory> secondGoodsCategory = goodsCategoryMapper.selectList(new QueryWrapper<BailianGoodsCategory>().eq("parent_id", firstvo.getCategoryId()).orderByAsc("category_id"));
                List<GoodsCategorySecondVO> second = new ArrayList<>();
                if (!secondGoodsCategory.isEmpty()) {
                    for (BailianGoodsCategory goodsCategory : secondGoodsCategory) {
                        GoodsCategorySecondVO secondvo = new GoodsCategorySecondVO();
                        BeanUtils.copyProperties(goodsCategory, secondvo);
                        // 三级分类
                        List<BailianGoodsCategory> thirdGoodsCategory = goodsCategoryMapper.selectList(new QueryWrapper<BailianGoodsCategory>().eq("parent_id", secondvo.getCategoryId()).orderByAsc("category_id"));
                        List<GoodsCategoryThirdVO> third = new ArrayList<>();
                        if (!thirdGoodsCategory.isEmpty()) {
                            for (BailianGoodsCategory category : thirdGoodsCategory) {
                                GoodsCategoryThirdVO thirdVO = new GoodsCategoryThirdVO();
                                BeanUtils.copyProperties(category, thirdVO);
                                third.add(thirdVO);
                            }
                            secondvo.setGoodsCategoryThirdVOS(third);
                            second.add(secondvo);
                        }
                    }
                }
                firstvo.setGoodsCategorySecondVOS(second);
                first.add(firstvo);
            }
            return first;
        }
        return  null;
    }

    @Override
    public Page<BailianGoodsInfo> queryGoodsInfoByCategory(Integer pageNo, Integer pageSize, String  categoryName,Boolean flag) {
        Page<BailianGoodsInfo> page = new Page<>(pageNo,pageSize);
        BailianGoodsCategory goodsCategory = goodsCategoryMapper.selectOne(new QueryWrapper<BailianGoodsCategory>().eq("category_name",categoryName).select("category_id"));
        if (flag){
            return goodsInfoMapper.selectPage(page,new QueryWrapper<BailianGoodsInfo>().eq("goods_category_id",goodsCategory.getCategoryId()).orderByAsc("selling_price"));
        }
        return goodsInfoMapper.selectPage(page,new QueryWrapper<BailianGoodsInfo>().eq("goods_category_id",goodsCategory.getCategoryId()).orderByDesc("selling_price"));
    }

}
