package com.k.security.core.social.qq.api;

import com.k.security.core.model.QQUserInfo;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Desc:
 *
 * @author: keen
 * Date: 2019-10-05
 * Time: 10:27
 */
public interface QQApi {
    QQUserInfo getQQUserInfo() throws IOException;
}
