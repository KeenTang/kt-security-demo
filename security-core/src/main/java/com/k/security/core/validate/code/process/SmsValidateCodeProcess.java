package com.k.security.core.validate.code.process;

import com.k.security.core.validate.code.ValidateCode;
import com.k.security.core.validate.code.ValidateCodeGenerator;
import com.k.security.core.validate.code.sms.SmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * Created with IntelliJ IDEA.
 * Desc:
 *
 * @author: keen
 * Date: 2019-10-04
 * Time: 17:25
 */
@Component
public class SmsValidateCodeProcess extends AbstractValidateCodeProcess {

    @Autowired
    private SmsCodeSender smsCodeSender;

    @Override
    public void send(ServletWebRequest request, ValidateCode validateCode)
            throws ServletRequestBindingException {
        String mobile = ServletRequestUtils.getStringParameter(request.getRequest(), "mobile");
        smsCodeSender.send(mobile, validateCode.getCode());
    }

    /**
     * 存储验证码时的Key
     *
     * @return
     */
    @Override
    public String validateCodeKey() {
        return ValidateCodeGenerator.VALIDATE_CODE_KEY_PREFIX + "SMS";
    }
}
