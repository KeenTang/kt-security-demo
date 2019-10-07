package com.k.security.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.k.security.core.model.ResponseResult;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Desc:
 *
 * @author: keen
 * Date: 2019-10-07
 * Time: 21:23
 */
@Slf4j
@Data
public class DefaultLogoutSuccessHandler implements LogoutSuccessHandler {

    @Value("#{securityProperties.browser.signOutRedirectUrl}")
    private String signOutRedirectUrl;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                Authentication authentication) throws IOException, ServletException {
        log.info("退出成功处理");
        if (StringUtils.isBlank(signOutRedirectUrl)) {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.getWriter().write(objectMapper.writeValueAsString(new ResponseResult("退出成功!")));
        } else {
            response.sendRedirect(signOutRedirectUrl);
        }
    }
}
