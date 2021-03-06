package com.cuning.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cuning.bean.BailianConsignee;
import com.cuning.constant.CommonConstant;
import com.cuning.service.AddressService;
import com.cuning.util.RequestResult;
import com.cuning.util.ResultBuildUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created On : 2022/06/13.
 * <p>
 * Author     : lixu
 * <p>
 * Description: 收货人地址操作入口
 */
@Slf4j
@RestController
@Api(tags = "收货人地址操作入口")
public class ConsigneeAddressController {

    @Autowired
    private AddressService addressService;

    /**
     * @author : lixu
     * @date   : 2022/06/15
     * @param  : [java.lang.Integer, java.lang.Integer, java.lang.String]
     * @return : com.cuning.util.RequestResult<com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.cuning.bean.BailianConsignee>>
     * @description : 根据用户id，查询收货人的地址
     */
    @GetMapping("/queryAddress")
    @ApiOperation(value = "用户收货地址查询",notes = "根据用户id，查询收货人的地址")
    public Page<BailianConsignee> queryConsigneeAddress(@RequestParam("pageNo") Integer pageNo,
                                                                       @RequestParam("pageSize") Integer pageSize,
                                                                       @RequestParam("userId") String userId){

        // 根据用户id，调用查询用户收货地址的个数
        int addressCount = addressService.searchAddressCount(userId);
        log.info("------ 用户：{}，收货地址的个数：{}",userId,addressCount);

        // 根据用户id，调用查询收货人信息业务接口
        Page<BailianConsignee> bailianConsigneeList = addressService.selectAddressListByPage(pageNo,pageSize,userId);
        log.info("------ 用户：{}，收货地址有：{}",userId,bailianConsigneeList);

        // 返回用户地址列表
        return bailianConsigneeList;
    }

    /**
     * @author : lixu
     * @date   : 2022/06/15
     * @param  : [java.lang.String]
     * @return : com.cuning.util.RequestResult<java.util.Map<java.lang.String,java.lang.String>>
     * @description : 根据地址id列表，批量删除收货人的地址
     */
    @PostMapping("/delAddress")
    @ApiOperation(value = "收货人地址批量删除",notes = "根据地址id列表，批量删除收货人的地址")
    public RequestResult<Map<String,String>> delConsigneeAddress(@RequestParam("consigneeId") String consigneeId) {

        // 根据地址id,调用删除收货人信息业务接口
        return addressService.delAddress(consigneeId);
    }

    /**
     * @author : lixu
     * @date   : 2022/06/15
     * @param  : [com.cuning.bean.BailianConsignee]
     * @return : com.cuning.util.RequestResult<java.lang.String>
     * @description : 添加收货人的地址
     */
    @PostMapping("/addAddress")
    @ApiOperation(value = "添加收货人地址",notes = "添加收货人的地址")
    public RequestResult<String> addConsigneeAddress(@RequestBody BailianConsignee bailianConsignee) {


        // 判断收货地址是否满20个
        if (addressService.searchAddressCount(bailianConsignee.getUserId()) >= 5) {
            return ResultBuildUtil.fail("收货地址已满，无法添加！");
        }

        // 调用添加收货人信息业务接口
        if (addressService.insertAddress(bailianConsignee)) {
            return ResultBuildUtil.success("添加成功！");
        }

        return ResultBuildUtil.fail("添加失败！");
    }

    /**
     * @author : lixu
     * @date   : 2022/06/15
     * @param  : [com.cuning.bean.BailianConsignee]
     * @return : com.cuning.util.RequestResult<java.lang.String>
     * @description : 修改收货人的地址
     */
    @PostMapping("/modAddress")
    @ApiOperation(value = "修改收货人地址",notes = "修改收货人的地址")
    public boolean modConsigneeAddress(@RequestBody BailianConsignee bailianConsignee) {

        // 调用修改收货人信息业务接口
        if (addressService.updateAddress(bailianConsignee)){
            return true;
        }

        return false;
    }


}
