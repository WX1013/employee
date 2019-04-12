package com.emp.interceptor;

import com.emp.pojo.AdminEntity;
import com.emp.pojo.UserEntity;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: Joker
 * @Date: 2019/2/27 16:42
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        // 获取请求路径
        String requestURI = request.getRequestURI();
        // 如果是注册或者登陆就放行
        if(requestURI.contains("/login")  || requestURI.contains("/regist") || requestURI.contains("/forgetPass")){
            return true;
        }
        // 获取域中信息
        AdminEntity admin_info = (AdminEntity) request.getSession().getAttribute("admin_info");
        UserEntity user_info = (UserEntity) request.getSession().getAttribute("user_info");
        // 如果未登录 跳转到登录页面
        if(admin_info == null && user_info == null){
            request.getRequestDispatcher("/login.html").forward(request,response);
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView mv) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {

    }
}
