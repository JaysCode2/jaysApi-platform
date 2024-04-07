package com.jays.jaysapigateway;


import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

//@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.jays.jaysapicommon.client"})
//处理数据源报错问题
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
//@SpringBootApplication(exclude = {
//        DataSourceAutoConfiguration.class,
//        DataSourceTransactionManagerAutoConfiguration.class,
//        HibernateJpaAutoConfiguration.class})
//@EnableDubbo
//@Service
public class JaysApiGatewayApplication {
//    @DubboReference
//    private DemoService demoService;

    public static void main(String[] args) {
        SpringApplication.run(JaysApiGatewayApplication.class, args);
    }
//public static void main(String[] args) {
//
//    ConfigurableApplicationContext context = SpringApplication.run(JaysApiGatewayApplication.class, args);
//    JaysApiGatewayApplication application = context.getBean(JaysApiGatewayApplication.class);
//    String result = application.doSayHello("world");
//    String result2 = application.doSayHello2("world");
//    System.out.println("result: " + result);
//    System.out.println("result: " + result2);
//}
//
//    public String doSayHello(String name) {
//        return demoService.sayHello(name);
//    }
//
//    public String doSayHello2(String name) {
//        return demoService.sayHello2(name);
//    }

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
