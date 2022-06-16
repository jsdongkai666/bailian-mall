package com.cuning.constant;

/**
 * @author dengteng
 * @title: CouponEnums
 * @projectName cuning-bailian
 * @description: TODO
 * @date 2022/6/15
 */
public enum CouponEnums {
    EXPIRED("1","已过期"),UNUSED("2","未使用"),USED("3","已使用");


    private String code;
    private String msg;

    private CouponEnums(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    /**
     * 通过code获取msg
     *
     * @param code
     * @return
     */
    public static String getMsgByCode(String code) {
        for (CouponEnums value : CouponEnums.values()) {
            if (value.getCode().equals(code)) {
                return value.getMsg();
            }
        }
        return null;
    }

    /**
     * 通过msg获取code
     *
     * @param msg
     * @return
     */
    public static String getCodeByMsg(String msg) {
        for (CouponEnums value : CouponEnums.values()) {
            if (value.getMsg().equals(msg)) {
                return value.getCode();
            }
        }
        return null;
    }


}
