package com.cuning;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.apache.catalina.connector.Connector;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan("com.cuning.mapper")
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class BailianUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(BailianUserApplication.class, args);
    }


}
