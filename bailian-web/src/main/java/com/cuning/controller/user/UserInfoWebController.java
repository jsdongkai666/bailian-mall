package com.cuning.controller.user;

import com.cuning.bean.BailianConsignee;
import com.cuning.bean.user.User;
import com.cuning.service.user.UserFeignService;
import com.cuning.util.RequestResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
     * Created On : 2022/06/14.
     * <p>
     * Author     : lixu
     * <p>
     * Description: 用户修改个人资料
     */
    @PostMapping("/modPersonInfo")
    @ApiOperation(value = "修改个人资料",notes = "用户个人资料修改，性别，生日，昵称等")
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
    public RequestResult<Map<String,String>> modPassword(@RequestBody User user,
                                                         @RequestParam("password") String password,
                                                         @RequestParam("newPassword") String newPassword,
                                                         @RequestParam("newPasswordAgain") String newPasswordAgain){
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
    RequestResult<List<BailianConsignee>> queryConsigneeAddress(@RequestParam("userId") String userId){
        return userFeignService.queryConsigneeAddress(userId);
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
    public RequestResult<Map<String,String>> delConsigneeAddress(@RequestParam("consigneeId") String consigneeId){
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
    public RequestResult<String> modConsigneeAddress(@RequestBody BailianConsignee bailianConsignee){
        return userFeignService.modConsigneeAddress(bailianConsignee);
    }
}
