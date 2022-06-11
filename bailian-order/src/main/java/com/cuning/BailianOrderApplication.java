package com.cuning;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class BailianOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(BailianOrderApplication.class, args);
    }

}
