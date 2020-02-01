package com.home.examination.common.interceptor;

import com.home.examination.common.runner.MyStartupRunner;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class WebAccessHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        request.getSession().setAttribute("sys_dict", MyStartupRunner.map);
        if(request.getCookies() == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return false;
        }
        List<Cookie> list = Arrays.asList(request.getCookies());
        boolean flag = list.stream().anyMatch(cookie -> cookie.getName().equals("token"));
        if(!flag){
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return false;
        }
        return true;
    }

}
