package com.cuning.service.impl;
/*
 * @Created on : 2022/6/9 0009
 * <p>
 * @Author     : Administrator
 * <p>
 * @Description: GoodsInfoServiceImpl
 **/
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cuning.bean.goods.BailianGoodsInfo;
import com.cuning.mapper.GoodsInfoMapper;
import com.cuning.service.GoodsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoodsInfoServiceImpl extends ServiceImpl<GoodsInfoMapper, BailianGoodsInfo> implements GoodsInfoService {

    @Autowired(required = false)
    private GoodsInfoMapper goodsInfoMapper;

    @Override
    public BailianGoodsInfo saveGoods(BailianGoodsInfo goodsInfo) {
        Integer insert = goodsInfoMapper.insert(goodsInfo);
        return goodsInfo;
    }

    @Override
    public Page<BailianGoodsInfo> queryGoodsInfoPage(Integer pageNo, Integer pageSize,Boolean flag) {
        Page<BailianGoodsInfo> page = new Page<>(pageNo,pageSize);
        if (flag){
            return goodsInfoMapper.selectPage(page,new QueryWrapper<BailianGoodsInfo>().orderByAsc("selling_price"));
        }
        return goodsInfoMapper.selectPage(page,new QueryWrapper<BailianGoodsInfo>().orderByDesc("selling_price"));
    }


    @Override
    public Boolean updateGoodsInfo(BailianGoodsInfo goodsInfo) {
        return goodsInfoMapper.updateById(goodsInfo) > 0 ;
    }

    @Override
    public Boolean deleteGoodsInfo(Integer goodsId) {
        return this.removeById(goodsId);
    }
}
