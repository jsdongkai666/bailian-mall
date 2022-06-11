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
import com.cuning.bean.BailianGoodsInfo;
import com.cuning.mapper.GoodsInfoMapper;
import com.cuning.service.GoodsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsInfoServiceImpl extends ServiceImpl<GoodsInfoMapper, BailianGoodsInfo> implements GoodsInfoService {

    @Autowired(required = false)
    private GoodsInfoMapper goodsInfoMapper;

    @Override
    public BailianGoodsInfo saveGoods(BailianGoodsInfo goodsInfo) {
        boolean insert = this.save(goodsInfo);
        return goodsInfo;
    }

    @Override
    public Page<BailianGoodsInfo> queryGoodsInfoPage(Integer pageNo, Integer pageSize,String goodsName) {
        Page<BailianGoodsInfo> page = new Page<>(pageNo,pageSize);
        return goodsInfoMapper.selectPage(page,new QueryWrapper<BailianGoodsInfo>().eq("goods_name",goodsName));
    }

    @Override
    public BailianGoodsInfo queryGoodsInfoById(Integer goodsId) {
        return goodsInfoMapper.selectById(goodsId);
    }

//    @Override
//    public List<BailianGoodsInfo> queryGoodsInfo(String goodsName) {
//        List<BailianGoodsInfo> bailianGoodsInfoList = goodsInfoMapper.selectList(new QueryWrapper<BailianGoodsInfo>().like("goods_name",goodsName));
//        return bailianGoodsInfoList;
//    }

    @Override
    public Boolean updateGoodsInfo(BailianGoodsInfo goodsInfo) {
        return this.updateById(goodsInfo);
    }

    @Override
    public Boolean deleteGoodsInfo(Integer goodsId) {
        return this.removeById(goodsId);
    }
}
