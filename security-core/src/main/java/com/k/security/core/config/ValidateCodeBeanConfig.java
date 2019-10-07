package com.k.security.core.config;

import com.k.security.core.validate.code.NumberValidateCodeGenerator;
import com.k.security.core.validate.code.ValidateCodeGenerator;
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

}
