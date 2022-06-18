package com.cuning.service; /*
 * @Created on : 2022/6/13 0013
 * <p>
 * @Author     : Administrator
 * <p>
 * @Description: GoodsCommentaryService
 **/

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cuning.bean.goods.BailianGoodsCommentary;
import com.cuning.bean.shoppingOrder.BailianOrderItem;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

public interface GoodsCommentaryService extends IService<BailianGoodsCommentary> {

    /***
     * @author : Administrator
     * @date   : 2022/6/18 0018
     * @param  : [java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.Integer]
     * @return : com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.cuning.bean.goods.BailianGoodsCommentary>
     * @description : 分页查询商品评论，可分为差评，中评，好评
     */
    Page<BailianGoodsCommentary> queryGoodsCommentary(Integer pageNo, Integer pageSize, String goodsId, Integer commentaryType);

    /***
     * @author : Administrator
     * @date   : 2022/6/18 0018
     * @param  : [java.lang.Integer, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String]
     * @return : java.util.Map<java.lang.String,java.lang.String>
     * @description : 评论商品
     */
    Map<String, String> saveGoodsCommentary(Integer commentaryLevel, String goodsCommentary, String commentaryUrl, String userName, String userHeadImg, String goodsId, String userId, String orderNo);

    /***
     * @author : Administrator
     * @date   : 2022/6/18 0018
     * @param  : [java.lang.String, java.lang.String, java.lang.String]
     * @return : java.util.Map<java.lang.String,java.lang.String>
     * @description : 删除评论
     */
    Map<String, String> deleteGoodsCommentary(String userId, String orderNo, String goodsId);

    /***
     * @author : Administrator
     * @date   : 2022/6/18 0018
     * @param  : [java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.String]
     * @return : com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.cuning.bean.shoppingOrder.BailianOrderItem>
     * @description : 分页查询待评论和已评论
     */
    Page<BailianOrderItem> queryGoodsCommentaryType(Integer pageNo, Integer pageSize, Integer commentaryType, String userId);

    /***
     * @author : Administrator
     * @date   : 2022/6/18 0018
     * @param  : [java.lang.String, java.lang.String, java.lang.String]
     * @return : void
     * @description : 评论结束后，修改商品评论类型
     */
    void updateOrderItemCommentaryType(String userId, String orderNo, String goodsId);

    /***
     * @author : Administrator
     * @date   : 2022/6/18 0018
     * @param  : [java.lang.String, java.lang.String, java.lang.String]
     * @return : java.util.Map<java.lang.String,java.lang.String>
     * @description : 查询此用户是否下过该订单，订单中是否有此商品，商品能否评论
     */
    Map<String, String> queryOrderItem(String userId, String orderNo, String goodsId);

    /**
     * @param : [java.lang.String]
     * @return : java.lang.Integer
     * @author : lixu
     * @date : 2022/06/16
     * @description : 根据商品id，查询商品的评价数
     */
    Integer selectCommentaryCount(String goodsId);
}
