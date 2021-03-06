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
import com.cuning.util.SnowFlake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
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

    @Autowired
    private SnowFlake snowFlake;


    @Override
    public Page<BailianGoodsCommentary> queryGoodsCommentary(Integer pageNo, Integer pageSize, String goodsId, Integer commentaryType) {
        Page<BailianGoodsCommentary> page = new Page<>(pageNo, pageSize);
        if (commentaryType == 0) {
            return goodsCommentaryMapper.selectPage(page, new QueryWrapper<BailianGoodsCommentary>().eq("goods_id", goodsId).orderByDesc("commentary_time"));
        }
        return goodsCommentaryMapper.selectPage(page, new QueryWrapper<BailianGoodsCommentary>().eq("goods_id", goodsId).eq("commentary_type", commentaryType).orderByDesc("commentary_time"));
    }

    @Override
    public Map<String, String> saveGoodsCommentary(Integer commentaryLevel, String goodsCommentary, String commentaryUrl, String userName, String userHeadImg, String goodsId, String userId, String orderNo) {
        BailianGoodsCommentary goodsCommentary1 = new BailianGoodsCommentary();
        Map<String, String> map = new HashMap<>();
        BailianOrder bailianOrder = shoppingOrderMapper.selectOne(new QueryWrapper<BailianOrder>().eq("user_id", userId).eq("order_no", orderNo));
        BailianOrderItem bailianOrderItem = shoppingOrderItemMapper.selectOne(new QueryWrapper<BailianOrderItem>().eq("order_id", bailianOrder.getOrderId()).eq("goods_id", goodsId).ne("commentary_type", 2));
        goodsCommentary1.setCommentaryId("11" + Long.toString(snowFlake.nextId()).substring(9, 19));
        goodsCommentary1.setUserImg(userHeadImg);
        goodsCommentary1.setUserName(userName);
        goodsCommentary1.setGoodsId(goodsId);
        if (commentaryLevel <= 2) {
            goodsCommentary1.setCommentaryType(1);
        } else if (commentaryLevel < 4) {
            goodsCommentary1.setCommentaryType(2);
        } else {
            goodsCommentary1.setCommentaryType(3);
        }
        goodsCommentary1.setOrderId(bailianOrderItem.getOrderId());
        goodsCommentary1.setCommentaryLevel(commentaryLevel);
        goodsCommentary1.setGoodsCommentary(goodsCommentary);
        goodsCommentary1.setCommentaryUrl(commentaryUrl);
        goodsCommentary1.setCommentaryTime(new Date());
        int tag = goodsCommentaryMapper.insert(goodsCommentary1);
        map.put("code", "200");
        map.put("msg", "????????????");
        return map;
    }

    @Override
    public Map<String, String> deleteGoodsCommentary(String userId, String orderNo, String goodsId) {
        BailianOrder bailianOrder = shoppingOrderMapper.selectOne(new QueryWrapper<BailianOrder>().eq("user_id", userId).eq("order_no", orderNo));
        Map<String, String> map = new HashMap<>();
        if (bailianOrder == null) {
            map.put("code", "500");
            map.put("msg", "???????????????");
            return map;
        }
        BailianOrderItem bailianOrderItem = shoppingOrderItemMapper.selectOne(new QueryWrapper<BailianOrderItem>().eq("order_id", bailianOrder.getOrderId()).eq("goods_id", goodsId));
        if (bailianOrderItem == null) {
            map.put("code", "500");
            map.put("msg", "?????????????????????????????????");
            return map;
        }
        bailianOrderItem.setCommentaryType(2);
        int tag = shoppingOrderItemMapper.updateById(bailianOrderItem);
        List<BailianGoodsCommentary> list = goodsCommentaryMapper.selectList(new QueryWrapper<BailianGoodsCommentary>().eq("order_id", bailianOrder.getOrderId()).eq("goods_id", goodsId));
        if (list.isEmpty()) {
            map.put("code", "500");
            map.put("msg", "??????????????????");
            return map;
        }
        List<String> ids = new ArrayList<>();
        list.stream().forEach(item -> {
            ids.add(item.getCommentaryId());
        });
        if (this.removeByIds(ids)) {
            map.put("code", "200");
            map.put("msg", "?????????????????????");
        } else {
            map.put("msg", "?????????????????????");
        }
        return map;
    }

    @Override
    public Page<BailianOrderItem> queryGoodsCommentaryType(Integer pageNo, Integer pageSize, Integer commentaryType, String userId) {
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
            List<String> idList = bailianOrderItemList.stream().map(item -> item.getOrderItemId()).collect(Collectors.toList());
            QueryWrapper<BailianOrderItem> orderItemQueryWrapper = new QueryWrapper<>();
            return shoppingOrderItemMapper.selectPage(page, orderItemQueryWrapper.in("order_item_id", idList).eq("commentary_type", commentaryType).orderByDesc("create_time"));
        }
        return null;
    }

    @Override
    public void updateOrderItemCommentaryType(String userId, String orderNo, String goodsId) {

        BailianOrder bailianOrder1 = shoppingOrderMapper.selectOne(new QueryWrapper<BailianOrder>().eq("user_id", userId).eq("order_no", orderNo));
        BailianOrderItem bailianOrderItem = shoppingOrderItemMapper.selectOne(new QueryWrapper<BailianOrderItem>().eq("order_id", bailianOrder1.getOrderId()).eq("goods_id", goodsId));
        bailianOrderItem.setCommentaryType(bailianOrderItem.getCommentaryType() + 1);
        int flag = shoppingOrderItemMapper.updateById(bailianOrderItem);
    }

    @Override
    public Map<String, String> queryOrderItem(String userId, String orderNo, String goodsId) {
        BailianOrder bailianOrder = shoppingOrderMapper.selectOne(new QueryWrapper<BailianOrder>().eq("user_id", userId).eq("order_no", orderNo));
        Map<String, String> map = new HashMap<>();
        map.put("code", "200");
        map.put("msg", "???????????????????????????");
        if (bailianOrder == null) {
            map.put("code", "500");
            map.put("msg", "???????????????");
            return map;
        }
        if (bailianOrder.getOrderStatus() != 4) {
            map.put("code", "500");
            map.put("msg", "???????????????");
            return map;
        }
        BailianOrderItem bailianOrderItem = shoppingOrderItemMapper.selectOne(new QueryWrapper<BailianOrderItem>().eq("order_id", bailianOrder.getOrderId()).eq("goods_id", goodsId));
        if (bailianOrderItem == null) {
            map.put("code", "500");
            map.put("msg", "??????????????????");
            return map;
        }
        if (bailianOrderItem.getCommentaryType() >= 2) {
            map.put("code", "500");
            map.put("msg", "?????????????????????");
            return map;
        }
        return map;
    }

    @Override
    public Integer selectCommentaryCount(String goodsId) {
        QueryWrapper<BailianGoodsCommentary> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("goods_id", goodsId);
        return goodsCommentaryMapper.selectCount(queryWrapper);
    }


}
