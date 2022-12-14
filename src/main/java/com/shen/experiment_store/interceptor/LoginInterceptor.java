package com.shen.experiment_store.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义登录拦截器
 * 在preHandle方法中定义若未登录，则返回false，不允许用户访问
 * 若登录，则返回true，允许用户访问。
 *
 * @author Shen
 * @date 2022/12/14 8:41
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getSession().getAttribute("uid") == null) {
            response.sendRedirect("/web/login.html");
            return false;
        }
        return true;
    }
}
