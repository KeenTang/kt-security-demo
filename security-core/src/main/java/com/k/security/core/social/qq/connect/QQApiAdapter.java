package com.k.security.core.social.qq.connect;

import com.k.security.core.model.QQUserInfo;
import com.k.security.core.social.qq.api.QQApi;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Desc:适配用户信息，将QQ用户信息转换为
 * @author: keen
 * Date: 2019-10-05
 * Time: 14:08
 */
public class QQApiAdapter implements ApiAdapter<QQApi> {
    @Override
    public boolean test(QQApi qqApi) {
        return true;
    }

    @Override
    public void setConnectionValues(QQApi qqApi, ConnectionValues connectionValues) {
        QQUserInfo qqUserInfo = null;
        try {
            qqUserInfo = qqApi.getQQUserInfo();
        } catch (IOException e) {
           throw new RuntimeException(e);
        }
        connectionValues.setDisplayName(qqUserInfo.getNickname());
        connectionValues.setImageUrl(qqUserInfo.getFigureurl_qq_1());
        connectionValues.setProfileUrl(null);
        connectionValues.setProviderUserId(qqUserInfo.getOpenId());
    }

    @Override
    public UserProfile fetchUserProfile(QQApi qqApi) {
        return null;
    }

    @Override
    public void updateStatus(QQApi qqApi, String s) {

    }
}
