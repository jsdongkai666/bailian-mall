package com.cuning.config;

import com.alibaba.fastjson.JSON;
import com.cuning.bean.SensitiveWord;
import com.cuning.service.SensitiveWordService;
import com.cuning.util.RedisKeyUtils;
import com.cuning.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created On : 2022/5/17.
 * <p>
 * Author : zhukang
 * <p>
 * Description: 敏感词缓存
 */
@Component
public class SensitiveWordCache {
    // 敏感词集合
    public static Map sensitiveWordMap;

    // 敏感字在redis过期时间
    private static final int EXPIRE_TIME = 3600;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired(required = false)
    private SensitiveWordService sensitiveWordService;

    // 应用启动后自动执行此方法
    @PostConstruct
    public void initSensitiveWord(){
        System.out.println("------- 系统启动，从redis中读取敏感字，sensitiveWordMap放入内存 -------");

        // 从redis获取敏感词，从缓存中获取，减少数据库交互或者文件解析，提高效率
        Object sensitiveWordObj = redisUtils.get(RedisKeyUtils.getSensitiveWordKey(SensitiveWordCache.class.getName()));

        // redis没有获取到，从数据库查询敏感词放入redis缓存
        if (sensitiveWordObj == null) {
            // 从数据库查询敏感词，转换为set集合
            Set<String> set = sensitiveWordService.getSensitiveWords().stream().map(SensitiveWord::getWord).collect(Collectors.toSet());

            // 将敏感词库加入到HashMap中，确定有穷自动机DFA
            addSensitiveWordToHashMap(set);

            // 存入redis，可以设置过期（另：可以加个定时，定时扫描redis中敏感词库是否过期，如果过期，重新刷新缓存，防止敏感字需要更新）
            redisUtils.set(RedisKeyUtils.getSensitiveWordKey(SensitiveWordCache.class.getName()), JSON.toJSONString(sensitiveWordMap), EXPIRE_TIME);

            return;
        }

        // 转换为敏感词库Map
        sensitiveWordMap = JSON.parseObject(sensitiveWordObj.toString(), Map.class);
    }

    /**
     * @author : zhukang
     * @date   : 2022/4/27
     * @param  : [java.util.Set<java.lang.String>]
     * @return : java.util.Map
     * @description : 将HashSet中的敏感词,存入HashMap中
     */
    private void addSensitiveWordToHashMap(Set<String> wordSet) {
        // 初始化敏感词容器，减少扩容操作
        sensitiveWordMap = new HashMap(wordSet.size());
        for (String word : wordSet) {
            Map nowMap = sensitiveWordMap;
            for (int i = 0; i < word.length(); i++) {
                // 转换成char型
                char keyChar = word.charAt(i);
                // 获取
                Object tempMap = nowMap.get(keyChar);
                // 如果存在该key，直接赋值
                if (tempMap != null) {
                    // 一个一个放进Map中
                    nowMap = (Map) tempMap;
                }
                // 不存在则，则构建一个map，同时将isEnd设置为0，因为他不是最后一个
                else {
                    // 设置标志位，不是最后一个
                    Map<String, String> newMap = new HashMap<String, String>();
                    // 没有这个key，就把(isEnd，0) 放在Map中
                    newMap.put("isEnd", "0");
                    // 添加到集合
                    nowMap.put(keyChar, newMap);
                    nowMap = newMap;
                }
                // 最后一个
                if (i == word.length() - 1) {
                    nowMap.put("isEnd", "1");
                }
            }
        }
    }

    /**
     * @author : zhukang
     * @date   : 2022/4/27
     * @param  : []
     * @return : java.util.Set<java.lang.String>
     * @description : 读取敏感词库文件,存入HashMap中
     */
    private Set<String> readSensitiveWordFile() throws IOException {
        // 敏感词集合
        Set<String> wordSet = null;
        //敏感词库
        try (
                // 获取输入流，读取resources目录下的static目录中的敏感词文件（一个敏感词一行）
                InputStream inputStream = new ClassPathResource("static/sensitiveword.txt").getInputStream();

                // 读取文件输入流
                InputStreamReader read = new InputStreamReader(inputStream, "UTF-8");

                // 高效读取
                BufferedReader br = new BufferedReader(read);
        ) {
            // 创建set集合，存储读取的敏感字
            wordSet = new HashSet<>();

            // 读取文件，将文件内容放入到set中
            String txt = null;
            while ((txt = br.readLine()) != null) {
                wordSet.add(txt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 返回敏感字集合
        return wordSet;
    }
}

