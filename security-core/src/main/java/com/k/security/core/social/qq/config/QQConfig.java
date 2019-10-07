package com.k.security.core.social.qq.config;

import com.k.security.core.properties.SecurityProperties;
import com.k.security.core.social.QQProperties;
import com.k.security.core.social.qq.connect.QQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.security.AuthenticationNameUserIdSource;

/**
 * Created with IntelliJ IDEA.
 * Desc:
 *
 * @author: keen
 * Date: 2019-10-05
 * Time: 15:31
 */
@Configuration
@ConditionalOnProperty(prefix = "kt.security.social.qq",name = "app-id")
public class QQConfig extends SocialConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer,
                                       Environment environment) {
        QQProperties qq = securityProperties.getSocial().getQq();
        connectionFactoryConfigurer.addConnectionFactory(
                new QQConnectionFactory(qq.getProviderId(), qq.getAppId(), qq.getAppSecret()));
    }

    /**
     * //获取登陆人
     * @return
     */
    @Override
    public UserIdSource getUserIdSource() {
        return new AuthenticationNameUserIdSource();
    }

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        return null;
    }
}
