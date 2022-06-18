package com.cuning.bean.goods;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/*
 * @Created on : 2022/6/10 0010
 * <p>
 * @Author     : Administrator
 * <p>
 * @Description: 商品分类
 **/
@Data
public class BailianGoodsCategory {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("分类主键")
    private Integer categoryId;

    @ApiModelProperty("分类等级")
    private Integer categoryLevel;

    @ApiModelProperty("父分类id")
    private Integer parentId;

    @ApiModelProperty("分类名")
    private String categoryName;

    @ApiModelProperty("排序值")
    private Integer categoryRank;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty("创建者")
    private String createUser;

    @ApiModelProperty("修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    @ApiModelProperty("修改者")
    private String updateUser;
}
