package com.cuning.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cuning.bean.BailianCarousel;
import com.cuning.bean.BailianConsignee;
import com.cuning.constant.CommonConstant;
import com.cuning.mapper.AddressMapper;
import com.cuning.service.AddressService;
import com.cuning.util.RequestResult;
import com.cuning.util.ResultBuildUtil;
import com.cuning.util.SnowFlake;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created On : 2022/06/13.
 * <p>
 * Author     : lixu
 * <p>
 * Description: 收货人地址信息业务实现类
 */
@Service
@Slf4j
public class AddressServiceImpl extends ServiceImpl<AddressMapper, BailianConsignee> implements AddressService {

    @Autowired(required = false)
    private AddressMapper addressMapper;

    @Autowired
    private SnowFlake snowFlake;

    @Override
    public Page<BailianConsignee> selectAddressListByPage(Integer pageNo,Integer pageSize,String userId) {

        // 根据用户的id，分页查询该用户所有地址信息
        Page<BailianConsignee> page = new Page<>(pageNo,pageSize);
        QueryWrapper<BailianConsignee> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        return addressMapper.selectPage(page,queryWrapper);
    }

    @Override
    public int searchAddressCount(String userId) {
        QueryWrapper<BailianConsignee> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        return addressMapper.selectCount(queryWrapper);
    }

    @Override
    public RequestResult<Map<String,String>> delAddress(String consigneeId) {

        // 返回结果Map集合
        Map<String,String> resultMap = new HashMap<>();

        // 根据地址id，删除用户地址信息
        QueryWrapper<BailianConsignee> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("consignee_id",consigneeId);
        BailianConsignee bailianConsignee = addressMapper.selectOne(queryWrapper);
        if (bailianConsignee.getIsDefault() == 1) {
            resultMap.put("errMsg","该地址为默认地址，无法删除！");
            return ResultBuildUtil.fail(resultMap);
        }

        if (addressMapper.deleteById(consigneeId) > 0) {
            resultMap.put("code", CommonConstant.UNIFY_RETURN_SUCCESS_CODE);
            resultMap.put("msg","删除成功！");
            return ResultBuildUtil.success(resultMap);
        }
        resultMap.put("errCode", CommonConstant.UNIFY_RETURN_FAIL_CODE);
        resultMap.put("errMsg","删除失败！");
        return ResultBuildUtil.fail(resultMap);
    }

    @Override
    public boolean insertAddress(BailianConsignee bailianConsignee) {

        // 添加用户地址信息
        if (bailianConsignee.getIsDefault() == 1) {
            QueryWrapper<BailianConsignee> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id",bailianConsignee.getUserId()).eq("is_default",1);
            BailianConsignee bailianConsignee1 = addressMapper.selectOne(queryWrapper);
            if (bailianConsignee1 != null) {
                bailianConsignee1.setIsDefault(0);
                addressMapper.updateById(bailianConsignee1);
            }
        }
        bailianConsignee.setConsigneeId("51" + Long.toString(snowFlake.nextId()).substring(9,19));
        return addressMapper.insert(bailianConsignee) > 0;
    }

    @Override
    public boolean updateAddress(BailianConsignee bailianConsignee) {

        BailianConsignee bailianConsignee2 = addressMapper.selectById(bailianConsignee.getConsigneeId());
        if (bailianConsignee.getConsigneeAddress() != null) {
            bailianConsignee2.setConsigneeAddress(bailianConsignee.getConsigneeAddress());
        }
        if (bailianConsignee.getConsigneeName() != null) {
            bailianConsignee2.setConsigneeName(bailianConsignee.getConsigneeName());
        }
        if (bailianConsignee.getConsigneeTel() != null) {
            bailianConsignee2.setConsigneeTel(bailianConsignee.getConsigneeTel());
        }
        // 修改用户地址信息
        if (bailianConsignee.getIsDefault() == 1) {
            QueryWrapper<BailianConsignee> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id",bailianConsignee.getUserId()).eq("is_default",1);
            BailianConsignee bailianConsignee1 = addressMapper.selectOne(queryWrapper);
            if (bailianConsignee1 != null) {
                bailianConsignee1.setIsDefault(0);
                addressMapper.updateById(bailianConsignee1);
            }

        }

        return addressMapper.updateById(bailianConsignee2) > 0;
    }


    @Override
    public BailianConsignee selectDefaultAddressByUserId(String userId) {
        QueryWrapper<BailianConsignee> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_default",1).eq("user_id",userId);
        return addressMapper.selectOne(queryWrapper);
    }
}
