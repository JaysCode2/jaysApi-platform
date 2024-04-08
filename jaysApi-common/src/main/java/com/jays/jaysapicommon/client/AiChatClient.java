package com.jays.jaysapicommon.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

//讯飞星火大模型
@FeignClient(name = "ChatAi-client",url = "http://localhost:8099")
public interface AiChatClient {
    @GetMapping("/test/sendQuestion")
    String sendQuestion(@RequestParam("question") String question);
}
