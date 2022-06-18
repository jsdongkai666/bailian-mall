package com.cuning.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cuning.bean.goods.BailianGoodsCommentary;
import com.cuning.bean.shoppingOrder.BailianOrderItem;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/*
 * @Created on : 2022/6/15 0015
 * <p>
 * @Author     : Administrator
 * <p>
 * @Description: GoodsCommentaryFeignService
 **/
@FeignClient(name = "bailian-order")
public interface GoodsCommentaryFeignService {

    @GetMapping("/queryGoodsCommentary")
    Page<BailianGoodsCommentary> queryGoodsCommentary(@RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize,
                                                      @RequestParam("goodsId") String goodsId, @RequestParam("commentaryType") Integer commentaryType);

    @PostMapping("/saveGoodsCommentary")
    Map<String, String> saveGoodsCommentary(@RequestParam("userId") String userId, @RequestParam("userName") String userName,
                                            @RequestParam("userHeadImg") String userHeadImg, @RequestParam("orderNo") String orderNo,
                                            @RequestParam("goodsId") String goodsId, @RequestParam("commentaryLevel") Integer commentaryLevel,
                                            @RequestParam("goodsCommentary") String goodsCommentary, @RequestParam("commentaryUrl") String commentaryUrl);

    @GetMapping("/deleteGoodsCommentary")
    Map<String, String> deleteGoodsCommentary(@RequestParam("userId") String userId, @RequestParam("orderNo") String orderNo,
                                              @RequestParam("goodsId") String goodsId);

    @GetMapping("/queryGoodsCommentaryType")
    Page<BailianOrderItem> queryGoodsCommentaryType(@RequestParam("userId") String userId, @RequestParam("pageNo") Integer pageNo,
                                                    @RequestParam("pageSize") Integer pageSize, @RequestParam("commentaryType") Integer commentaryType);

    @GetMapping("/SensitiveWord")
    Boolean seneitiveWord(@RequestParam("goodsCommentary") String goodsCommentary);
}
