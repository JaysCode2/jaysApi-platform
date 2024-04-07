package com.jays.jaysapicommon.client;

import com.jays.jaysapicommon.domain.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "inner-user-info-client", url = "http://localhost:8086")
public interface UserClient {
    @PostMapping("/api/inner/user/getInvokeUser")
    User getInvokeUser(@RequestParam("accessKey") String accessKey);
}
