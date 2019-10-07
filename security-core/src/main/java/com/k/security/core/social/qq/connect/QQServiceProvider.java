package com.k.security.core.social.qq.connect;

import com.k.security.core.social.qq.api.QQApi;
import com.k.security.core.social.qq.api.impl.QQApiImpl;
import lombok.Data;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Template;

/**
 * Created with IntelliJ IDEA.
 * Desc:
 *
 * @author: keen
 * Date: 2019-10-05
 * Time: 12:58
 */
public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQApi> {

    /**
     * 授权地址，获取Authorization Code
     */
    private static final String AUTHORIZE_URL = "https://graph.qq.com/oauth2.0/authorize";
    /**
     * 通过Authorization Code获取Access Token
     */
    private static final String ACCESS_TOKEN_URL = "https://graph.qq.com/oauth2.0/token";
    private String appId;

    public QQServiceProvider(String appId, String appSecret) {
        super(new QQOAuth2Template(appId, appSecret, AUTHORIZE_URL, ACCESS_TOKEN_URL));
        this.appId = appId;
    }

    @Override
    public QQApi getApi(String accessToken) {
        return new QQApiImpl(accessToken, appId);
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
