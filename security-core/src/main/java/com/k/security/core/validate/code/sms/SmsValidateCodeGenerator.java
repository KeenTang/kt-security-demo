package com.k.security.core.validate.code.sms;

import com.k.security.core.validate.code.ValidateCode;
import com.k.security.core.validate.code.ValidateCodeGenerator;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * Created with IntelliJ IDEA.
 * Desc:
 *
 * @author: keen
 * Date: 2019-10-04
 * Time: 16:33
 */
public class SmsValidateCodeGenerator implements ValidateCodeGenerator {
    /**
     * 生成验证码
     *
     * @param request
     * @return
     */
    @Override
    public ValidateCode generate(ServletWebRequest request) {
        String code = RandomStringUtils.randomNumeric(4);
        return new ValidateCode(code, 6000);
    }
}
