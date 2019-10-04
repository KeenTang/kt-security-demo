package com.k.security.core.validate.code.process;

import com.k.security.core.enums.ValidateCodeTypeEnum;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * Created with IntelliJ IDEA.
 * Desc:
 *
 * @author: keen
 * Date: 2019-10-04
 * Time: 16:59
 */
public interface ValidateCodeProcess {
    /**
     * 创建验证码
     * @param request
     * @param validateCodeType
     * @throws ServletRequestBindingException
     */
    void create(ServletWebRequest request,ValidateCodeTypeEnum validateCodeType) throws ServletRequestBindingException;
}
