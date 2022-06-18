package com.cuning.bean.goods;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/*
 * @Created on : 2022/6/9 0009
 * <p>
 * @Author     : Administrator
 * <p>
 * @Description: 商品详情，命名规则：10+
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BailianGoodsInfo implements Serializable {

    private static final long serialVersionUID = 5473302609274012318L;
    @TableId
    @ApiModelProperty("商品编号")
    private String goodsId;


    /**
     * 商品名称
     */
    @ApiModelProperty("商品名称")
    private String goodsName;
    /**
     * 商品简介
     */
    @ApiModelProperty("商品简介")
    private String goodsIntro;
    /**
     * 商品分类id
     */
    @ApiModelProperty("商品分类id")
    private Integer goodsCategoryId;
    /**
     * 商品图片
     */
    @ApiModelProperty("商品图片")
    private String goodsCoverImg;
    /**
     * 商品轮播图
     */
    @ApiModelProperty("商品轮播图")
    private String goodsCarousel;
    /**
     * 商品价格
     */
    @ApiModelProperty("商品价格")
    private Integer originalPrice;
    /**
     * 商品售卖价格
     */
    @ApiModelProperty("商品售卖价格")
    private Integer sellingPrice;
    /**
     * 库存数量
     */
    @ApiModelProperty("库存数量")
    private Integer stockNum;
    /**
     * 小标签
     */
    @ApiModelProperty("小标签")
    private String tag;
    /**
     * 商品上架下架状态（0上架 1下架）
     */
    @ApiModelProperty("商品上架下架状态")
    private Byte goodsSellStatus;
    /**
     * 商品详细介绍
     */
    @ApiModelProperty("商品详细介绍")
    private String goodsDetailContent;

    @ApiModelProperty("添加者主键")
    private String createUser;

    @ApiModelProperty("添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty("修改者主键")
    private String updateUser;

    @ApiModelProperty("修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

}
