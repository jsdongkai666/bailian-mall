package com.cuning.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cuning.bean.goods.BailianGoodsCommentary;
import com.cuning.bean.shoppingOrder.BailianOrder;
import com.cuning.bean.shoppingOrder.BailianOrderItem;
import com.cuning.mapper.OrderItemMapper;
import com.cuning.mapper.ShoppingOrderMapper;
import com.cuning.service.GoodsCommentaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
/*
 * @Created on : 2022/6/13 0013
 * <p>
 * @Author     : Administrator
 * <p>
 * @Description: GoodsCommentaryController
 **/
@Slf4j
@RestController
public class GoodsCommentaryController {

    @Autowired
    private GoodsCommentaryService goodsCommentaryService;

    @Autowired(required = false)
    private ShoppingOrderMapper shoppingOrderMapper;

    @Autowired(required = false)
    private OrderItemMapper orderItemMapper;

    /***
     * @author : Administrator
     * @date   : 2022/6/13 0013
     * @param  : [java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer]
     * @return : com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.cuning.bean.goods.BailianGoodsCommentary>
     * @description : 根据商品id和评论类型对商品评论进行分页查询
     */
    @GetMapping("/queryGoodsCommentary")
    public Page<BailianGoodsCommentary> queryGoodsCommentary(@RequestParam Integer pageNo, @RequestParam Integer pageSize, @RequestParam Integer goodsId, @RequestParam Integer commentaryType){
        return goodsCommentaryService.queryGoodsCommentary(pageNo,pageSize,goodsId,commentaryType);
    }

    /***
     * @author : Administrator
     * @date   : 2022/6/13 0013
     * @param  : [javax.servlet.http.HttpServletRequest, java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String]
     * @return : com.cuning.bean.goods.BailianGoodsCommentary
     * @description : 对已完成订单中的商品进行评论或者追评
     */
    @PostMapping("/saveGoodsCommentary")
    public BailianGoodsCommentary saveGoodsCommentary(@RequestParam String userId,@RequestParam String userName,@RequestParam String userHeadImg,@RequestParam String orderNo,@RequestParam Integer goodsId,
                                                      @RequestParam Integer commentaryLevel, @RequestParam String goodsCommentary, @RequestParam String commentaryUrl){
        BailianGoodsCommentary bailianGoodsCommentary = new BailianGoodsCommentary();
        BailianOrder bailianOrder = shoppingOrderMapper.selectOne(new QueryWrapper<BailianOrder>().eq("user_id",userId).eq("order_no",orderNo));
        if ("4".equals(bailianOrder.getOrderStatus())){
            BailianOrderItem bailianOrderItem = orderItemMapper.selectOne(new QueryWrapper<BailianOrderItem>().eq("order_id",bailianOrder.getOrderId()).eq("goods_id",goodsId).ne("commentary_type",2));
            if (bailianOrderItem.getOrderItemId()!=null){
                bailianGoodsCommentary.setUserName(userName);
                bailianGoodsCommentary.setUserImg(userHeadImg);
                return goodsCommentaryService.saveGoodsCommentary(commentaryLevel,goodsCommentary,commentaryUrl);
            }
        }

        return null;
    }

    /***
     * @author : Administrator
     * @date   : 2022/6/13 0013
     * @param  : [java.lang.Integer]
     * @return : boolean
     * @description : 删除评论
     */
    @GetMapping("/deleteGoodsCommentary")
    public boolean deleteGoodsCommentary(Integer commentaryId){
        return goodsCommentaryService.deleteGoodsCommentary(commentaryId);
    }

    /***
     * @author : Administrator
     * @date   : 2022/6/13 0013
     * @param  : [javax.servlet.http.HttpServletRequest, java.lang.Integer, java.lang.Integer, java.lang.Integer]
     * @return : com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.cuning.bean.shoppingOrder.BailianOrderItem>
     * @description :  分页查看待评价和已评价
     */
    @GetMapping("/queryGoodsCommentaryType")
    public Page<BailianOrderItem> queryGoodsCommentaryType(@RequestParam String userId,@RequestParam Integer pageNo,@RequestParam Integer pageSize,@RequestParam Integer commentaryType){
        return goodsCommentaryService.queryGoodsCommentaryType(pageNo,pageSize,commentaryType,userId);
    }
}
