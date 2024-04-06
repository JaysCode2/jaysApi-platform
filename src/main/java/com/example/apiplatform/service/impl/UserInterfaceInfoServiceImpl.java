package com.example.apiplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.apiplatform.common.ErrorCode;
import com.example.apiplatform.exception.BusinessException;
import com.example.apiplatform.service.UserInterfaceInfoService;
import com.example.apiplatform.mapper.UserInterfaceInfoMapper;
import com.jays.jaysapicommon.domain.UserInterfaceInfo;
import org.springframework.stereotype.Service;

/**
* @author chenjiexiang
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service实现
* @createDate 2024-04-01 21:29:05
*/
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
    implements UserInterfaceInfoService{

    /**
     * 接口调用统计实现
     * @param interfaceId
     * @param userId
     * @return
     */
    @Override
    public boolean invokeCount(long interfaceId, long userId) {
        //校验
        if (interfaceId <= 0 || userId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"数据为空");
        }
        UpdateWrapper<UserInterfaceInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("interfaceInfoId",interfaceId);
        updateWrapper.eq("userId",userId);
        //做计算
        updateWrapper.setSql("leftNum = leftNum - 1,totalNum = totalNum + 1");
        return this.update(updateWrapper);
    }
}




