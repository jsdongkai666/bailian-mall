package com.cuning.service;
/*
 * @Created on : 2022/6/14 0014
 * <p>
 * @Author     : Administrator
 * <p>
 * @Description: SensitiveWordFeignService
 **/

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value = "bailian-search")
public interface SensitiveWordFeignService {

    @GetMapping("/testSensitiveWord")
    String testSensitiveWord(@RequestParam("searchKey") String searchKey);
}
