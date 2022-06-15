package com.cuning.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/*
 * @Created on : 2022/6/9 0009
 * <p>
 * @Author     : Administrator
 * <p>
 * @Description: 商品详情
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BailianGoodsInfo implements Serializable {

    private static final long serialVersionUID = 5473302609274012318L;
    @TableId
    private String goodsId;

    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 商品简介
     */
    private String goodsIntro;
    /**
     * 商品分类id
     */
    private Integer goodsCategoryId;
    /**
     * 商品图片
     */
    private String goodsCoverImg;
    /**
     * 商品图片
     */
    private String goodsCarousel;
    /**
     * 商品价格
     */
    private Integer originalPrice;
    /**
     * 商品售卖价格
     */
    private Integer sellingPrice;
    /**
     * 库存数量
     */
    private Integer stockNum;
    /**
     * 小标签
     */
    private String tag;
    /**
     * 商品上架下架状态（0上架 1下架）
     */
    private Byte goodsSellStatus;
    /**
     * 商品详细介绍
     */
    private String goodsDetailContent;

    private Integer createUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private Integer updateUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;


}
