package com.cuning.util;

/**
 * Created On : 2022/4/27.
 * <p>
 * Author : zhukang
 * <p>
 * Description: 生成redis的key的工具类
 */
public class RedisKeyUtils {
    /**
     * 用户搜索历史前缀
     */
    public static final String USER_SEARCH_HISTORY_PREFIX = "search-history";

    /**
     * 敏感词前缀
     */
    public static final String SENSITIVE_WORD_PREFIX = "sensitive-word";

    /**
     * @author : zhukang
     * @date   : 2022/4/27
     * @param  : [java.lang.String]
     * @return : java.lang.String
     * @description : 获取用户的redis中的搜索历史key
     */
    public static String getUserSearchHistoryKey(String userId){
        StringBuilder sbd = new StringBuilder();
        sbd.append(USER_SEARCH_HISTORY_PREFIX).append(":").append(userId);
        return sbd.toString();
    }

    /**
     * @author : zhukang
     * @date   : 2022/4/28
     * @param  : [java.lang.String]
     * @return : java.lang.String
     * @description : 获取敏感字key
     */
    public static String getSensitiveWordKey(String type) {
        StringBuilder sb = new StringBuilder()
                .append(type).append(".").append(SENSITIVE_WORD_PREFIX);
        return sb.toString();
    }
}
