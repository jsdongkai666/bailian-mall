package com.cuning.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Created On : 2022/06/09.
 * <p>
 * Author     : lixu
 * <p>
 * Description: 轮播图实体类 ，命名规则：20+
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "轮播图",description = "对应数据库bailianmall中的bailian_carousel")
public class BailianCarousel implements Serializable {

    private static final long serialVersionUID = -7771537176974371148L;
    /**
     * id
     */
    @TableId
    @ApiModelProperty("轮播图编号")
    private String carouselId;

    /**
     * 轮播图地址
     */
    @ApiModelProperty("轮播图地址")
    private String carouselUrl;

    /**
     * 跳转地址
     */
    @ApiModelProperty("跳转地址")
    private String redirectUrl;

    /**
     * 轮播图排名
     */
    @ApiModelProperty("轮播图排名")
    private Integer carouselRank;

    /**
     * 是否被删除
     */
    @ApiModelProperty("是否被删除")
    private Integer isDeleted;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    private String createUser;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 更新人
     */
    @ApiModelProperty("更新人")
    private String updateUser;
}
