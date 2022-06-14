package com.cuning.controller.user;

import com.cuning.service.UserInfoFeignService;
import com.cuning.util.RequestResult;
import com.cuning.util.ResultBuildUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author dengteng
 * @title: UserInfoController
 * @projectName cuning-bailian
 * @description: 用户签到管理入口
 * @date 2022/6/14
 */
@Slf4j
@RestController
@Api(tags = "用户签到操作入口")
public class UserInfoController {

    @Autowired
    private UserInfoFeignService userInfoFeignService;


    /**
    * @Param: []
    * @return: com.cuning.util.RequestResult<java.util.Map<java.lang.String,java.lang.String>>
    * @Author: dengteng
    * @Date: 2022/6/14
    * @Description: 用户签到
    */
    @ApiOperation("用户签到")
    @ApiImplicitParam(name = "userId",value = "用户id",paramType = "query")
    @GetMapping("/checkIn")
    public RequestResult<Map<String, String>> checkIn(@RequestParam("userId")String userId){
        Map<String, String> map = userInfoFeignService.checkIn(userId);
        return ResultBuildUtil.success(map);
    }

    /**
     * @Param: []
     * @return: com.cuning.util.RequestResult<java.util.Map<java.lang.String,java.lang.String>>
     * @Author: dengteng
     * @Date: 2022/6/14
     * @Description: 用户当月签到记录
     */
    @ApiOperation("用户当月签到记")
    @ApiImplicitParam(name = "userId",value = "用户id",paramType = "query")
    @GetMapping("/userCheckList")
    public RequestResult<Map<String, Object>> userCheckList(@RequestParam("userId")String userId){
        Map<String, Object> map = userInfoFeignService.getUserCheckList(userId);
        return ResultBuildUtil.success(map);
    }

}
