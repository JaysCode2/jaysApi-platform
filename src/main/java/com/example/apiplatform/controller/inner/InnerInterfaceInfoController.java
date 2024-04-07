package com.example.apiplatform.controller.inner;


import com.example.apiplatform.service.impl.inner.InnerInterfaceInfoServiceImpl;
import com.jays.jaysapicommon.domain.InterfaceInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/inner/interfaceInfo")
public class InnerInterfaceInfoController {
    @Resource
    private InnerInterfaceInfoServiceImpl innerInterfaceInfoService;
    @PostMapping("/getInterfaceInfo")
    public InterfaceInfo getInterfaceInfo(@RequestParam("url") String url, @RequestParam("method") String method){
        return innerInterfaceInfoService.getInterfaceInfo(url,method);
    }
}
