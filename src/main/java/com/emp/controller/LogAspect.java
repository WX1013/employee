package com.emp.controller;

import com.emp.pojo.AdminEntity;
import com.emp.pojo.LogEntity;
import com.emp.pojo.UserEntity;
import com.emp.service.LogService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Map;

/**
 * @Description:
 * @Author: Joker
 * @Date: 2019/2/27 16:42
 */
@Component
@Aspect
public class LogAspect {

    private final static Log log = LogFactory.getLog(LogAspect.class);

    @Autowired
    private LogService logService;

    @Pointcut("execution(* com.emp.controller..*(..))")
    public void aspect(){
    }


    //配置环绕通知,使用在方法aspect()上注册的切入点
    @Around("aspect()")
    public Object around(ProceedingJoinPoint  pjp){
        LogEntity log = new LogEntity();
        // 获取请求对象
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // 获取当前登录用户
        UserEntity user = (UserEntity) request.getSession().getAttribute("user_info");
        AdminEntity admin = (AdminEntity) request.getSession().getAttribute("admin_info");
        if(admin == null && user != null){
            log.setUsername(user.getUsername());
        }
        if(admin != null && user == null){
            log.setUsername(admin.getUsername());
        }
        // 获取访问ip
        String ipAddress = request.getHeader("x-forwarded-for");
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if(ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")){
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress= inet.getHostAddress();
            }
        }
        log.setIp(ipAddress);
        log.setAddTime(new Date());
        // 获取访问的模块
        Object target = pjp.getTarget();
        String module = target.getClass().getSimpleName();
        log.setModule(module);
        // 获取访问的方法
        String methodName = pjp.getSignature().getName();
        log.setMethod(methodName);
        if(log.getUsername() != null && !methodName.contains("getLogList")){
            logService.save(log);
        }
        // 拦截的方法参数
        Object object = null;
        try {
            Object[] args = pjp.getArgs();
            object = pjp.proceed(args);
        }catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        return object;
    }

}

