package com.cuning.bean.seckill;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tengjiaozhai
 * @Description TODO
 * @createTime 2022年06月16日 10:08:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BailianSeckillUser {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("商品id")
    private String goodsId;

    @ApiModelProperty("抢购状态")
    private Boolean seckillStatus;


}
