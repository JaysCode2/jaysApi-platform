package com.jays.jaysapigateway.keysConfig;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
@Data
@Component
//不知道为什么这个注解注入不进去
//@ConfigurationProperties(prefix = "keys")
public class KeysConfig {
    @Value("${keys.accessKey}")
    private String accessKey;

    @Value("${keys.secretKey}")
    private String secretKey;
}

