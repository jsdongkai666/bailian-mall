package com.cuning.service; /*
 * @Created on : 2022/6/14 0014
 * <p>
 * @Author     : Administrator
 * <p>
 * @Description: SensitiveWordFeignService
 **/

import com.cuning.bean.SensitiveWord;
import org.springframework.cloud.netflix.feign.FeignClient;

import java.util.List;

@FeignClient(value = "bailian-search")
public interface SensitiveWordFeignService {

    List<SensitiveWord> getSensitiveWords();
}
