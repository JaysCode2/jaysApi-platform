package com.example.apiplatform.controller;

import com.example.apiplatform.annotation.AuthCheck;
import com.example.apiplatform.common.BaseResponse;
import com.example.apiplatform.common.ErrorCode;
import com.example.apiplatform.common.ResultUtils;
import com.example.apiplatform.domain.User;
import com.example.apiplatform.exception.BusinessException;
import com.example.apiplatform.service.UserService;
import com.example.apiplatform.vo.UserVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    /**
     * 用户注册
     * @param user
     * @return
     */
    @PostMapping("/register")
    public BaseResponse<UserVo> register(@RequestBody User user) {
        return ResultUtils.success(userService.registerUser(user));
    }

    /**
     * 用户登入
     * @param user
     * @return
     */
    @PostMapping("/login")
    public BaseResponse<UserVo> userLogin(@RequestBody User user, HttpServletRequest request){
        return ResultUtils.success(userService.userLogin(user,request));
    }

    /**
     * 用户退出登入
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public BaseResponse<String> logout(HttpServletRequest request){
        if (request.getSession().getAttribute("loginUser") == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR, "用户未登录");
        }
        request.getSession().removeAttribute("loginUser");
        return ResultUtils.success("退出成功");
    }

    @AuthCheck(mustRole = "admin")
    @GetMapping("/testAuthCheck")
    public BaseResponse<String> testAuCheck(){
        return ResultUtils.success("test successfully");
    }
}
