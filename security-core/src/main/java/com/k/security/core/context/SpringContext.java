package com.k.security.core.context;

import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * Desc:
 *
 * @author: keen
 * Date: 2019-10-03
 * Time: 11:39
 */
@Component
@Data
public class SpringContext implements ApplicationContextAware {

    private static ApplicationContext context;

    @Autowired
    private Environment environment;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public <T> T getBean(String name, Class<T> cls) {
        return context.getBean(name, cls);
    }

    public static ApplicationContext context(){
        return context;
    }
}
