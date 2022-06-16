package com.cuning.controller.user;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cuning.annotation.CheckToken;
import com.cuning.bean.BailianConsignee;
import com.cuning.bean.user.User;
import com.cuning.service.user.UserFeignService;
import com.cuning.util.JwtUtil;
import com.cuning.util.RequestResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created On : 2022/06/14.
 * <p>
 * Author     : lixu
 * <p>
 * Description: 用户前端操作入口
 */
@Slf4j
@RestController
@Api(tags = "用户前端操作入口")
public class UserInfoWebController {

    @Autowired(required = false)
    private UserFeignService userFeignService;

    /**
     * @author : lixu
     * @date   : 2022/06/16
     * @param  : [javax.servlet.http.HttpServletRequest]
     * @return : com.cuning.util.RequestResult<com.cuning.bean.user.User>
     * @description : 查看个人资料
     */
    @GetMapping("/queryPersonInfo")
    @ApiOperation(value = "查看个人资料",notes = "用户个人资料展示")
    public RequestResult<User> queryPersonInfo(HttpServletRequest request) throws Exception {
        User user = JwtUtil.parseJWT(request.getHeader("token"));
        assert user != null;
        String userId = user.getUserId();
        return userFeignService.queryPersonInfo(userId);
    }

    /**
     * Created On : 2022/06/14.
     * <p>
     * Author     : lixu
     * <p>
     * Description: 用户修改个人资料
     */
    @PostMapping("/modPersonInfo")
    @ApiOperation(value = "修改个人资料",notes = "用户个人资料修改，性别，生日，昵称等")
    @CheckToken
    public RequestResult<String> modPersonInfo(@RequestBody User user){
        return userFeignService.modPersonInfo(user);
    }

    /**
     * Created On : 2022/06/14.
     * <p>
     * Author     : lixu
     * <p>
     * Description: 用户修改密码
     */
    @PostMapping("/modPwd")
    @ApiOperation(value = "修改密码",notes = "用户修改密码，必须有旧密码，新密码和确认新密码")
    @CheckToken
    public RequestResult<Map<String,String>> modPassword(@RequestBody User user,
                                                         @ApiParam(name = "password",value = "密码")@RequestParam("password") String password,
                                                         @ApiParam(name = "newPassword",value = "新密码")@RequestParam("newPassword") String newPassword,
                                                         @ApiParam(name = "newPasswordAgain",value = "确认新密码")@RequestParam("newPasswordAgain") String newPasswordAgain){
        return userFeignService.modPassword(user, password, newPassword, newPasswordAgain);
    }

    /**
     * Created On : 2022/06/14.
     * <p>
     * Author     : lixu
     * <p>
     * Description: 收货地址查询
     */
    @GetMapping("/queryAddress")
    @ApiOperation(value = "收货人地址查询",notes = "根据用户id，查询收货人的地址")
    @CheckToken
    RequestResult<Page<BailianConsignee>> queryConsigneeAddress(HttpServletRequest request,
                                                                @ApiParam(required = true,name = "pageNo",value = "页码",defaultValue = "1")@RequestParam("pageNo") Integer pageNo,
                                                                @ApiParam(required = true,name = "pageSize",value = "条数",defaultValue = "3")@RequestParam("pageSize") Integer pageSize) throws Exception {
        User user = JwtUtil.parseJWT(request.getHeader("token"));
        assert user != null;
        String userId = user.getUserId();
        return userFeignService.queryConsigneeAddress(pageNo,pageSize,userId);
    }

    /**
     * @author : lixu
     * @date   : 2022/06/15
     * @param  : [java.lang.String]
     * @return : com.cuning.bean.BailianConsignee
     * @description : 根据用户id，查询该用户的默认地址
     */
    @GetMapping("/queryDefaultAddress")
    @ApiOperation(value = "收货人默认地址查询",notes = "根据用户id，查询用户的默认地址")
    public BailianConsignee queryDefaultAddressByUserId(HttpServletRequest request) throws Exception {
        User user = JwtUtil.parseJWT(request.getHeader("token"));
        assert user != null;
        String userId = user.getUserId();
        return  userFeignService.queryDefaultAddressByUserId(userId);
    }

    /**
     * Created On : 2022/06/14.
     * <p>
     * Author     : lixu
     * <p>
     * Description: 收货地址批量删除
     */
    @PostMapping("/delAddress")
    @ApiOperation(value = "收货人地址批量删除",notes = "根据地址id列表，批量删除收货人的地址")
    @CheckToken
    public RequestResult<Map<String,String>> delConsigneeAddress(@ApiParam(name = "consigneeId",value = "地址编号")@RequestParam("consigneeId") String consigneeId){
        return userFeignService.delConsigneeAddress(consigneeId);
    }

    /**
     * Created On : 2022/06/14.
     * <p>
     * Author     : lixu
     * <p>
     * Description: 添加收货地址
     */
    @PostMapping("/addAddress")
    @ApiOperation(value = "添加收货人地址",notes = "添加收货人的地址")
    @CheckToken
    public RequestResult<String> addConsigneeAddress(@RequestBody BailianConsignee bailianConsignee){
        return userFeignService.addConsigneeAddress(bailianConsignee);
    }

    /**
     * Created On : 2022/06/14.
     * <p>
     * Author     : lixu
     * <p>
     * Description: 修改收货地址
     */
    @PostMapping("/modAddress")
    @ApiOperation(value = "修改收货人地址",notes = "修改收货人的地址")
    @CheckToken
    public RequestResult<String> modConsigneeAddress(@RequestBody BailianConsignee bailianConsignee){
        return userFeignService.modConsigneeAddress(bailianConsignee);
    }

}
