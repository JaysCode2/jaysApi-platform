package com.example.apiplatform.controller.inner;

import com.jays.jaysapicommon.client.UserClient;
import com.jays.jaysapicommon.domain.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/test")
public class testClientController {
    @Resource
    private UserClient userClient;
    @PostMapping("/testOpenfeign")
    public User getInvokeUser(@RequestParam("accessKey") String accessKey){
        return userClient.getInvokeUser(accessKey);
    }
}
