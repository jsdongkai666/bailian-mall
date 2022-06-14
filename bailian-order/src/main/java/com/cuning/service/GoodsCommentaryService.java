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

public interface GoodsCommentaryService extends IService<BailianGoodsCommentary> {

    Page<BailianGoodsCommentary> queryGoodsCommentary(Integer pageNo,Integer pageSize,Integer goodsId,Integer commentaryType);

    BailianGoodsCommentary saveGoodsCommentary(Integer commentaryLevel,String goodsCommentary,String commentaryUrl);

    Boolean deleteGoodsCommentary(Integer commentaryId);

    Page<BailianOrderItem> queryGoodsCommentaryType(Integer pageNo,Integer pageSize,Integer commentaryType,String userId);

}
