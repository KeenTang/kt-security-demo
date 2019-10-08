package com.k.security.app.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.k.security.core.enums.LoginTypeEnum;
import com.k.security.core.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
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
 * Time: 22:39
 */

@Slf4j
@Component("KtAuthenticationSuccessHandler")
public class KtAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 用户登录成功后自定义处理
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        log.info("用户登录成功后自定义处理");
        /**
         * 如果是JSON的登录类型，则返回JSON数据
         */
        if (LoginTypeEnum.JSON.equals(securityProperties.getBrowser().getLoginType())) {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.getWriter().write(objectMapper.writeValueAsString(authentication));
        } else {
            /**
             * 不是JSON的登录类型，则调用父类的onAuthenticationSuccess，执行页面跳转
             */
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
