package com.k.security.core.properties;

import com.k.security.core.social.QQProperties;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Desc:
 *
 * @author: keen
 * Date: 2019-10-05
 * Time: 15:26
 */
@Data
public class SocialProperties {
    private String filterProcessUrl = "/auth";
    private QQProperties qq = new QQProperties();
}
