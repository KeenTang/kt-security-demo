package com.k.security.app.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.k.security.core.enums.LoginTypeEnum;
import com.k.security.core.model.ResponseResult;
import com.k.security.core.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Desc:
 *
 * @author: keen
 * Date: 2019-10-03
 * Time: 22:44
 */
@Slf4j
@Component("ktAuthenticationFailureHandler")
public class KtAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 自定义登录失败时处理器
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        log.info("登录失败处理器");
        /**
         * 如果是JSON的登录类型，则返回JSON数据
         */
        if (LoginTypeEnum.JSON.equals(securityProperties.getBrowser().getLoginType())) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.getWriter().write(objectMapper.writeValueAsString(new ResponseResult(exception.getMessage())));
        } else {
            /**
             * 不是JSON的登录类型，则调用父类的onAuthenticationSuccess，返回父类中的错误提示信息
             */
            super.onAuthenticationFailure(request, response, exception);
        }
    }
}
