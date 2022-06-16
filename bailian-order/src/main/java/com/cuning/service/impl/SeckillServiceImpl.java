package com.cuning.service.impl;

import com.cuning.bean.seckill.BailianSeckill;
import com.cuning.mapper.SeckillMappper;
import com.cuning.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created On : 2022/6/16.
 * <p>
 * Author     : WangDeFeng
 * <p>
 * Description: SeckillServiceImpl
 */
public class SeckillServiceImpl implements SeckillService {

    @Autowired
    private SeckillMappper seckillMappper;

    @Override
    public Boolean insertSeckillShop(BailianSeckill bailianSeckill) {
        if(bailianSeckill.getGoodsNum()<=0){
            return false;
        }
        if(bailianSeckill.getGoodsPrice()<=0){
            return false;
        }
        if(bailianSeckill.getCategoryId()<=0){
            return false;
        }
        return seckillMappper.insert(bailianSeckill)>0;
    }
}
