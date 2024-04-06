package com.example.apiplatform;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.apiplatform.mapper")
//@EnableDubbo
public class ApiPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiPlatformApplication.class, args);
    }

}
