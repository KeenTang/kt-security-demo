package com.k.security.browser.controller;

import com.k.security.core.model.ResponseResult;
import com.k.security.core.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Desc:
 *
 * @author: keen
 * Date: 2019-10-03
 * Time: 21:31
 */
@RestController
@Slf4j
public class BrowserSecurityController {

    /**
     * 当前请求的URL地址缓存，可以使用RequestCache拿出当前请求
     */
    private RequestCache requestCache = new HttpSessionRequestCache();

    /**
     * URL跳转工具类
     */
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 当需要身份认证时，跳转到这里
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/authentication/required")
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public ResponseResult requiredAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        //当前的请求
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (savedRequest != null) {
            String targetUrl = savedRequest.getRedirectUrl();
            log.info("当前的跳转请求地址是:" + targetUrl);
            //如果是html请求，则跳转到登录页面，就不会再执行后面的代码
            if (StringUtils.endsWithIgnoreCase(targetUrl, ".html")) {
                redirectStrategy.sendRedirect(request, response,
                        securityProperties.getBrowser().getLoginPage());
            }
        }
        //如果不是html页面，而是一个服务，则返回401和错误信息
        ResponseResult responseResult = new ResponseResult("你访问的服务需要身份认证,请跳转到登录页面");
        return responseResult;
    }
}
