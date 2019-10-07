package com.k.security.core.properties;

import com.k.security.core.enums.LoginTypeEnum;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Desc:
 *
 * @author: keen
 * Date: 2019-10-03
 * Time: 21:55
 */
@Data
public class BrowserProperties {
    /**
     * 如果没有在配置中定义，则使用默认值
     */
    private String loginPage = "/test-login.html";
    private String registerPage = "/test-register.html";
    private String loginProcessingUrl = "/authentication/form";
    private LoginTypeEnum loginType = LoginTypeEnum.JSON;
}
