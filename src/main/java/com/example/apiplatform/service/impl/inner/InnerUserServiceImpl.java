package com.example.apiplatform.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jays.jaysapicommon.common.ErrorCode;
import com.example.apiplatform.exception.BusinessException;
import com.example.apiplatform.mapper.UserMapper;
import com.jays.jaysapicommon.domain.User;
import com.jays.jaysapicommon.service.InnerUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 内部用户服务实现类
 */
@Service
public class InnerUserServiceImpl implements InnerUserService {

    @Resource
    private UserMapper userMapper;


    @Override
    public User getInvokeUser(String accessKey) {
        if (StringUtils.isAnyBlank(accessKey)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("accessKey", accessKey);
        return userMapper.selectOne(queryWrapper);
    }
}
