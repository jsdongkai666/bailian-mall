package com.cuning.service; /*
 * @Created on : 2022/6/14 0014
 * <p>
 * @Author     : Administrator
 * <p>
 * @Description: SensitiveWordFeignService
 **/

import com.cuning.bean.SensitiveWord;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value = "bailian-search")
public interface SensitiveWordFeignService {

    @PostMapping("/saveGoodsCommentary")
    List<SensitiveWord> getSensitiveWords();
}
