package com.cuning.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

/**
 * Created On : 2022/06/09.
 * <p>
 * Author     : lixu
 * <p>
 * Description: 轮播图实体类
 */
@Data
@ApiModel(value = "轮播图",description = "对应数据库bailianmall中的bailianCarousel")
public class bailianCarousel {

    /**
     * id
     */
    @TableId
    private Integer carouselId;

    /**
     * 轮播图地址
     */
    private String carouselUrl;

    /**
     * 跳转地址
     */
    private String redirectUrl;

    /**
     * 轮播图排名
     */
    private Integer carouselRank;

    /**
     * 是否被删除
     */
    private Integer isDeleted;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 创建人
     */
    private Integer createUser;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 更新人
     */
    private Integer updateUser;
}
