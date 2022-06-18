package com.cuning.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cuning.bean.goods.BailianGoodsInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

/*
 * @Created on : 2022/6/14 0014
 * <p>
 * @Author     : Administrator
 * <p>
 * @Description: GoodsInfoService
 **/
@FeignClient(name = "bailian-goods")
public interface GoodsInfoFeignService {

    /***
     * @author : Administrator
     * @date   : 2022/6/18 0018
     * @param  : [com.cuning.bean.goods.BailianGoodsInfo, java.lang.String]
     * @return : com.cuning.bean.goods.BailianGoodsInfo
     * @description : 新增商品详情
     */
    @PostMapping("/addGoods")
    BailianGoodsInfo saveGoods(@RequestBody BailianGoodsInfo goodsInfo, @RequestParam("userId") String userId);

    /***
     * @author : Administrator
     * @date   : 2022/6/18 0018
     * @param  : [java.lang.Integer, java.lang.Integer]
     * @return : com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.cuning.bean.goods.BailianGoodsInfo>
     * @description : 分页查询商品详情
     */
    @GetMapping("/queryGoodsPage")
    Page<BailianGoodsInfo> queryGoodsInfoPage(@RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize);

    /***
     * @author : Administrator
     * @date   : 2022/6/18 0018
     * @param  : [com.cuning.bean.goods.BailianGoodsInfo, java.lang.String]
     * @return : com.cuning.bean.goods.BailianGoodsInfo
     * @description :修改商品详情
     */
    @PostMapping("/updateGoods")
    BailianGoodsInfo updateGoodsInfo(@RequestBody BailianGoodsInfo goodsInfo, @RequestParam("userId") String userId);

    /***
     * @author : Administrator
     * @date   : 2022/6/18 0018
     * @param  : [java.lang.String]
     * @return : java.lang.Boolean
     * @description : 删除商品详情
     */
    @GetMapping("/deleteGoods")
    Boolean deleteGoodsInfo(@RequestParam("goodsId") String goodsId);

    /***
     * @author : Administrator
     * @date   : 2022/6/18 0018
     * @param  : [java.lang.String, java.lang.Byte]
     * @return : java.lang.Boolean
     * @description : 上下架商品
     */
    @PostMapping("/updateStatus")
    Boolean updateGoodsSellStatus(@RequestParam("goodsId") String goodsId, @RequestParam("goodsSellStatus") Byte goodsSellStatus);

    /***
     * @author : Administrator
     * @date   : 2022/6/18 0018
     * @param  : [java.lang.String]
     * @return : com.cuning.bean.goods.BailianGoodsInfo
     * @description : 根据id查询商品详情
     */
    @GetMapping("/queryGoodsById")
    BailianGoodsInfo queryGoodsById(@RequestParam("goodsId") String goodsId);
}
