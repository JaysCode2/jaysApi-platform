package com.jays.jaysapicommon.client;

import com.jays.jaysapicommon.domain.InterfaceInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "interfaceInfo-client",url = "http://localhost:8086")
public interface InterfaceInfoClient {

    @PostMapping("/api/inner/interfaceInfo/getInterfaceInfo") // 这里的路径应该是被调用服务的路径
    InterfaceInfo getInterfaceInfo(@RequestParam("url") String url, @RequestParam("method") String method);
}
