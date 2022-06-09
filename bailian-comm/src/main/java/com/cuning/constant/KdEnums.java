package com.cuning.constant;

/**
 * @author dengteng
 * @title: KdEnums
 * @projectName cuning-bailian
 * @description: 物流状态枚举类
 * @date 2022/6/9
 */
public enum KdEnums {

    RECEIVED("1", "已揽收"), ON_THE_WAY("2", "在途中"), HAVE_BEEN_RECEIVED("3", "已签收"), PROBLEM_PIECE("4", "问题件");

    private String code;
    private String msg;

    private KdEnums(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private String getCode() {
        return code;
    }

    private String getMsg() {
        return msg;
    }

    /**
     * 通过code获取msg
     *
     * @param code
     * @return
     */
    public static String getMsgByCode(String code) {
        for (KdEnums value : KdEnums.values()) {
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
        for (KdEnums value : KdEnums.values()) {
            if (value.getMsg().equals(msg)) {
                return value.getCode();
            }
        }
        return null;
    }


}
