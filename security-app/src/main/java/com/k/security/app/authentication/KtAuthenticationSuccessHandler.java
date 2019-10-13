package com.k.security.app.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.k.security.core.enums.LoginTypeEnum;
import com.k.security.core.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.Objects;

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

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private AuthorizationServerTokenServices authorizationServerTokenServices;

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
            response.getWriter().write(objectMapper.writeValueAsString(token(request,authentication)));
        } else {
            /**
             * 不是JSON的登录类型，则调用父类的onAuthenticationSuccess，执行页面跳转
             */
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }

    private OAuth2AccessToken token(HttpServletRequest request, Authentication authentication) throws IOException {
        //如果是Token验证，请求头就会带上Authorization属性，并且值是Basic加client-id和client-secret组成base64编码后的字符串
        String header = request.getHeader("Authorization");

        //如果有Authorization属性，但值不是Basic开头就提示异常
        if (header == null || !header.startsWith("Basic ")) {
            throw new RuntimeException("错误的client信息");
        }

        String[] tokens = extractAndDecodeHeader(header, request);
        assert tokens.length == 2;

        String clientId = tokens[0];
        String clientSecret = tokens[1];
        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
        if (clientDetails == null) {
            throw new RuntimeException(String.format("client-id:%s不存在", clientId));
        }
        if (!Objects.equals(clientDetails.getClientSecret(), clientSecret)) {
            throw new RuntimeException("client-secret不匹配");
        }
        TokenRequest tokenRequest = new TokenRequest(Collections.EMPTY_MAP, clientId,
                clientDetails.getScope(), "custom");
        OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
        OAuth2Authentication auth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);
        OAuth2AccessToken accessToken = authorizationServerTokenServices.createAccessToken(auth2Authentication);
        return accessToken;
    }

    private String[] extractAndDecodeHeader(String header, HttpServletRequest request)
            throws IOException {
        //因为前6位是Basic和一个空格，所以拿6位之后的字符串
        byte[] base64Token = header.substring(6).getBytes("UTF-8");
        byte[] decoded;
        try {
            //base64解码
            decoded = Base64.getDecoder().decode(base64Token);
        } catch (IllegalArgumentException e) {
            throw new BadCredentialsException(
                    "Failed to decode basic authentication token");
        }

        //将解码后字节转成字符串就会拿到一个key:value这样的字符串
        String token = new String(decoded, StandardCharsets.UTF_8);

        int delim = token.indexOf(":");

        if (delim == -1) {
            throw new BadCredentialsException("Invalid basic authentication token");
        }
        //返回由client-id和client-secret组成的数组
        return new String[]{token.substring(0, delim), token.substring(delim + 1)};
    }
}
