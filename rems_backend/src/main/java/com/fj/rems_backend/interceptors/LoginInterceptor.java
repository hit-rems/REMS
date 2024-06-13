package com.fj.rems_backend.interceptors;

import com.fj.rems_backend.utils.JwtUtil;
import com.fj.rems_backend.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getMethod().equalsIgnoreCase("OPTIONS")){
            // 处理 OPTIONS 请求，直接返回 200 OK
            response.setStatus(HttpServletResponse.SC_OK);
            return false; // 返回 false 表示拦截器链不再继续，结束当前请求
        }
        String token = request.getHeader("Authorization");
        //验证jwt
        try {
            Map<String, Object> clians = JwtUtil.parseToken(token);
            //存储threadlocal
            ThreadLocalUtil.set(clians);
            return true;
        } catch (Exception e) {
            response.setStatus(401);
            response.setHeader("Location", "/login");
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if(request.getMethod().equalsIgnoreCase("OPTIONS")){
            return;
        }
        //清除threadlocal
        ThreadLocalUtil.remove();
    }
}
