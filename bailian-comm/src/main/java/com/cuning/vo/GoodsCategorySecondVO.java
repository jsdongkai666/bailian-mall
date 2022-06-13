package com.cuning.vo; /*
 * @Created on : 2022/6/11 0011
 * <p>
 * @Author     : Administrator
 * <p>
 * @Description: GoodsCategorySecondVO
 **/

import lombok.Data;

import java.util.List;

@Data
public class GoodsCategorySecondVO {
    private Integer categoryId;

    private Integer categoryLevel;

    private String categoryName;

    private List<GoodsCategoryThirdVO> goodsCategoryThirdVOS;
}
