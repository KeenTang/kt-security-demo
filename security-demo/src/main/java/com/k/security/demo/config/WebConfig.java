package com.k.security.demo.config;

import com.k.security.demo.filter.TimeFilter;
import com.k.security.demo.interceptor.TimeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * Desc:
 *
 * @author: keen
 * Date: 2019-10-02
 * Time: 22:36
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private TimeInterceptor timeInterceptor;

  /*  @Bean
    public FilterRegistrationBean<TimeFilter> timeFilterRegistrationBean() {
        FilterRegistrationBean<TimeFilter> timeFilterRegistrationBean = new FilterRegistrationBean<>();
        timeFilterRegistrationBean.setUrlPatterns(Arrays.asList("/*"));
        timeFilterRegistrationBean.setFilter(new TimeFilter());
        return timeFilterRegistrationBean;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(timeInterceptor);
    }*/
}
