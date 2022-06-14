package com.cuning.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cuning.bean.BailianConsignee;
import com.cuning.mapper.AddressMapper;
import com.cuning.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<BailianConsignee> selectAddressList(String userId) {

        // 根据用户的id，查询该用户所有地址信息
        QueryWrapper<BailianConsignee> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        return addressMapper.selectList(queryWrapper);
    }

    @Override
    public int searchAddressCount(String userId) {
        QueryWrapper<BailianConsignee> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        return addressMapper.selectCount(queryWrapper);
    }

    @Override
    public String delAddress(String consigneeId) {
        // 根据地址id，删除用户地址信息
        QueryWrapper<BailianConsignee> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("consignee_id",consigneeId);
        BailianConsignee bailianConsignee = addressMapper.selectOne(queryWrapper);
        if (bailianConsignee.getIsDefault() == 1) {
            return "该地址为默认地址，无法删除！";
        }

        if (addressMapper.deleteById(consigneeId) > 0) {
            return "删除成功！";
        }
        return "删除失败！";
    }

    @Override
    public boolean insertAddress(BailianConsignee bailianConsignee) {

        // 添加用户地址信息
        if (bailianConsignee.getIsDefault() == 1) {
            selectAddressList(bailianConsignee.getUserId()).forEach(bailianConsignee1 -> {
                bailianConsignee1.setIsDefault(0);
                updateAddress(bailianConsignee1);
            });

        }
        return addressMapper.insert(bailianConsignee) > 0;
    }

    @Override
    public boolean updateAddress(BailianConsignee bailianConsignee) {

        // 修改用户地址信息
        if (bailianConsignee.getIsDefault() == 1) {
            selectAddressList(bailianConsignee.getUserId()).forEach(bailianConsignee1 -> {
                bailianConsignee1.setIsDefault(0);
                updateAddress(bailianConsignee1);
            });

        }

        return addressMapper.updateById(bailianConsignee) > 0;
    }
}
