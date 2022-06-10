package com.cuning;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.cuning.mapper")
public class BailianGoodsApplication {

    public static void main(String[] args) {
        SpringApplication.run(BailianGoodsApplication.class, args);
    }

}
