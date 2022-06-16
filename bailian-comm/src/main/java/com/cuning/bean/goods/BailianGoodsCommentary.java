package com.cuning.bean.goods;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    private String commentaryId;

    private String goodsId;

    private String userName;

    private String userImg;

    private String goodsCommentary;

    private Integer commentaryType;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date commentaryTime;

    private Integer commentaryLevel;

    private String commentaryUrl;
}
