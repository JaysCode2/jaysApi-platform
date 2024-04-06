package com.example.apiplatform.aop;

import com.example.apiplatform.annotation.AuthCheck;
import com.example.apiplatform.common.ErrorCode;
import com.example.apiplatform.exception.BusinessException;
import com.example.apiplatform.service.UserService;
import com.jays.jaysapicommon.domain.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


@Aspect
@Component
public class AuthInterceptor {
    @Resource
    private UserService userService;

    /**
     * 权限校验
     * @param point
     * @param authCheck
     * @return
     * @throws Throwable
     */
    @Around("@annotation(authCheck)")
    public Object doInterceptor(ProceedingJoinPoint point, AuthCheck authCheck) throws Throwable{
        String mustRole = authCheck.mustRole();
        //保证request对象正确
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        //要从session中拿到当前登入的用户
        User user = userService.getLoginUser(request);
        //用户角色鉴权
        if(!user.getUserRole().equals(mustRole)){
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR,"用户角色无权限");
        }
        return point.proceed();
    }
}
