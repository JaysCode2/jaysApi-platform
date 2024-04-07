package com.example.apiplatform.loginUtils;

import com.jays.jaysapicommon.common.ErrorCode;
import com.example.apiplatform.exception.BusinessException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getSession().getAttribute("loginUser")==null){
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR,"你没登入呀");
//            response.setStatus(401);
//            response.setCharacterEncoding("UTF-8");
//            response.getWriter().write("您未登入");
//            return false;
        }
        return true;
    }
}
