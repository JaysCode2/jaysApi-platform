package com.example.apiplatform.aop;

import com.example.apiplatform.service.UserService;
import com.jays.jaysapicommon.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

// aop实现用户请求日志打印
@Component
@Aspect
@Slf4j
public class LogInterceptor {
    @Resource
    private UserService userService;
    /**
     * 执行拦截
     */
    @Around("execution(* com.example.apiplatform.controller.*.*(..)) " +
            "&& !(execution(* com.example.apiplatform.controller.UserController.register(..))) " +
            "&& !(execution(* com.example.apiplatform.controller.UserController.userLogin(..)))")
    public Object doInterceptor(ProceedingJoinPoint point) throws Throwable {
        // 计时
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        // 获取请求路径
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest httpServletRequest = ((ServletRequestAttributes) requestAttributes).getRequest();
        //拿到user对象信息
        User user = userService.getLoginUser(httpServletRequest);
        String url = httpServletRequest.getRequestURI();
        long userId = user.getId();
        String userName = user.getUserName();
        // 获取请求参数
        Object[] args = point.getArgs();
        String reqParam = "[" + StringUtils.join(args, ", ") + "]";
        // 输出请求日志
        log.info("request start，id: {},userName: {},path: {}, ip: {}, params: {}", userId, userName, url,
                httpServletRequest.getRemoteHost(), reqParam);
        // 执行原方法
        Object result = point.proceed();
        // 输出响应日志
        stopWatch.stop();
        long totalTimeMillis = stopWatch.getTotalTimeMillis();
        log.info("request end, id: {},userName: {}, cost: {}ms", userId, userName, totalTimeMillis);
        return result;
    }
}
