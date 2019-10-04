package com.k.security.core.validate.code;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * Desc:
 *
 * @author: keen
 * Date: 2019-10-04
 * Time: 0:28
 */
@Data
@Component
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Value("${kt.security.browser.loginProcessingUrl}")
    private String loginProcessingUrl;

    @Value("#{securityProperties.code.urls}")
    private String urls;

    private AuthenticationFailureHandler authenticationFailureHandler;

    private Set<String> urlSet = new HashSet<>();

    private AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        if (StringUtils.isNotBlank(this.urls)) {
            for (String url : urls.split(",")) {
                urlSet.add(url);
            }
        }
        urlSet.add(loginProcessingUrl);
        urlSet.add("/authentication/mobile");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        /**
         * 只有请求的URL路径匹配配置的URL才进行验证码验证
         */
        boolean result = false;
        String requestURI = request.getRequestURI();
        for (String url : this.urlSet) {
            if (pathMatcher.match(url, requestURI)) {
                result = true;
                break;
            }
        }
        if (result) {
            try {
                this.validate(request);
            } catch (Exception e) {
                AuthenticationException ae;
                if (e instanceof AuthenticationException) {
                    ae = (AuthenticationException) e;
                } else {
                    ae = new ValidateCodeException(e.getMessage(), e);
                }
                authenticationFailureHandler.onAuthenticationFailure(request, response, ae);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private void validate(HttpServletRequest request) throws ServletRequestBindingException {
        ServletWebRequest servletWebRequest = new ServletWebRequest(request);
        String validateCodeKey = this.getValidateCodeKey(request);
        ValidateCode validateCode = (ValidateCode) sessionStrategy.getAttribute(servletWebRequest, validateCodeKey);
        String validateCodeValue = ServletRequestUtils.getStringParameter(request, "validateCode");
        if (StringUtils.isBlank(validateCodeValue)) {
            throw new ValidateCodeException("请输入验证码");
        }
        if (validateCode == null || validateCode.isExpired()) {
            sessionStrategy.removeAttribute(servletWebRequest, validateCodeKey);
            throw new ValidateCodeException("验证码已过期");
        }
        if (!validateCodeValue.equalsIgnoreCase(validateCode.getCode())) {
            throw new ValidateCodeException("验证码错误");
        }
        sessionStrategy.removeAttribute(servletWebRequest, validateCodeKey);
    }

    private String getValidateCodeKey(HttpServletRequest request) throws ServletRequestBindingException {
        String codeType = ServletRequestUtils.getStringParameter(request, "codeType");
        return ValidateCodeGenerator.VALIDATE_CODE_KEY_PREFIX + codeType;
    }
}
