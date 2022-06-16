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

public interface GoodsCommentaryService extends IService<BailianGoodsCommentary> {

    Page<BailianGoodsCommentary> queryGoodsCommentary(Integer pageNo,Integer pageSize,String goodsId,Integer commentaryType);

    boolean saveGoodsCommentary(Integer commentaryLevel, String goodsCommentary, String commentaryUrl, String userName, String userHeadImg, String goodsId,String userId,String orderNo);

    Boolean deleteGoodsCommentary(String userId,String orderNo,String goodsId,String commentaryId);

    Page<BailianOrderItem> queryGoodsCommentaryType(Integer pageNo,Integer pageSize,Integer commentaryType,String userId);

    Boolean updateOrderItemCommentaryType(String userId,String orderNo,String goodsId);

    Boolean queryOrderItem(String userId,String orderNo,String goodsId);
}
