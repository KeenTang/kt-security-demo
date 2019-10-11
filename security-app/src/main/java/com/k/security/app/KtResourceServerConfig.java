package com.k.security.app;

import com.k.security.app.authentication.KtAuthenticationFailureHandler;
import com.k.security.app.authentication.KtAuthenticationSuccessHandler;
import com.k.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.k.security.core.properties.BrowserProperties;
import com.k.security.core.properties.SecurityProperties;
import com.k.security.core.service.UsernameDetailService;
import com.k.security.core.validate.code.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * Created with IntelliJ IDEA.
 * Desc:
 *
 * @author: keen
 * Date: 2019-10-08
 * Time: 18:18
 */
@Configuration
@EnableResourceServer
public class KtResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private KtAuthenticationSuccessHandler ktAuthenticationSuccessHandler;

    @Autowired
    private KtAuthenticationFailureHandler ktAuthenticationFailureHandler;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private SpringSocialConfigurer ktSocialConfig;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        BrowserProperties browserProperties = securityProperties.getBrowser();
        String loginPage = browserProperties.getLoginPage();
        String registerPage = browserProperties.getRegisterPage();
        String signOutRedirectUrl = browserProperties.getSignOutRedirectUrl();
        String loginProcessingUrl = browserProperties.getLoginProcessingUrl();
        http.formLogin()
                .loginPage("/authentication/required")
                .loginProcessingUrl(loginProcessingUrl)
                /**
                 * 使用自定义登录成功处理器
                 */
                .successHandler(ktAuthenticationSuccessHandler)
                /**
                 * 使用自定义登录失败处理器
                 */
                .failureHandler(ktAuthenticationFailureHandler)
                .and()
                .authorizeRequests()
                .antMatchers("/authentication/required", loginPage, registerPage, signOutRedirectUrl,
                        "/error", "/code/sms", "/code/image", "/authentication/mobile", "/user/register")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable()
               // .apply(smsCodeAuthenticationSecurityConfig)
              //  .and()
                .apply(ktSocialConfig);
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
