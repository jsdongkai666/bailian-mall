package com.cuning.service.user;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cuning.bean.BailianConsignee;
import com.cuning.bean.user.User;
import com.cuning.util.RequestResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/queryPersonInfo")
    RequestResult<User> queryPersonInfo(@RequestParam("userId") String userId);

    @PostMapping("/modPersonInfo")
    RequestResult<String> modPersonInfo(@RequestBody User user);

    @PostMapping("/modPwd")
    RequestResult<Map<String,String>> modPassword(@RequestBody User user,
                                                  @RequestParam("password") String password,
                                                  @RequestParam("newPassword") String newPassword,
                                                  @RequestParam("newPasswordAgain") String newPasswordAgain);

    @GetMapping("/queryAddress")
    RequestResult<Page<BailianConsignee>> queryConsigneeAddress(@RequestParam("pageNo") Integer pageNo,
                                                                @RequestParam("pageSize") Integer pageSize,
                                                                @RequestParam("userId") String userId);

    @GetMapping("/queryDefaultAddress")
    BailianConsignee queryDefaultAddressByUserId(@RequestParam("userId") String userId);

    @PostMapping("/delAddress")
    RequestResult<Map<String,String>> delConsigneeAddress(@RequestParam("consigneeId") String consigneeId);

    @PostMapping("/addAddress")
    RequestResult<String> addConsigneeAddress(@RequestBody BailianConsignee bailianConsignee);

    @PostMapping("/modAddress")
    RequestResult<String> modConsigneeAddress(@RequestBody BailianConsignee bailianConsignee);

}
