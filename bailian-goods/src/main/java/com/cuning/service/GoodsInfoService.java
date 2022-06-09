package com.cuning.service;
/*
 * @Created on : 2022/6/9 0009
 * <p>
 * @Author     : Administrator
 * <p>
 * @Description: GoodsInfoService
 **/

import com.baomidou.mybatisplus.extension.service.IService;
import com.cuning.bean.GoodsInfo;

public interface GoodsInfoService extends IService<GoodsInfo> {

    GoodsInfo saveGoods (GoodsInfo goodsInfo);
}
