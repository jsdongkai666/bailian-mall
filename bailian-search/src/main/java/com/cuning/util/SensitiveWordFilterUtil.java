package com.cuning.util;


import com.cuning.config.SensitiveWordCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created On : 2022/5/17.
 * <p>
 * Author : zhukang
 * <p>
 * Description: SensitiveWordFilterUtil
 */
@Component
public class SensitiveWordFilterUtil {
    // 最小匹配规则
    public static final int MIN_MATCH_TYPE = 1;

    // 最大匹配规则
    public static final int MAX_MATCH_TYPE = 2;

    @Autowired
    private SensitiveWordCache sensitiveWordCache;

    /**
     * @param : [java.lang.String, int, int]
     * @return : int
     * @author : zhukang
     * @date : 2022/4/28
     * @description : 检查文字中是否包含敏感字符，检查规则如下：如果存在，则返回敏感词字符的长度，不存在返回0
     */
    public int checkSensitiveWord(String txt, int beginIndex, int matchType) {
        // 敏感词结束标识位：用于敏感词只有1位的情况
        boolean flag = false;
        // 匹配标识数默认为0
        int matchFlag = 0;
        //判断敏感词redis是否存在
        sensitiveWordCache.judgeSensitiveWord();
        // 从内存中，获取敏感词库
        Map nowMap = SensitiveWordCache.sensitiveWordMap;
        for (int i = beginIndex; i < txt.length(); i++) {
            char word = txt.charAt(i);
            // 获取指定key
            nowMap = (Map) nowMap.get(word);

            //不存在，直接返回
            if (nowMap == null) {
                break;
            }
            // 输入的字(排列组合的匹配)出现在敏感词库中，判断是否为最后一个
            // 找到相应key，匹配标识+1
            matchFlag++;
            // 如果为最后一个匹配规则,结束循环，返回匹配标识数
            if ("1".equals(nowMap.get("isEnd"))) {
                // 结束标志位为true
                flag = true;
                // 最小规则，直接返回,最大规则还需继续查找
                if (SensitiveWordFilterUtil.MIN_MATCH_TYPE == matchType) {
                    break;
                }
            }
        }

        // 长度必须大于等于1，为词
        if (matchFlag < 2 || !flag) {
            matchFlag = 0;
        }

        return matchFlag;
    }


    /**
     * @param : [java.lang.String]
     * @return : boolean
     * @author : zhukang
     * @date : 2022/4/28
     * @description : 是否包含敏感词(默认按最小匹配规则来，只要有敏感词就ok)
     * 如果敏感词库为：
     * 坏
     * 坏蛋
     * 坏蛋人
     * 初始化之后为：{坏={isEnd=1, 蛋={人={isEnd=1}, isEnd=1}}}
     * 测试的文字为：我是一名中国人。
     * 1、按最小规则匹配，  匹配 坏 的时候，就为最后一个了 直接break。
     * 2、按最大规则匹配，  匹配 坏 的时候，就为最后一个，继续匹配 蛋，人。
     */
    public boolean isContainSensitiveWord(String txt) {
        boolean flag = false;
        for (int i = 0; i < txt.length(); i++) {
            int matchFlag = this.checkSensitiveWord(txt, i, MIN_MATCH_TYPE);
            if (matchFlag > 0) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * @param : [java.lang.String, int]
     * @return : boolean
     * @author : zhukang
     * @date : 2022/4/28
     * @description : 是否包含敏感词(默认按指定匹配规则来，只要有敏感词就ok)
     */
    public boolean isContainSensitiveWord(String txt, int matchType) {
        boolean flag = false;
        for (int i = 0; i < txt.length(); i++) {
            int matchFlag = this.checkSensitiveWord(txt, i, matchType);
            if (matchFlag > 0) {
                flag = true;
            }
        }
        return flag;
    }


    /**
     * @param : [java.lang.String, int]
     * @return : java.util.Set<java.lang.String>
     * @author : zhukang
     * @date : 2022/4/28
     * @description : 获取文字中的敏感词
     */
    public Set<String> getSensitiveWord(String txt, int matchType) {
        Set<String> sensitiveWordList = new HashSet<>();
        for (int i = 0; i < txt.length(); i++) {
            // 判断是否包含敏感字符
            int length = checkSensitiveWord(txt, i, matchType);
            // 存在,加入list中
            if (length > 0) {
                sensitiveWordList.add(txt.substring(i, i + length));
                // 减1的原因，是因为for会自增
                i = i + length - 1;
            }
        }
        return sensitiveWordList;
    }

    /**
     * @param : [java.lang.String, int, java.lang.String]
     * @return : java.lang.String
     * @author : zhukang
     * @date : 2022/4/28
     * @description : 替换敏感字字符
     */
    public String replaceSensitiveWord(String txt, int matchType, String replaceChar) {
        String resultTxt = txt;
        // 获取所有的敏感词
        Set<String> set = getSensitiveWord(txt, matchType);
        Iterator<String> iterator = set.iterator();
        String word = null;
        String replaceString = null;
        while (iterator.hasNext()) {
            word = iterator.next();
            replaceString = getReplaceChars(replaceChar, word.length());
            resultTxt = resultTxt.replaceAll(word, replaceString);
        }
        return resultTxt;
    }

    /**
     * @param : [java.lang.String, int]
     * @return : java.lang.String
     * @author : zhukang
     * @date : 2022/4/28
     * @description : 获取替换字符串
     */
    private String getReplaceChars(String replaceChar, int length) {
        String resultReplace = replaceChar;
        for (int i = 1; i < length; i++) {
            resultReplace += replaceChar;
        }
        return resultReplace;
    }

}
