package com.cuning.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.cuning.bean.seckill.BailianSeckill;
import com.cuning.bean.seckill.BailianSeckillUser;
import com.cuning.bean.shoppingOrder.BailianOrder;
import com.cuning.mapper.SeckillMappper;
import com.cuning.mapper.SeckillUserMapper;
import com.cuning.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created On : 2022/6/16.
 * <p>
 * Author     : WangDeFeng
 * <p>
 * Description: SeckillServiceImpl
 */
@Service
public class SeckillServiceImpl implements SeckillService {

    @Autowired
    private SeckillMappper seckillMappper;

    @Autowired
    private SeckillUserMapper seckillUserMapper;

    @Override
    public Boolean insertSeckillShop(BailianSeckill bailianSeckill) {
        if (bailianSeckill.getGoodsNum() <= 0) {
            return false;
        }
        if (bailianSeckill.getGoodsPrice() <= 0) {
            return false;
        }
        if (bailianSeckill.getCategoryId() <= 0) {
            return false;
        }
        if (bailianSeckill.getGoodsName()=="") {
            return false;
        }
        Calendar calendar = new GregorianCalendar();
        bailianSeckill.setGoodsCreateTime(new Date());
        calendar.setTime(bailianSeckill.getGoodsCreateTime());
        calendar.add(calendar.DATE, 10);
        bailianSeckill.setGoodsStartTime(calendar.getTime());
        calendar.add(calendar.DATE, 30);
        bailianSeckill.setGoodsEndTime(calendar.getTime());
        return seckillMappper.insert(bailianSeckill) > 0;
    }

    @Override
    public Boolean deleteSeckillShop(String goodsId) {
        QueryWrapper<BailianSeckillUser> bailianSeckillUserQueryWrapper = new QueryWrapper<>();
        bailianSeckillUserQueryWrapper.eq("goods_id", goodsId);
        if (seckillUserMapper.delete(bailianSeckillUserQueryWrapper) > 0) {
            return seckillMappper.deleteById(goodsId) > 0;
        }
        return false;
    }

    @Override
    public Boolean updateSeckillShop(BailianSeckill bailianSeckill) {
        UpdateWrapper<BailianSeckill> updateWrapper = new UpdateWrapper<>();
        if (bailianSeckill.getGoodsPrice() != 0) updateWrapper.set("goods_price", bailianSeckill.getGoodsPrice());
        if (bailianSeckill.getGoodsNum() != 0) updateWrapper.set("goods_num", bailianSeckill.getGoodsNum());
        if (bailianSeckill.getCategoryId() != 0) updateWrapper.set("category_id", bailianSeckill.getCategoryId());
        if (!bailianSeckill.getGoodsName().isEmpty()) updateWrapper.set("category_id", bailianSeckill.getCategoryId());
        if (!bailianSeckill.getGoodsId().isEmpty()) {
            updateWrapper.eq("order_id", bailianSeckill.getGoodsId());
            updateWrapper.eq("goods_update_time",new Date());
            return seckillMappper.update(null,updateWrapper)>0;
        }
        return false;
    }

    @Override
    public BailianSeckill selectSeckillShop(String goodsName) {
        QueryWrapper<BailianSeckill> bailianSeckillQueryWrapper = new QueryWrapper<>();
        bailianSeckillQueryWrapper.eq("goods_name",goodsName);
        return seckillMappper.selectOne(bailianSeckillQueryWrapper);
    }


}
