package com.cuning.service.user;

import com.cuning.bean.BailianConsignee;
import com.cuning.bean.user.User;
import com.cuning.util.RequestResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * Created On : 2022/06/14.
 * <p>
 * Author     : lixu
 * <p>
 * Description: 用户远程调用接口
 */
@FeignClient(name = "bailian-user")
public interface UserFeignService {

    @PostMapping("/modPersonInfo")
    @ApiOperation(value = "修改个人资料",notes = "用户个人资料修改，性别，生日，昵称等")
    RequestResult<String> modPersonInfo(@RequestBody User user);

    @PostMapping("/modPwd")
    @ApiOperation(value = "修改密码",notes = "用户修改密码，必须有旧密码，新密码和确认新密码")
    RequestResult<Map<String,String>> modPassword(@RequestBody User user,
                                                  @RequestParam("password") String password,
                                                  @RequestParam("newPassword") String newPassword,
                                                  @RequestParam("newPasswordAgain") String newPasswordAgain);

    @GetMapping("/queryAddress")
    @ApiOperation(value = "收货人地址查询",notes = "根据用户id，查询收货人的地址")
    RequestResult<List<BailianConsignee>> queryConsigneeAddress(@RequestParam("userId") String userId);

    @PostMapping("/delAddress")
    @ApiOperation(value = "收货人地址批量删除",notes = "根据地址id列表，批量删除收货人的地址")
    RequestResult<Map<String,String>> delConsigneeAddress(@RequestParam("consigneeId") String consigneeId);

    @PostMapping("/addAddress")
    @ApiOperation(value = "添加收货人地址",notes = "添加收货人的地址")
    RequestResult<String> addConsigneeAddress(@RequestBody BailianConsignee bailianConsignee);

    @PostMapping("/modAddress")
    @ApiOperation(value = "修改收货人地址",notes = "修改收货人的地址")
    RequestResult<String> modConsigneeAddress(@RequestBody BailianConsignee bailianConsignee);
}
