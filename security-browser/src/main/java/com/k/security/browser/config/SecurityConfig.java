package com.k.security.browser.config;

import com.k.security.browser.authentication.KtAuthenticationFailureHandler;
import com.k.security.browser.authentication.KtAuthenticationSuccessHandler;
import com.k.security.browser.service.UsernameDetailService;
import com.k.security.core.authentication.mobile.SmsCodeAuthenticationFilter;
import com.k.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.k.security.core.service.UsernameDetailService;
import com.k.security.core.validate.code.ValidateCodeFilter;
import com.k.security.core.properties.BrowserProperties;
import com.k.security.core.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * Created with IntelliJ IDEA.
 * Desc:
 *
 * @author: keen
 * Date: 2019-10-02
 * Time: 18:02
 */
@Slf4j
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private KtAuthenticationSuccessHandler ktAuthenticationSuccessHandler;

    @Autowired
    private KtAuthenticationFailureHandler ktAuthenticationFailureHandler;

    @Autowired
    private ValidateCodeFilter validateCodeFilter;

    @Autowired
    private UsernameDetailService userDetailService;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private SpringSocialConfigurer ktSocialConfig;

    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("SecurityConfig configure");
        BrowserProperties browserProperties = securityProperties.getBrowser();
        String loginPage = browserProperties.getLoginPage();
        String registerPage = browserProperties.getRegisterPage();
        String signOutRedirectUrl = browserProperties.getSignOutRedirectUrl();
        String loginProcessingUrl = browserProperties.getLoginProcessingUrl();
        validateCodeFilter.setAuthenticationFailureHandler(ktAuthenticationFailureHandler);
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        //validateCodeFilter.afterPropertiesSet();
        //http.httpBasic()
        http.authenticationProvider(daoAuthenticationProvider)
                .addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin()
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
                .antMatchers("/authentication/required", loginPage, registerPage,signOutRedirectUrl,
                        "/error", "/code/sms", "/code/image", "/authentication/mobile", "/user/register")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .rememberMe()
                .tokenRepository(persistentRememberMeToken())
                .tokenValiditySeconds(3600)
                .userDetailsService(userDetailService)
                .and()
                .logout()
                .logoutUrl("/signOut")
                .logoutSuccessHandler(logoutSuccessHandler)
                .deleteCookies("JSESSIONID")
                .and()
                .sessionManagement()
                .maximumSessions(1)
                .and()
                .and()
                .csrf().disable()
                .apply(smsCodeAuthenticationSecurityConfig)
                .and()
                .apply(ktSocialConfig);
        ;
    }

    @Bean
    public PersistentTokenRepository persistentRememberMeToken() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        //jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
