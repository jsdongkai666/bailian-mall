package com.cuning.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created On : 2022/06/13.
 * <p>
 * Author     : lixu
 * <p>
 * Description: 收货人信息实体类，命名规则：51+
 */
@Data
@ApiModel(value = "收货人信息",description = "对应数据库bailianmall中的bailian_consignee")
public class BailianConsignee {

    /**
     * 收货id
     */
    @TableId
    @ApiModelProperty("收货id")
    private String consigneeId;

    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    private String userId;

    /**
     * 收货人姓名
     */
    @ApiModelProperty("收货人姓名")
    private String consigneeName;

    /**
     * 收货地址
     */
    @ApiModelProperty("收货地址")
    private String consigneeAddress;

    /**
     * 收货电话
     */
    @ApiModelProperty("收货电话")
    private String consigneeTel;

    /**
     * 是否为默认地址
     */
    @ApiModelProperty("是否为默认地址")
    private Integer isDefault;
}
