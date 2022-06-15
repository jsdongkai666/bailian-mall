package com.cuning.service.user;

import com.cuning.bean.user.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author tengjiaozhai
 * @Description TODO
 * @createTime 2022年06月11日 17:30:00
 */
@FeignClient(value = "bailian-user")
public interface UserWebService {

    @GetMapping("/user/register")
    @ApiOperation("用户注册")
    public Map<String, Object> RegisterUser(@RequestParam("userName") String userName,
                                            @RequestParam("userPassword") String userPassword);

    @GetMapping("/user/login")
    @ApiOperation("用户登录")
    public Map<String, Object> LoginUser(@RequestParam("userName") String userName,
                                         @RequestParam("userPassword") String userPassword,
                                         @RequestParam("captcha") String captcha);

    @GetMapping("/user/tel/login")
    @ApiOperation("用户手机登录")
    public Map<String, Object> telLoginUser(@RequestParam("tel") String tel,
                                            @RequestParam("captcha") String captcha);



    @ApiOperation("手机验证码")
    @GetMapping(value = "/tel/captcha")
    public String telCaptcha();

    @GetMapping("/weChatAuthCodeUrl")
    @ApiOperation("请求回调地址")
    public Map<String, Object> generateWeChatAuthCodeUrl();

    @PostMapping("/rechargeMember")
    @ApiOperation(value = "充值会员",notes = "30 积分购买等级1 50积分等级2 100积分等级3\n" +
            "等级1 98折 等级2 95折 等级3 9折")
    public Map<String,Object> rechargeMember(@RequestBody User user,
                                             @RequestParam("vipLevel") Integer vipLevel);

    @PostMapping("/checkMember")
    @ApiOperation("校验会员是否过期 true-为过期 false-不过期")
    public Boolean checkMember(@RequestBody User user);
}
