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
    /**
     * @author : wangdefeng
     * @date   : 2022/6/17
     * @param  : [com.cuning.bean.seckill.BailianSeckill]
     * @return : java.lang.Boolean
     * @description : 增加抢购商品
     */
    Boolean insertSeckillShop(BailianSeckill bailianSeckill);
    /**
     * @author : wangdefeng
     * @date   : 2022/6/17
     * @param  : [java.lang.String]
     * @return : java.lang.Boolean
     * @description : 删除抢购商品
     */
    Boolean deleteSeckillShop(String goodsId);
    /**
     * @author : wangdefeng
     * @date   : 2022/6/17
     * @param  : [com.cuning.bean.seckill.BailianSeckill]
     * @return : java.lang.Boolean
     * @description : 更新抢购商品
     */
    Boolean updateSeckillShop(BailianSeckill bailianSeckill);
    /**
     * @author : wangdefeng
     * @date   : 2022/6/17
     * @param  : [java.lang.String]
     * @return : com.cuning.bean.seckill.BailianSeckill
     * @description : 搜索抢购商品
     */
    BailianSeckill selectSeckillShop(String goodsName);
}
