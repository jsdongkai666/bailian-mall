package com.cuning.bean.goods;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

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
    private Integer goodsId;

    private String userId;

    private String goodsCommentary;
}
