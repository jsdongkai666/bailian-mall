package com.cuning.bean.seckill;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigInteger;
import java.util.Date;

/**
 * @author tengjiaozhai
 * @Description TODO
 * @createTime 2022年06月16日 10:02:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

public class BailianSeckill {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("商品id")
    private Long goodsId;

    @ApiModelProperty("商品价格")
    private Double goodsPrice;

    @ApiModelProperty("商品库存")
    private Integer goodsNum;

    @ApiModelProperty("商品创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date goodsCreateTime;

    @ApiModelProperty("商品更新时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date goodsUpdateTime;

    @ApiModelProperty("抢购开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date goodsStartTime;

    @ApiModelProperty("抢购结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date goodsEndTime;

    @ApiModelProperty("分类id")
    private Long categoryId;
}
