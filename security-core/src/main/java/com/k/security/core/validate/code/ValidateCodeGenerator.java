package com.k.security.core.validate.code;

import com.k.security.core.context.SpringContext;
import org.springframework.core.env.Environment;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * Created with IntelliJ IDEA.
 * Desc:
 *
 * @author: keen
 * Date: 2019-10-04
 * Time: 13:29
 */
public interface ValidateCodeGenerator {
    String VALIDATE_CODE_KEY_PREFIX = "VALIDATE_CODE_";

    /**
     * 初始化数据
     */
    default void init(){
        Environment env = SpringContext.context().getEnvironment();
        System.out.println("env=="+env);
    }
    /**
     * 生成验证码
     *
     * @param request
     * @return
     */
    ValidateCode generate(ServletWebRequest request);
}
