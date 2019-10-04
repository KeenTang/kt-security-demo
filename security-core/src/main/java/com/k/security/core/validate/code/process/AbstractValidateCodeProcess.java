package com.k.security.core.validate.code.process;

import com.k.security.core.enums.ValidateCodeTypeEnum;
import com.k.security.core.validate.code.ImageCode;
import com.k.security.core.validate.code.ValidateCode;
import com.k.security.core.validate.code.ValidateCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Desc:
 *
 * @author: keen
 * Date: 2019-10-04
 * Time: 17:00
 */
public abstract class AbstractValidateCodeProcess implements ValidateCodeProcess {

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Autowired
    private Map<String, ValidateCodeGenerator> validateCodeGeneratorMap;

    /**
     * 发送验证码
     * @param request
     * @param validateCode
     * @throws ServletRequestBindingException
     */
    public abstract void send(ServletWebRequest request, ValidateCode validateCode)
            throws ServletRequestBindingException;

    /**
     * 存储验证码时的Key
     * @return
     */
    public abstract String validateCodeKey();

    private void storage(ServletWebRequest request, ValidateCode validateCode) {
        sessionStrategy.setAttribute(request, this.validateCodeKey(), validateCode);
    }

    @Override
    public void create(ServletWebRequest request, ValidateCodeTypeEnum validateCodeType)
            throws ServletRequestBindingException {
        ValidateCode validateCode = generate(request, validateCodeType);
        storage(request, validateCode);
        send(request, validateCode);
    }


    private ValidateCode generate(ServletWebRequest request, ValidateCodeTypeEnum validateCodeType) {
        return validateCodeGeneratorMap.get(validateCodeType.code()).generate(request);
    }
}
