package com.cuning.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cuning.bean.goods.BailianGoodsCommentary;
import com.cuning.bean.goods.BailianGoodsInfo;
import lombok.Data;

import java.util.Map;

/**
 * Created On : 2022/06/16.
 * <p>
 * Author     : lixu
 * <p>
 * Description: 商品详情实体类
 */
@Data
public class GoodsDetailsVO {

    /**
     * 商品详情
     */
    BailianGoodsInfo bailianGoodsInfo;

    /**
     * 累计销量
     */
    Map<String,Integer> salesMap;

    /**
     * 评价
     */
    Page<BailianGoodsCommentary> bailianGoodsCommentaryPage;
}
