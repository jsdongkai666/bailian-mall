package com.cuning.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cuning.bean.BailianCarousel;
import com.cuning.bean.BailianConsignee;
import com.cuning.util.RequestResult;

import java.util.List;
import java.util.Map;

/**
 * Created On : 2022/06/13.
 * <p>
 * Author     : lixu
 * <p>
 * Description: 收货人地址信息业务接口
 */
public interface AddressService extends IService<BailianConsignee> {

    /**
     * @author : lixu
     * @date   : 2022/06/13
     * @param  : [java.lang.String]
     * @return : java.util.List<com.cuning.bean.BailianConsignee>
     * @description : 根据用户id,查询用户的收货地址
     */
    Page<BailianConsignee> selectAddressListByPage(Integer pageNo, Integer pageSize, String userId);

    /**
     * @author : lixu
     * @date   : 2022/06/14
     * @param  : [java.lang.String]
     * @return : int
     * @description : 查询用户收货地址的个数
     */
    int searchAddressCount(String userId);

    /**
     * @author : lixu
     * @date   : 2022/06/13
     * @param  : [java.util.List<java.lang.String>]
     * @return : boolean
     * @description : 根据地址id，删除收货地址
     */
    RequestResult<Map<String,String>> delAddress(String consigneeId);


    /**
     * @author : lixu
     * @date   : 2022/06/13
     * @param  : [java.util.List<java.lang.String>]
     * @return : boolean
     * @description : 添加收货地址
     */
    boolean insertAddress(BailianConsignee bailianConsignee);

    /**
     * @author : lixu
     * @date   : 2022/06/13
     * @param  : [java.util.List<java.lang.String>]
     * @return : boolean
     * @description : 修改收货地址
     */
    boolean updateAddress(BailianConsignee bailianConsignee);


}
