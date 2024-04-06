package com.example.apiplatform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jays.jaysapicommon.domain.UserInterfaceInfo;

/**
* @author chenjiexiang
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service
* @createDate 2024-04-01 21:29:05
*/
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {
    /**
     * 接口调用统计
     * @param interfaceId
     * @param userId
     * @return
     */
    boolean invokeCount(long interfaceId,long userId);
}
