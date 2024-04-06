package com.jays.jaysapigateway.keysConfig;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
@Data
@Configuration
@Component
//不知道为什么这个注解注入不进去
//用这个注解要导入依赖
@ConfigurationProperties("keys.client")
public class KeysConfig {
//    @Value("${keys.accessKey}")
//    private String accessKey;

//    @Value("${keys.secretKey}")
    private String secretKey;
}

