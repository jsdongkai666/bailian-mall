package com.cuning.controller;

import com.cuning.bean.user.User;
import com.cuning.constant.CommonConstant;
import com.cuning.service.UserService;
import com.cuning.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dengteng
 * @title: CheckInPointController
 * @projectName cuning-bailian
 * @description: TODO
 * @date 2022/6/11
 */
@Slf4j
@RestController
public class CheckInPointController {

    @Autowired
    private UserService userService;
    @Autowired
    private RedisUtils redisUtils;

    /**
    * @Param: [java.lang.String]
    * @return: java.util.Map<java.lang.String,java.lang.String>
    * @Author: dengteng
    * @Date: 2022/6/11
    * @Description: 用户当日签到
    */
    @GetMapping("/checkIn")
    public Map<String, String> checkIn(String userId){

        Map<String, String> result = new HashMap<>();

        // 今日已签到
        if (userService.isChecked(userId)) {
            result.put("code", CommonConstant.UNIFY_RETURN_SUCCESS_CODE);
            result.put("msg","今日已签到！");
            return result;
        }

        User user = userService.todayCheck(userId);

        if (user == null) {
            result.put("code", CommonConstant.UNIFY_RETURN_FAIL_CODE);
            result.put("msg","签到失败！");
            log.error("签到失败，用户ID：{}",userId);

            return result;
        }

        result.put("code", CommonConstant.UNIFY_RETURN_SUCCESS_CODE);
        result.put("msg","签到成功！");

        return result;
    }

    /**
    * @Param: []
    * @return: void
    * @Author: dengteng
    * @Date: 2022/6/11
    * @Description: 定时设置所有用户的签到状态为0
    */
    @Scheduled(cron = "0 0 0 * * ? ")
    //@GetMapping("/scheduled")
    public void setCheckStatus(){
        log.info("定时任务修改签到状态开始");
        boolean flag = false;
        if (redisUtils.lock("checkSheduled",1,60)) {
            flag = userService.cronSetCheckStatus();
        }
        log.info("定时任务修改签到状态结束，结果：{}",flag);
    }

}
