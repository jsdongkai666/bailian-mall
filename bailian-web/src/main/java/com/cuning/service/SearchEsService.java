package com.cuning.service;

import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * Created On : 2022/6/18.
 * <p>
 * Author     : lenovo
 * <p>
 * Description: SearchEsService
 */
@FeignClient(name = "bailian-search")
public interface SearchEsService {

    /**
     * @author : wangdefeng
     * @date   : 2022/6/18
     * @param  : [java.lang.String, java.lang.String]
     * @return : java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @description : 搜索商品列表
     */
    @GetMapping("/searchShopList")
    List<Map<String, Object>> searchShopList( @RequestParam(value = "searchKey",required = false) String searchKey,
                                              @RequestParam("pageIndex") Integer pageIndex,
                                             @RequestParam("pageSize") Integer pageSize);
}
