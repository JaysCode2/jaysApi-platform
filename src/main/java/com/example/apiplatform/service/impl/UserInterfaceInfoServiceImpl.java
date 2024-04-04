package com.example.apiplatform.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.apiplatform.domain.UserInterfaceInfo;
import com.example.apiplatform.service.UserInterfaceInfoService;
import com.example.apiplatform.mapper.UserInterfaceInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author chenjiexiang
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service实现
* @createDate 2024-04-01 21:29:05
*/
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
    implements UserInterfaceInfoService{

}




