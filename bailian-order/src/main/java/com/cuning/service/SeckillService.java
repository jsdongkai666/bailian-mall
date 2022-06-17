package com.cuning.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cuning.bean.seckill.BailianSeckill;

/**
 * Created On : 2022/6/16.
 * <p>
 * Author     : WangDeFeng
 * <p>
 * Description: SeckillService
 */
public interface SeckillService extends IService<BailianSeckill> {

    Boolean insertSeckillShop(BailianSeckill bailianSeckill);

    Boolean deleteSeckillShop(String goodsId);

    Boolean updateSeckillShop(BailianSeckill bailianSeckill);

    BailianSeckill selectSeckillShop(String goodsName);
}
