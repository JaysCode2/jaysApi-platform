package com.jays.jaysapicommon.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "inner-user-interface-info-client", url = "http://localhost:8086")
public interface UserInterfaceClient {
    @PostMapping("/api/inner/userInterfaceInfo/invokeCount")
    Boolean invokeCount(@RequestParam("interfaceInfoId") long interfaceInfoId, @RequestParam("userId") long userId);
}
