package com.cuning.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cuning.bean.goods.BailianGoodsCommentary;
import com.cuning.bean.shoppingOrder.BailianOrder;
import com.cuning.bean.shoppingOrder.BailianOrderItem;
import com.cuning.mapper.GoodsCommentaryMapper;
import com.cuning.mapper.ShoppingOrderItemMapper;
import com.cuning.mapper.ShoppingOrderMapper;
import com.cuning.service.GoodsCommentaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/*
 * @Created on : 2022/6/13 0013
 * <p>
 * @Author     : Administrator
 * <p>
 * @Description: GoodsCommentaryServiceImpl
 **/
@Service
public class GoodsCommentaryServiceImpl extends ServiceImpl<GoodsCommentaryMapper, BailianGoodsCommentary> implements GoodsCommentaryService {

    @Autowired(required = false)
    private GoodsCommentaryMapper goodsCommentaryMapper;

    @Autowired(required = false)
    private ShoppingOrderMapper shoppingOrderMapper;

    @Autowired(required = false)
    private ShoppingOrderItemMapper shoppingOrderItemMapper;

    @Override
    public Page<BailianGoodsCommentary> queryGoodsCommentary(Integer pageNo, Integer pageSize, Integer goodsId, Integer commentaryType) {
        Page<BailianGoodsCommentary> page = new Page<>(pageNo,pageSize);
        if (commentaryType == 0){
            return goodsCommentaryMapper.selectPage(page,new QueryWrapper<BailianGoodsCommentary>().eq("goods_id",goodsId).orderByDesc("commentary_time"));
        }
        return goodsCommentaryMapper.selectPage(page,new QueryWrapper<BailianGoodsCommentary>().eq("goods_id",goodsId).eq("commentary_type",commentaryType).orderByDesc("commentary_time"));
    }

    @Override
    public boolean saveGoodsCommentary(Integer commentaryLevel, String goodsCommentary,String commentaryUrl, String userName, String userHeadImg, Integer goodsId,String userId,String orderNo) {
        BailianGoodsCommentary goodsCommentary1 = new BailianGoodsCommentary();
//      if(sensitiveWordFeignService.testSensitiveWord(goodsCommentary) != null){
        BailianOrder bailianOrder = shoppingOrderMapper.selectOne(new QueryWrapper<BailianOrder>().eq("user_id",userId).eq("order_no",orderNo));
        if (bailianOrder.getOrderStatus() == 4){
            BailianOrderItem bailianOrderItem = shoppingOrderItemMapper.selectOne(new QueryWrapper<BailianOrderItem>().eq("order_id",bailianOrder.getOrderId()).eq("goods_id",goodsId).ne("commentary_type",2));
            if (bailianOrderItem.getOrderItemId()!=null){
                goodsCommentary1.setUserName(userName);
                goodsCommentary1.setUserImg(userHeadImg);
                goodsCommentary1.setGoodsId(goodsId);
            }
        }
//      }
        if (commentaryLevel <= 0 || commentaryLevel >5){
            return false;
        }
        if (commentaryLevel <= 2){
            goodsCommentary1.setCommentaryType(1);
        } else if(commentaryLevel <4){
            goodsCommentary1.setCommentaryId(2);
        } else {
            goodsCommentary1.setCommentaryType(3);
        }
        goodsCommentary1.setCommentaryLevel(commentaryLevel);
        goodsCommentary1.setGoodsCommentary(goodsCommentary);
        goodsCommentary1.setCommentaryUrl(commentaryUrl);
        goodsCommentary1.setCommentaryTime(new Date());
        return goodsCommentaryMapper.insert(goodsCommentary1) > 0;
    }

    @Override
    public Boolean deleteGoodsCommentary(Integer commentaryId) {
        return this.removeById(commentaryId);
    }

    @Override
    public Page<BailianOrderItem> queryGoodsCommentaryType(Integer pageNo, Integer pageSize, Integer commentaryType,String userId) {
        Page<BailianOrderItem> page = new Page<>(pageNo, pageSize);
        QueryWrapper<BailianOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId).eq("order_status", 4);
        List<BailianOrder> bailianOrders = shoppingOrderMapper.selectList(queryWrapper);
        if (!bailianOrders.isEmpty()) {
            List<BailianOrderItem> bailianOrderItemList = new ArrayList<>();
            bailianOrders.stream().forEach(item -> {
                QueryWrapper<BailianOrderItem> wrapper = new QueryWrapper<>();
                wrapper.eq("order_id", item.getOrderId());
                List<BailianOrderItem> bailianOrderItems = shoppingOrderItemMapper.selectList(wrapper);
                bailianOrderItemList.addAll(bailianOrderItems);
            });
            List<Integer> idList = bailianOrderItemList.stream().map(item -> item.getGoodsId()).collect(Collectors.toList());
            QueryWrapper<BailianOrderItem> orderItemQueryWrapper = new QueryWrapper<>();
            return shoppingOrderItemMapper.selectPage(page, orderItemQueryWrapper.in("goods_id", idList).eq("commentary_type", commentaryType).orderByDesc("create_time"));
        }
        return null;
    }
}
