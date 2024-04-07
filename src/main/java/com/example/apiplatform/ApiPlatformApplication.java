package com.example.apiplatform;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@MapperScan("com.example.apiplatform.mapper")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.jays.jaysapicommon.client"})
//@EnableDubbo
public class ApiPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiPlatformApplication.class, args);
    }

}
