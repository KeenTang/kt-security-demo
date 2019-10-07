package com.k.security.core.model;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Desc:
 *
 * @author: keen
 * Date: 2019-10-07
 * Time: 11:16
 */
@Data
public class SocialUserInfo {
    private String providerId;

    private String providerUserId;

    private String headImage;

    private String nickname;
}
