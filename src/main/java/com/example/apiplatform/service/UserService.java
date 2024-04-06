package com.example.apiplatform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.apiplatform.vo.UserVo;
import com.jays.jaysapicommon.domain.User;

import javax.servlet.http.HttpServletRequest;

/**
* @author chenjiexiang
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2024-02-07 21:09:22
*/

public interface UserService extends IService<User> {
    //用户注册
    UserVo registerUser(User user);
    //用户登入
    UserVo userLogin(User user, HttpServletRequest request);
    // 获取当前用户
    User getLoginUser(HttpServletRequest request);
}
