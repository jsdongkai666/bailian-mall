package com.cuning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class BailianLogisticsApplication {

    public static void main(String[] args) {
        SpringApplication.run(BailianLogisticsApplication.class, args);
    }

}
