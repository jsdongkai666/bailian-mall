package com.cuning.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cuning.bean.user.User;
import com.cuning.constant.CommonConstant;
import com.cuning.service.UserService;
import com.cuning.util.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
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
@Api(tags = "用户签到")
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
    @ApiOperation("用户当日签到")
    @GetMapping("/checkIn")
    public Map<String, String> checkIn(String userId){

        Map<String, String> result = new HashMap<>();

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getUserId,userId);
        User user = userService.getOne(queryWrapper);

        if (user == null) {
            result.put("code", CommonConstant.UNIFY_RETURN_FAIL_CODE);
            result.put("msg","用户不存在，签到失败！");
            log.error("签到失败，用户ID：{}",userId);
            return result;
        }

        // 今日已签到
        if (userService.isChecked(user)) {
            result.put("code", CommonConstant.UNIFY_RETURN_SUCCESS_CODE);
            result.put("msg","今日已签到！");
            return result;
        }

        if (userService.todayCheck(user)) {
            result.put("code", CommonConstant.UNIFY_RETURN_SUCCESS_CODE);
            result.put("msg","签到成功！");
        }else {
            result.put("code", CommonConstant.UNIFY_RETURN_FAIL_CODE);
            result.put("msg","签到失败！");
        }

        return result;
    }


    /**
    * @Param: []
    * @return: java.util.List<java.lang.String>
    * @Author: dengteng
    * @Date: 2022/6/11
    * @Description: 获取用户当月签到的所有具体日期
    */
    @ApiOperation("获取用户当月签到的具体日期")
    @GetMapping("/getUserCheckList")
    public Map<String, Object> getUserCheckList(String userId){
        Map<String, Object> result = new HashMap<>();

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getUserId,userId);
        User user = userService.getOne(queryWrapper);

        if (user == null) {
            result.put("code", CommonConstant.UNIFY_RETURN_FAIL_CODE);
            result.put("msg","用户不存在");
            log.error("查询用户签到信息失败，用户ID：{}",userId);
            return result;
        }

        List<String> checkDateList = userService.getCheckDateList(user);

        result.put("code", CommonConstant.UNIFY_RETURN_SUCCESS_CODE);
        result.put("msg","查询成功");
        result.put("data",checkDateList);
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
