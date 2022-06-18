package com.cuning.bean.goods;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/*
 * @Created on : 2022/6/13 0013
 * <p>
 * @Author     : Administrator
 * <p>
 * @Description: BailianGoodsCommentary
 **/
@Data
public class BailianGoodsCommentary {
    @TableId
    @ApiModelProperty("评论主键")
    private String commentaryId;

    @ApiModelProperty("订单表主键")
    private String  orderId;

    @ApiModelProperty("商品编号")
    private String goodsId;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("用户头像")
    private String userImg;

    @ApiModelProperty("商品评论")
    private String goodsCommentary;

    /**
     * 商品评论类型0：全部 1：差评 2：中评 3：好评
     */
    @ApiModelProperty("评论类型")
    private Integer commentaryType;

    @ApiModelProperty("评论时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date commentaryTime;

    @ApiModelProperty("评论星级")
    private Integer commentaryLevel;

    @ApiModelProperty("评论图片")
    private String commentaryUrl;
}
