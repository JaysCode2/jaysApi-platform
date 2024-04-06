package com.jays.jaysapigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JaysApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(JaysApiGatewayApplication.class, args);
    }

    //测试gateway编程式路由转发
//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route("toBilibili", r -> r.path("/bilibili")
//                        .uri("https://www.bilibili.com"))
//                .route("toJaysOj", r -> r.path("/oj")
//                        .uri("http://www.bjfuacm.com"))
//                .build();
//    }
}
