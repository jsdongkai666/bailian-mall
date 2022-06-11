package com.cuning;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.cuning.mapper")
@EnableSwaggerBootstrapUI
@EnableSwagger2
@EnableFeignClients
public class BailianGoodsApplication {

    public static void main(String[] args) {
        SpringApplication.run(BailianGoodsApplication.class, args);
    }

}
