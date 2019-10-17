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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.social.security.SpringSocialConfigurer;


/**
 * Created with IntelliJ IDEA.
 * Desc:
 *
 * @author: keen
 * Date: 2019-10-08
 * Time: 18:18
 */

public class KtResourceServerConfig{


}
