package com.home.examination.common.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author Administrator
 */
public class AccessHandlerInterceptor implements HandlerInterceptor {

    public static final Logger logger = LoggerFactory.getLogger(AccessHandlerInterceptor.class);

    /**
     * 检查标记@Security的方法,用户未登录,跳转登录页
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        List<Cookie> list = Arrays.asList(request.getCookies());
        boolean flag = list.stream().anyMatch(cookie -> cookie.getName().equals("token"));
        if(!flag) response.sendRedirect(request.getContextPath() + "/login.jsp");
        return flag;
    }

}
