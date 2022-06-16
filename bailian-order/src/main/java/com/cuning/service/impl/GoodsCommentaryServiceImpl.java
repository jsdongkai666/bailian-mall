package com.cuning.service.Impl;

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
    public Page<BailianGoodsCommentary> queryGoodsCommentary(Integer pageNo, Integer pageSize, String goodsId, Integer commentaryType) {
        Page<BailianGoodsCommentary> page = new Page<>(pageNo,pageSize);
        if (commentaryType == 0){
            return goodsCommentaryMapper.selectPage(page,new QueryWrapper<BailianGoodsCommentary>().eq("goods_id",goodsId).orderByDesc("commentary_time"));
        }
        return goodsCommentaryMapper.selectPage(page,new QueryWrapper<BailianGoodsCommentary>().eq("goods_id",goodsId).eq("commentary_type",commentaryType).orderByDesc("commentary_time"));
    }

    @Override
    public boolean saveGoodsCommentary(Integer commentaryLevel, String goodsCommentary,String commentaryUrl, String userName, String userHeadImg, String goodsId,String userId,String orderNo) {
        BailianGoodsCommentary goodsCommentary1 = new BailianGoodsCommentary();
        BailianOrder bailianOrder = shoppingOrderMapper.selectOne(new QueryWrapper<BailianOrder>().eq("user_id",userId).eq("order_no",orderNo));
        if (bailianOrder.getOrderStatus() == 4){
            BailianOrderItem bailianOrderItem = shoppingOrderItemMapper.selectOne(new QueryWrapper<BailianOrderItem>().eq("order_id",bailianOrder.getOrderId()).eq("goods_id",goodsId).ne("commentary_type",2));
            if (bailianOrderItem != null){
                goodsCommentary1.setUserImg(userHeadImg);
                goodsCommentary1.setUserName(userName);
                goodsCommentary1.setGoodsId(goodsId);
                if (commentaryLevel <= 0 || commentaryLevel >5){
                    return false;
                }
                if (commentaryLevel <= 2){
                    goodsCommentary1.setCommentaryType(1);
                } else if(commentaryLevel <4){
                    goodsCommentary1.setCommentaryType(2);
                } else {
                    goodsCommentary1.setCommentaryType(3);
                }
                goodsCommentary1.setOrderId(bailianOrderItem.getOrderId());
                goodsCommentary1.setCommentaryLevel(commentaryLevel);
                goodsCommentary1.setGoodsCommentary(goodsCommentary);
                goodsCommentary1.setCommentaryUrl(commentaryUrl);
                goodsCommentary1.setCommentaryTime(new Date());
            }
        }
        return goodsCommentaryMapper.insert(goodsCommentary1) > 0;
    }

    @Override
    public Boolean deleteGoodsCommentary(String userId,String orderNo,String goodsId) {
        BailianOrder bailianOrder = shoppingOrderMapper.selectOne(new QueryWrapper<BailianOrder>().eq("user_id",userId).eq("order_no",orderNo));
        BailianOrderItem bailianOrderItem  = shoppingOrderItemMapper.selectOne(new QueryWrapper<BailianOrderItem>().eq("order_id",bailianOrder.getOrderId()).eq("goods_id",goodsId));
        bailianOrderItem.setCommentaryType(2);
        int tag = shoppingOrderItemMapper.updateById(bailianOrderItem);
        List<BailianGoodsCommentary> list = goodsCommentaryMapper.selectList(new QueryWrapper<BailianGoodsCommentary>().eq("order_id",bailianOrder.getOrderId()).eq("goods_id",goodsId));
        if (list.isEmpty()){
            return false;
        }
        List<String> ids = new ArrayList<>();
        list.stream().forEach(item ->{
            ids.add(item.getCommentaryId());
        });
        return this.removeByIds(ids);
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
            List<String> idList = bailianOrderItemList.stream().map(item -> item.getGoodsId()).collect(Collectors.toList());
            QueryWrapper<BailianOrderItem> orderItemQueryWrapper = new QueryWrapper<>();
            return shoppingOrderItemMapper.selectPage(page, orderItemQueryWrapper.in("goods_id", idList).eq("commentary_type", commentaryType).orderByDesc("create_time"));
        }
        return null;
    }

    @Override
    public Boolean updateOrderItemCommentaryType(String userId,String orderNo,String goodsId) {

        BailianOrder bailianOrder1 = shoppingOrderMapper.selectOne(new QueryWrapper<BailianOrder>().eq("user_id",userId).eq("order_no",orderNo));
        BailianOrderItem bailianOrderItem  = shoppingOrderItemMapper.selectOne(new QueryWrapper<BailianOrderItem>().eq("order_id",bailianOrder1.getOrderId()).eq("goods_id",goodsId));
        bailianOrderItem.setCommentaryType(bailianOrderItem.getCommentaryType()+1);
        if (bailianOrderItem.getCommentaryType() > 2){
            return false;
        }
        return shoppingOrderItemMapper.updateById(bailianOrderItem) > 0;
    }

    @Override
    public Boolean queryOrderItem(String userId, String orderNo, String goodsId) {
        BailianOrder bailianOrder = shoppingOrderMapper.selectOne(new QueryWrapper<BailianOrder>().eq("user_id",userId).eq("order_no",orderNo));
        BailianOrderItem bailianOrderItem  = shoppingOrderItemMapper.selectOne(new QueryWrapper<BailianOrderItem>().eq("order_id",bailianOrder.getOrderId()).eq("goods_id",goodsId));
        if (bailianOrderItem.getCommentaryType()>2){
            return false;
        }
        return true;
    }


}
