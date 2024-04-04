package com.jays.jaysapiclientsdk;

import com.jays.client.JaysApiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("jays.client")
@Data
@ComponentScan
public class JaysApiClientConfig {
    private String accessKey;
    private String secretKey;
    @Bean
    public JaysApiClient jaysApiClient(){
        return new JaysApiClient(accessKey,secretKey);
    }
}
