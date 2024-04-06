package com.example.apiplatform.service.impl.inner;

import com.example.apiplatform.service.UserInterfaceInfoService;
import com.jays.jaysapicommon.service.InnerUserInterfaceInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 内部用户接口信息服务实现类
 */
@Service
public class InnerUserInterfaceInfoServiceImpl implements InnerUserInterfaceInfoService {

    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {
        return userInterfaceInfoService.invokeCount(interfaceInfoId, userId);
    }
}
