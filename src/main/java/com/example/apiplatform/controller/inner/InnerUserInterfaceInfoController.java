package com.example.apiplatform.controller.inner;


import com.example.apiplatform.service.impl.inner.InnerUserInterfaceInfoServiceImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/inner/userInterfaceInfo")
public class InnerUserInterfaceInfoController {
    @Resource
    private InnerUserInterfaceInfoServiceImpl innerUserInterfaceInfoService;
    @PostMapping("/invokeCount")
    public Boolean invokeCount(@RequestParam("interfaceInfoId") long interfaceInfoId,@RequestParam("userId") long userId){
        return innerUserInterfaceInfoService.invokeCount(interfaceInfoId, userId);
    }
}
