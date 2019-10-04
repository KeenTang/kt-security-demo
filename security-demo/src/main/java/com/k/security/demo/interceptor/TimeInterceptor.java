package com.k.security.demo.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * Desc:
 *
 * @author: keen
 * Date: 2019-10-02
 * Time: 22:44
 */
@Component
public class TimeInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        System.out.println("TimeInterceptor preHandle");
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        System.out.println("controller类型:" + handlerMethod.getBean().getClass().getName()
                + "方法名:" + handlerMethod.getMethod().getName());
        request.setAttribute("st", System.currentTimeMillis());
        return true;
    }

    /**
     * 有异常postHandle不执行
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
        long st = (long) request.getAttribute("st");
        System.out.println("TimeInterceptor postHandle,总耗时:" + (System.currentTimeMillis() - st));
    }

    /**
     * 是否有异常都会执行
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        System.out.println("TimeInterceptor afterCompletion");
    }
}
