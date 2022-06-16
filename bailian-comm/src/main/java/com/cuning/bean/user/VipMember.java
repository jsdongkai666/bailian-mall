package com.cuning.bean.user;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author tengjiaozhai
 * @Description TODO
 * @createTime 2022年06月14日 19:36:00
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
public enum VipMember {

    // 白银会员
    ONE(1,300,0.98),
    // 黄金会员
    TWO(2,500,0.95),
    // 钻石会员
    THREE(3,1000,0.9);

    // 会员等级
    private Integer vipLevel;
    // 购买会员所需积分
    private Integer vipPoint;
    // 会员折扣
    private Double vipDiscount;

    public Integer getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(Integer vipLevel) {
        this.vipLevel = vipLevel;
    }

    public Integer getVipPoint() {
        return vipPoint;
    }

    public void setVipPoint(Integer vipPoint) {
        this.vipPoint = vipPoint;
    }

    public Double getVipDiscount() {
        return vipDiscount;
    }

    public void setVipDiscount(Double vipDiscount) {
        this.vipDiscount = vipDiscount;
    }

    /**
     * @author smj
     * @description 通过会员等级获取枚举对象
     * @since 2020-7-17 14:54
     **/
    public static VipMember fromLevel(Integer level) {
        for (VipMember vipMember : VipMember.values()) {
            if (level.equals(vipMember.getVipLevel())) {
                return vipMember;
            }
        }
        return null;
    }

    /**
     * @author smj
     * @description 通过名字获取枚举对象
     * @since 2020-7-17 15:13
     **/
    public static VipMember fromName(String name) {
        return VipMember.valueOf(name);
    }


}
