package com.cuning.service.impl;
/*
 * @Created on : 2022/6/9 0009
 * <p>
 * @Author     : Administrator
 * <p>
 * @Description: GoodsInfoServiceImpl
 **/

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cuning.bean.goods.BailianGoodsInfo;
import com.cuning.constant.CommonConstant;
import com.cuning.mapper.GoodsInfoMapper;
import com.cuning.service.GoodsInfoService;
import com.cuning.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GoodsInfoServiceImpl extends ServiceImpl<GoodsInfoMapper, BailianGoodsInfo> implements GoodsInfoService {

    @Autowired(required = false)
    private GoodsInfoMapper goodsInfoMapper;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public BailianGoodsInfo saveGoods(BailianGoodsInfo goodsInfo) {
        Integer insert = goodsInfoMapper.insert(goodsInfo);
        return goodsInfo;
    }

    @Override
    public Page<BailianGoodsInfo> queryGoodsInfoPage(Integer pageNo, Integer pageSize,String goodsName) {
        Page<BailianGoodsInfo> page = new Page<>(pageNo,pageSize);
        return goodsInfoMapper.selectPage(page,new QueryWrapper<BailianGoodsInfo>().like("goods_name",goodsName));
    }




    @Override
    public Boolean updateGoodsInfo(BailianGoodsInfo goodsInfo) {
        return goodsInfoMapper.updateById(goodsInfo) > 0 ;
    }

    @Override
    public Boolean deleteGoodsInfo(Integer goodsId) {
        return this.removeById(goodsId);
    }

    @Override
    public List<BailianGoodsInfo> selectGoodsByGoodsCategoryId(Integer categoryId) {
        QueryWrapper<BailianGoodsInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("goods_category_id",categoryId);
        return goodsInfoMapper.selectList(queryWrapper);
    }

    @Override
    public List<Integer> selectGoodsCategoryIds() {
        QueryWrapper<BailianGoodsInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("goods_category_id");
        List<BailianGoodsInfo> goodsInfoList = goodsInfoMapper.selectList(queryWrapper);
        return goodsInfoList.stream().map(BailianGoodsInfo::getGoodsCategoryId).collect(Collectors.toList());
    }

    @Override
    public BailianGoodsInfo queryGoodsInfoById(Integer goodsId) {
        return goodsInfoMapper.selectById(goodsId);
    }

    @Override
    public Map<String, String> setArrivalReminders(String userId, String goodsId) {

        List<Object> objects = redisUtils.lGet(goodsId + ":reminder", 0, -1);

        Map<String, String> result = new HashMap<>();

        for (Object object : objects) {
            String userIdStr = (String) object;

            if (userIdStr.equals(userId)) {
                redisUtils.lRemove(goodsId + ":reminder", 1, userId);
                result.put("code", CommonConstant.UNIFY_RETURN_SUCCESS_CODE);
                result.put("msg", "取消到货提醒成功！");
                return result;
            }

        }

        redisUtils.lSet(goodsId + ":reminder" ,userId );

        result.put("code", CommonConstant.UNIFY_RETURN_SUCCESS_CODE);
        result.put("msg", "设置到货提醒成功！");
        return result;
    }

    @Override
    public Boolean cancelArrivalReminders(String userId, String goodsId) {
        return redisUtils.lRemove(goodsId + ":reminder", 1, userId) > 0;
    }

    @Override
    public Boolean replenishment(String goodsId, Integer stockNum) {

        BailianGoodsInfo goods = this.getById(goodsId);

        goods.setStockNum(goods.getStockNum() + stockNum);

        return this.updateById(goods);
    }

    @Override
    public Map<String, String> collectGoods(String userId, String goodsId) {

        List<Object> objects = redisUtils.lGet(userId + ":collect", 0, -1);

        Map<String, String> result = new HashMap<>();

        for (Object object : objects) {
            String userIdStr = (String) object;

            if (userIdStr.equals(goodsId)) {
                redisUtils.lRemove(userId + ":collect", 1,goodsId);
                result.put("code", CommonConstant.UNIFY_RETURN_SUCCESS_CODE);
                result.put("msg", "取消收藏成功！");
                return result;
            }

        }
        // 收藏商品大于100，默认移除最早收藏的
        if (objects.size() > 100) {
            redisUtils.lGet(userId + ":collect",objects.size()-1,objects.size());
        }

        redisUtils.lSet(userId + ":collect" ,goodsId );

        result.put("code", CommonConstant.UNIFY_RETURN_SUCCESS_CODE);
        result.put("msg", "收藏成功！");
        return result;
    }

    @Override
    public List<Object> getCollectListByUserId(String userId) {
        List<Object> objects = redisUtils.lGet(userId + ":collect", 0, -1);
        return objects;
    }
}
