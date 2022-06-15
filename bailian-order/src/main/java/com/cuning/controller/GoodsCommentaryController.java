package com.cuning.controller;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cuning.bean.goods.BailianGoodsCommentary;
import com.cuning.bean.shoppingOrder.BailianOrderItem;
import com.cuning.service.GoodsCommentaryService;
import com.cuning.util.SensitiveWordFilterUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
    private SensitiveWordFilterUtil sensitiveWordFilterUtil;

    @Autowired
    private GoodsCommentaryService goodsCommentaryService;


    /***
     * @author : Administrator
     * @date   : 2022/6/13 0013
     * @param  : [java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer]
     * @return : com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.cuning.bean.goods.BailianGoodsCommentary>
     * @description : 根据商品id和评论类型对商品评论进行分页查询
     */
    @GetMapping("/queryGoodsCommentary")
    public Page<BailianGoodsCommentary> queryGoodsCommentary(@RequestParam Integer pageNo, @RequestParam Integer pageSize, @RequestParam String goodsId, @RequestParam Integer commentaryType){
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
    public boolean saveGoodsCommentary(@RequestParam String userId,@RequestParam String userName,@RequestParam String userHeadImg,@RequestParam String orderNo,@RequestParam String goodsId,
                                                      @RequestParam Integer commentaryLevel, @RequestParam String goodsCommentary, @RequestParam String commentaryUrl){
        Boolean flag = goodsCommentaryService.saveGoodsCommentary(commentaryLevel,goodsCommentary,commentaryUrl,userName,userHeadImg,goodsId,userId,orderNo);
        if (flag){
            if(goodsCommentaryService.updateOrderItemCommentaryType(userId,orderNo,goodsId)){
                return true;
            }
        }
        return false;
    }

    /***
     * @author : Administrator
     * @date   : 2022/6/13 0013
     * @param  : [java.lang.Integer]
     * @return : boolean
     * @description : 删除评论
     */
    @GetMapping("/deleteGoodsCommentary")
    public boolean deleteGoodsCommentary(@RequestParam String commentaryId){
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

    /***
     * @author : Administrator
     * @date   : 2022/6/15 0015
     * @param  : [java.lang.String]
     * @return : java.lang.Boolean
     * @description : 敏感词校验
     */
    @GetMapping("/SensitiveWord")
    public Boolean seneitiveWord(@RequestParam String goodsCommentary) {
        return sensitiveWordFilterUtil.isContainSensitiveWord(goodsCommentary);
    }

}
