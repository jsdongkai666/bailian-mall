package com.cuning.controller.order;

import com.cuning.annotation.CheckToken;
import com.cuning.bean.goods.BailianGoodsInfo;
import com.cuning.bean.shoppingcarts.BailianCartProducts;
import com.cuning.bean.shoppingcarts.BailianCarts;
import com.cuning.bean.user.User;
import com.cuning.service.order.CartsService;
import com.cuning.util.JwtUtil;
import com.cuning.util.RequestResult;
import com.cuning.util.ResultBuildUtil;
import com.cuning.util.SnowFlake;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@Api(tags = "购物车操作")
public class ShoppingCartsController {

    @Autowired
    private SnowFlake snowFlake;

    @Autowired
    private CartsService cartsService;

    @ApiOperation("分页获取购物车列表")
    @GetMapping("/getCartsList")
    @CheckToken
    public RequestResult getCartsList(@RequestParam Integer pageNo, @RequestParam Integer pageSize, HttpServletRequest request) throws Exception {
        String token = request.getHeader("token");
        User user = JwtUtil.parseJWT(token);
        //校验用户数目
        if (user.getUserId() == null || user.getUserId() == ""){
            return ResultBuildUtil.fail("用户数据错误");
        }

        //获取购物车信息
        List<BailianCarts> resultList = cartsService.getCartsList(pageNo,pageSize,user.getUserId());

        //获取信息校验
        if (resultList == null || resultList.size() ==0){
           return ResultBuildUtil.fail("查询失败，未查询到结果");
        }
        return ResultBuildUtil.success(resultList);
    }

    @ApiOperation("商品添加至添加购物车")
    @CheckToken
    @PostMapping("/addGoods2Carts")
    public RequestResult addGoods2Carts(@RequestParam String goodsId,@RequestParam Integer buyCount,HttpServletRequest request) throws Exception {
        String token = request.getHeader("token");
        User user = JwtUtil.parseJWT(token);
        log.info("----当前操作用户为：{} : {}----",user.getUserId(),user.getUserName());
        //校验用户数目
        if (user.getUserId() == null || user.getUserId() == ""){
            return ResultBuildUtil.fail("用户数据错误");
        }

        //查询商品详细信息，判断商品正确性
        BailianGoodsInfo goodsDetail = cartsService.getGoodsDetail(goodsId);
        log.info("----用户所要加入购物车的商品信息为：{}",goodsDetail);
        if (goodsDetail == null){
            return ResultBuildUtil.fail("商品信息有误，请确认商品是否已经下架");
        }


        //判断该用户是否存在购物车
        //有购物车就 直接增加购物车物品 ，无购物车，先创建购物车，然后创建购物车详情
        BailianCarts bailianCarts = cartsService.getCartsById(user.getUserId());
        BailianCartProducts products = new BailianCartProducts();
        if (bailianCarts == null){
            long nextId = snowFlake.nextId();

            //根据用户id创建购物车篮子
            BailianCarts carts = new BailianCarts();
            carts.setId(String.valueOf(nextId));
            carts.setCreatedAt(new Date());
            carts.setUpdatedAt(new Date());
            carts.setUserId(String.valueOf(user.getUserId()));

            //创建购物车详情对象，并放入购物车篮子中
            List<BailianCartProducts> bailianCartProducts = new ArrayList<>();
            products.setCartId(String.valueOf(nextId));
            products.setId(String.valueOf(snowFlake.nextId()));
            products.setBuyNum(buyCount);
            products.setProductId(goodsId);
            products.setCreatedAt(new Date());
            products.setUpdatedAt(new Date());
            bailianCartProducts.add(products);


            carts.setProductsList(bailianCartProducts);

            if (cartsService.addCartsByUserId(carts)){
                return ResultBuildUtil.success("添加购物车成功！");
            }else {
                return ResultBuildUtil.fail("添加购物车失败，请检查购物信息！");
            }
        }else {
            if (bailianCarts.getProductsList().stream().map(item -> item.getProductId()).collect(Collectors.toList()).contains(goodsId)){
                return ResultBuildUtil.fail("请勿重复添加商品！");
            }
            products.setId(String.valueOf(snowFlake.nextId()));
            products.setCartId(bailianCarts.getId());
            products.setBuyNum(buyCount);
            products.setProductId(goodsId);
            products.setCreatedAt(new Date());
            products.setUpdatedAt(new Date());

            if (cartsService.addCartsDetail(products)){
                return ResultBuildUtil.success("添加购物车成功！");
            }else {
                return ResultBuildUtil.fail("添加购物车失败，请检查购物信息！");
            }
        }
    }

    @ApiOperation("批量删除购物车")
    @PostMapping("/deleteCartsByIds")
    public RequestResult batchDeleteCarts(@RequestParam List<String> ids){
        if (cartsService.batchDeleteCarts(ids)) {
            return ResultBuildUtil.success("删除成功");
        } else {
            return ResultBuildUtil.fail("删除失败");
        }
    }


    @ApiOperation("修改购物车信息（修改物品数量）")
    @PostMapping("/updateCartsInfo")
    @CheckToken
    public RequestResult updateCarts(@RequestParam Integer buyCount,@RequestParam String id) {

        //判断购买数量
        if (buyCount <= 0){
            return ResultBuildUtil.fail("购买数量最小不能小于1！");
        }

        if (cartsService.updateCartsInfo(buyCount,id)){
            return ResultBuildUtil.success("修改成功");
        }
        else {
            return ResultBuildUtil.fail("购买失败");
        }


    }

}
