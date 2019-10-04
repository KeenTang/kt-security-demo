package com.k.security.core.config;

import com.k.security.core.validate.code.NumberValidateCodeGenerator;
import com.k.security.core.validate.code.ValidateCodeGenerator;
import com.k.security.core.validate.code.sms.DefaultSmsCodeSender;
import com.k.security.core.validate.code.sms.SmsCodeSender;
import com.k.security.core.validate.code.sms.SmsValidateCodeGenerator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created with IntelliJ IDEA.
 * Desc:
 *
 * @author: keen
 * Date: 2019-10-04
 * Time: 13:50
 */
@Configuration
public class ValidateCodeBeanConfig {

    /**
     * 当容器中没有名称为imageCodeGenerator的Bean时，注册NumberValidateCodeGenerator对象
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(name = "imageCodeGenerator")
    public ValidateCodeGenerator imageCodeGenerator() {
        return new NumberValidateCodeGenerator();
    }

    /**
     * 当容器中没有名称为smsCodeGenerator的Bean时，注册SmsValidateCodeGenerator对象
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(name = "smsCodeGenerator")
    public ValidateCodeGenerator smsCodeGenerator(){
        return new SmsValidateCodeGenerator();
    }

    /**
     * 当容器中没有SmsCodeSender的实现类时，注册DefaultSmsCodeSender对象
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(SmsCodeSender.class)
    public SmsCodeSender smsCodeSender() {
        return new DefaultSmsCodeSender();
    }
}
