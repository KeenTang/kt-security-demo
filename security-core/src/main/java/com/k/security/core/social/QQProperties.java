package com.k.security.core.social;


import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Desc:
 *
 * @author: keen
 * Date: 2019-10-05
 * Time: 15:07
 */
@Data
public class QQProperties {
    private String appId;
    private String appSecret;
    private String providerId = "qq";
}
