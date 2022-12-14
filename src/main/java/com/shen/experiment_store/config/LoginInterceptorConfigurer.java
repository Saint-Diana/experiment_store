package com.shen.experiment_store.config;

import com.shen.experiment_store.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * 实现了WebMvcConfigurer接口的配置类，用于配置SpringMVC中的一些组件。
 * 重载addInterceptors方法，将我们自己定义的登录拦截器注册到容器。
 * 用于过滤未登录的请求。
 *
 * @author Shen
 * @date 2022/12/14 8:39
 */
@Configuration
public class LoginInterceptorConfigurer implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 创建拦截器对象
        HandlerInterceptor interceptor = new LoginInterceptor();
        // 白名单
        List<String> patterns = new ArrayList<>();
        patterns.add("/bootstrap3/**");
        patterns.add("/css/**");
        patterns.add("/images/**");
        patterns.add("/js/**");
        patterns.add("/web/register.html");
        patterns.add("/web/login.html");
        patterns.add("/web/index.html");
        patterns.add("/web/product.html");
        patterns.add("/users/reg");
        patterns.add("/users/login");
        patterns.add("/districts/**");
        patterns.add("/products/**");
        patterns.add("/districts/**");
        patterns.add("/web/index.html");
        patterns.add("/products/**");
        patterns.add("/**");
        // 通过注册工具添加拦截器
        registry.addInterceptor(interceptor).addPathPatterns("/**").excludePathPatterns(patterns);
    }
}
