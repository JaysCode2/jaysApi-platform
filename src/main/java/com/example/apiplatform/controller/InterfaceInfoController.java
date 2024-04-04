package com.example.apiplatform.controller;

import com.example.apiplatform.service.InterfaceInfoService;
import com.example.apiplatform.service.UserService;
import com.jays.client.JaysApiClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/interfaceInfo")
public class InterfaceInfoController {
    @Resource
    private InterfaceInfoService interfaceInfoService;

    @Resource
    private UserService userService;

    @Resource
    private JaysApiClient jaysApiClient;

    //增删改查

}
