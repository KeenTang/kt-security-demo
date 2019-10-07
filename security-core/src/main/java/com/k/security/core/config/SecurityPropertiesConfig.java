package com.k.security.core.config;

import com.k.security.core.DefaultLogoutSuccessHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.logout.DelegatingLogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * Created with IntelliJ IDEA.
 * Desc:
 *
 * @author: keen
 * Date: 2019-10-03
 * Time: 21:58
 */
@Configuration
//@EnableConfigurationProperties(value = SecurityProperties.class)
@EnableRedisHttpSession
public class SecurityPropertiesConfig {
    @Bean
    @ConditionalOnMissingBean(value = LogoutSuccessHandler.class)
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new DefaultLogoutSuccessHandler();
    }
}
