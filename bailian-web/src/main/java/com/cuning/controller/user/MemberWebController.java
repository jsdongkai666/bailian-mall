package com.cuning.controller.user;

import com.cuning.bean.user.User;
import com.cuning.constant.CommonConstant;
import com.cuning.service.user.UserWebService;
import com.cuning.util.JwtUtil;
import com.cuning.util.RequestResult;
import com.cuning.util.ResultBuildUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author tengjiaozhai
 * @Description TODO
 * @createTime 2022年06月15日 11:40:00
 */
@Slf4j
@RestController
@RequestMapping("/web")
@Api(tags = "会员管理")
public class MemberWebController {

    @Autowired
    private UserWebService  userWebService;
    // 提取token解析
    //        String token = request.getHeader("token");
    //        User user = JwtUtil.parseJWT(token);

    @GetMapping("/rechargeMember")
    @ApiOperation(value = "充值会员",notes = "30 积分购买等级1 50积分等级2 100积分等级3\n" +
            "等级1 98折 等级2 95折 等级3 9折")
    public RequestResult<Map<String,Object>> rechargeMember(HttpServletRequest request,
                                        @RequestParam("vipLevel") Integer vipLevel) throws Exception {
        String token = request.getHeader("token");
        User user = JwtUtil.parseJWT(token);
        Map<String, Object> resultMap = userWebService.rechargeMember(user, vipLevel);
        // 判读返回信息 fail还是success
        if (resultMap.get(CommonConstant.UNIFY_RETURN_FAIL_MSG)!=null){
            return ResultBuildUtil.fail(resultMap);
        }
        return  ResultBuildUtil.success(resultMap);
    }

    @GetMapping("/checkMember")
    @ApiOperation("校验会员是否过期 true-为过期 false-不过期")
    public Boolean checkMember(HttpServletRequest request) throws Exception {
        String token = request.getHeader("token");
        User user = JwtUtil.parseJWT(token);
        return userWebService.checkMember(user);
    }
}
