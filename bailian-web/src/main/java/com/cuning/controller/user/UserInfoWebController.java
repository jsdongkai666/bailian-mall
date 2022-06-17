package com.cuning.controller.user;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cuning.annotation.CheckToken;
import com.cuning.bean.BailianConsignee;
import com.cuning.bean.user.User;
import com.cuning.service.user.UserFeignService;
import com.cuning.util.JwtUtil;
import com.cuning.util.RequestResult;
import com.cuning.util.ResultBuildUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
    public RequestResult queryPersonInfo(HttpServletRequest request) throws Exception {

        // 从token中获取用户信息
        User user = JwtUtil.parseJWT(request.getHeader("token"));
        assert user != null;
        String userId = user.getUserId();

        // 判断用户是否异常
        if (userId == null || userId.equals("")) {
            return ResultBuildUtil.fail("用户数据异常，请重新登录！");
        }

        User user1 = userFeignService.queryPersonInfo(userId);
        if (user1 == null) {
            return ResultBuildUtil.fail("该用户信息不存在！");
        }

        return ResultBuildUtil.success(user1);
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
    public RequestResult<String> modPersonInfo(HttpServletRequest request,
                                               @ApiParam(name = "userName",value = "昵称")@RequestParam(value = "userName",required = false) String userName,
                                               @ApiParam(name = "userSex",value = "性别")@RequestParam(value = "userSex",required = false) Integer userSex,
                                               @ApiParam(name = "userTel",value = "手机号码")@RequestParam(value = "userTel",required = false) String userTel,
                                               @ApiParam(name = "userBirth",value = "生日")@RequestParam(value = "userBirth",required = false) String userBirth,
                                               @ApiParam(name = "userMail",value = "邮箱")@RequestParam(value = "userMail",required = false) String userMail,
                                               @ApiParam(name = "userHeadImg",value = "用户头像")@RequestParam(value = "userHeadImg",required = false) String userHeadImg
                                               ) throws Exception {

        // 从token中获取用户信息
        User user = JwtUtil.parseJWT(request.getHeader("token"));
        assert user != null;
        String userId = user.getUserId();

        // 判断用户是否异常
        if (StringUtils.isEmpty(userId)) {
            return ResultBuildUtil.fail("用户数据异常，请重新登录！");
        }

        // 新建一个实体，修改用户信息
        user.setUserId(userId);
        user.setUserName(userName);
        user.setUserSex(userSex);
        user.setUserTel(userTel);
        if (!StringUtils.isEmpty(userBirth)) {
            user.setUserBirth(new SimpleDateFormat("yyyy-MM-dd").parse(userBirth));
        }
        user.setUserMail(userMail);
        user.setUserHeadImg(userHeadImg);

        // // 判断用户信息是否修改成功
        if (userFeignService.modPersonInfo(user)) {
            return ResultBuildUtil.success("修改成功！");
        }

        return ResultBuildUtil.fail("修改失败");
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
    public RequestResult<String> modPassword(HttpServletRequest request,
                                             @ApiParam(name = "password",value = "密码")@RequestParam("password") String password,
                                             @ApiParam(name = "newPassword",value = "新密码")@RequestParam("newPassword") String newPassword,
                                             @ApiParam(name = "newPasswordAgain",value = "确认新密码")@RequestParam("newPasswordAgain") String newPasswordAgain) throws Exception {
        // 从token中获取用户信息
        User user = JwtUtil.parseJWT(request.getHeader("token"));
        assert user != null;
        String userId = user.getUserId();

        // 判断用户是否异常
        if (userId == null || userId.equals("")) {
            return ResultBuildUtil.fail("用户数据异常，请重新登录！");
        }

        // 修改密码
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
    RequestResult queryConsigneeAddress(HttpServletRequest request,
                                                                @ApiParam(required = true,name = "pageNo",value = "页码",defaultValue = "1")@RequestParam("pageNo") Integer pageNo,
                                                                @ApiParam(required = true,name = "pageSize",value = "条数",defaultValue = "3")@RequestParam("pageSize") Integer pageSize) throws Exception {
        // 从token中获取用户信息
        User user = JwtUtil.parseJWT(request.getHeader("token"));
        assert user != null;
        String userId = user.getUserId();

        // 判断用户是否异常
        if (userId == null || userId.equals("")) {
            return ResultBuildUtil.fail("用户数据异常，请重新登录！");
        }

        // 判断收货地址是否存在
        Page<BailianConsignee> bailianConsigneePage = userFeignService.queryConsigneeAddress(pageNo, pageSize, userId);
        if (bailianConsigneePage == null) {
            return ResultBuildUtil.fail("暂无收货地址！");
        }

        return ResultBuildUtil.success(bailianConsigneePage);
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
    public RequestResult queryDefaultAddressByUserId(HttpServletRequest request) throws Exception {

        // 从token中获取用户信息
        User user = JwtUtil.parseJWT(request.getHeader("token"));
        assert user != null;
        String userId = user.getUserId();

        // 判断用户是否异常
        if (userId == null || userId.equals("")) {
            return ResultBuildUtil.fail("用户数据异常，请重新登录！");
        }

        // 判断该用户是否存在默认收货地址
        BailianConsignee bailianConsignee = userFeignService.queryDefaultAddressByUserId(userId);
        if (bailianConsignee == null) {
            return ResultBuildUtil.fail("该用户没有默认地址！");
        }
        return  ResultBuildUtil.success(bailianConsignee);
    }

    /**
     * Created On : 2022/06/14.
     * <p>
     * Author     : lixu
     * <p>
     * Description: 收货地址删除
     */
    @PostMapping("/delAddress")
    @ApiOperation(value = "收货地址删除",notes = "根据地址id列表，删除收货人的地址")
    @CheckToken
    public RequestResult<Map<String,String>> delConsigneeAddress(@ApiParam(name = "consigneeId",value = "地址编号")@RequestParam("consigneeId") String consigneeId){


        // 根据id删除收货地址
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
    public RequestResult<String> addConsigneeAddress(HttpServletRequest request,
                                                     @ApiParam(name = "consignessName",value = "收货人")@RequestParam(value = "consignessName") String consignessName,
                                                     @ApiParam(name = "consigneeAddress",value = "收货地址")@RequestParam(value = "consigneeAddress") String consigneeAddress,
                                                     @ApiParam(name = "consigneeTel",value = "收货手机号码")@RequestParam(value = "consigneeTel") String consigneeTel,
                                                     @ApiParam(name = "isDefault",value = "是否为默认地址 0-不默认 1-默认")@RequestParam(value = "isDefault",required = false,defaultValue = "0") Integer isDefault) throws Exception {
        // 从token中获取用户信息
        User user = JwtUtil.parseJWT(request.getHeader("token"));
        assert user != null;
        String userId = user.getUserId();

        // 判断用户是否异常
        if (StringUtils.isEmpty(userId)) {
            return ResultBuildUtil.fail("用户数据异常，请重新登录！");
        }

        // 新建一个实体，添加收货地址信息
        BailianConsignee bailianConsignee = new BailianConsignee();
        bailianConsignee.setUserId(userId);
        bailianConsignee.setConsigneeName(consignessName);
        bailianConsignee.setConsigneeAddress(consigneeAddress);
        bailianConsignee.setConsigneeTel(consigneeTel);
        bailianConsignee.setIsDefault(isDefault);

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
    public RequestResult<String> modConsigneeAddress(HttpServletRequest request,
                                                     @ApiParam(name = "consignessId",value = "收货地址id")@RequestParam(value = "consignessId") String consignessId,
                                                     @ApiParam(name = "consignessName",value = "收货人")@RequestParam(value = "consignessName") String consignessName,
                                                     @ApiParam(name = "consigneeAddress",value = "收货地址")@RequestParam(value = "consigneeAddress") String consigneeAddress,
                                                     @ApiParam(name = "consigneeTel",value = "收货手机号码")@RequestParam(value = "consigneeTel") String consigneeTel,
                                                     @ApiParam(name = "isDefault",value = "是否为默认地址 0-不默认 1-默认")@RequestParam(value = "isDefault",required = false,defaultValue = "0") Integer isDefault) throws Exception {
        // 从token中获取用户信息
        User user = JwtUtil.parseJWT(request.getHeader("token"));
        assert user != null;
        String userId = user.getUserId();

        // 判断用户是否异常
        if (StringUtils.isEmpty(userId)) {
            return ResultBuildUtil.fail("用户数据异常，请重新登录！");
        }

        // 新建一个实体，修改收货地址信息
        BailianConsignee bailianConsignee = new BailianConsignee();
        bailianConsignee.setConsigneeId(consignessId);
        bailianConsignee.setUserId(userId);
        bailianConsignee.setConsigneeName(consignessName);
        bailianConsignee.setConsigneeAddress(consigneeAddress);
        bailianConsignee.setConsigneeTel(consigneeTel);
        bailianConsignee.setIsDefault(isDefault);


        // 判断收货地址是否修改成功
        if (userFeignService.modConsigneeAddress(bailianConsignee)) {
            return ResultBuildUtil.success("修改成功！");
        }

        return ResultBuildUtil.fail("修改失败");
    }

}
