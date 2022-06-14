package com.cuning;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableSwagger2
@EnableSwaggerBootstrapUI
@EnableEurekaClient
@EnableFeignClients
public class BailianWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(BailianWebApplication.class, args);
    }

}
