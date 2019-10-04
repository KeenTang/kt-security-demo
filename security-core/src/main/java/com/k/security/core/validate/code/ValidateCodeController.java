package com.k.security.core.validate.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.k.security.core.enums.ValidateCodeTypeEnum;
import com.k.security.core.validate.code.process.ValidateCodeProcess;
import com.k.security.core.validate.code.sms.SmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Desc:
 *
 * @author: keen
 * Date: 2019-10-04
 * Time: 0:00
 */
@RestController
@RequestMapping("/code")
public class ValidateCodeController {


    @Autowired
    private ValidateCodeProcess imageValidateCodeProcess;

    @Autowired
    private ValidateCodeProcess smsValidateCodeProcess;

    @GetMapping("/image")
    public void imageCode(HttpServletRequest request, HttpServletResponse response)
            throws ServletRequestBindingException {
        imageValidateCodeProcess.create(getServletWebRequest(request, response), ValidateCodeTypeEnum.IMAGE);
    }

    @GetMapping("/sms")
    public void smsCode(HttpServletRequest request, HttpServletResponse response)
            throws ServletRequestBindingException {
        smsValidateCodeProcess.create(getServletWebRequest(request, response), ValidateCodeTypeEnum.SMS);
    }

    private ServletWebRequest getServletWebRequest(HttpServletRequest request, HttpServletResponse response) {
        return new ServletWebRequest(request, response);
    }
}
