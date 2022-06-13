package com.cuning.bean.goods;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    private Integer categoryId;

    private Integer categoryLevel;

    private Integer parentId;

    private String categoryName;

    private Integer categoryRank;

    private Integer isDeleted;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private Integer createUser;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    private Integer updateUser;
}
