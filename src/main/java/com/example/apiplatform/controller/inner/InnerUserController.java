package com.example.apiplatform.controller.inner;

import com.example.apiplatform.service.impl.inner.InnerUserServiceImpl;
import com.jays.jaysapicommon.domain.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/inner/user")
public class InnerUserController {
    @Resource
    private InnerUserServiceImpl innerUserService;
    @PostMapping("/getInvokeUser")
    public User getInvokeUser(@RequestParam("accessKey") String accessKey){
        return innerUserService.getInvokeUser(accessKey);
    }
}
