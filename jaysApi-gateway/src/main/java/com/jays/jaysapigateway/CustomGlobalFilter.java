package com.jays.jaysapigateway;

import com.jays.jaysapigateway.keysConfig.KeysConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class CustomGlobalFilter implements GlobalFilter, Ordered {
    @Resource
    private KeysConfig keysConfig;
    private static final List<String> IP_WHITE_LIST = Arrays.asList("127.0.0.1");

    private static final String INTERFACE_HOST = "http://localhost:8123";
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 1. 请求日志
        ServerHttpRequest request = exchange.getRequest();
        String path = INTERFACE_HOST + request.getPath().value();
        String method = request.getMethod().toString();
        log.info("请求唯一标识：" + request.getId());
        log.info("请求路径：" + path);
        log.info("请求方法：" + method);
        log.info("请求参数：" + request.getQueryParams());
        String sourceAddress = request.getLocalAddress().getHostString();
        log.info("请求来源地址：" + sourceAddress);
        log.info("请求来源地址：" + request.getRemoteAddress());
        ServerHttpResponse response = exchange.getResponse();
        // 2. 访问控制 - 黑白名单
        if (!IP_WHITE_LIST.contains(sourceAddress)) {
            response.setStatusCode(HttpStatus.FORBIDDEN);
            return response.setComplete();
        }
        // 鉴权，判断ak和sk,这个是应该放在网关的
        HttpHeaders headers = request.getHeaders();
        String accessKey = keysConfig.getAccessKey();
        String secretKey = keysConfig.getSecretKey();
        String accessKeyHeader = headers.getFirst("accessKey");
        String secretKeyHeader = headers.getFirst("secretKey");
        String accessKeyMd5 = DigestUtils.md5DigestAsHex(accessKey.getBytes());
        String secretKeyMd5 = DigestUtils.md5DigestAsHex(secretKey.getBytes());
        if(!accessKeyHeader.equals(accessKeyMd5) || !secretKeyHeader.equals(secretKeyMd5)){
            response.setStatusCode(HttpStatus.FORBIDDEN);
            return response.setComplete();
        }

        // 5. 请求转发，调用模拟接口 + 响应日志
        Mono<Void> filter = chain.filter(exchange);
        return filter;
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
