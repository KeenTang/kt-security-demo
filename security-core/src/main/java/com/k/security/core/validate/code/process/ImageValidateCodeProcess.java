package com.k.security.core.validate.code.process;

import com.k.security.core.validate.code.ImageCode;
import com.k.security.core.validate.code.ValidateCode;
import com.k.security.core.validate.code.ValidateCodeGenerator;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Desc:
 *
 * @author: keen
 * Date: 2019-10-04
 * Time: 17:27
 */
@Component
public class ImageValidateCodeProcess extends AbstractValidateCodeProcess {
    @Override
    public void send(ServletWebRequest request, ValidateCode validateCode)
            throws ServletRequestBindingException {
        ImageCode imageCode = (ImageCode) validateCode;
        try {
            ImageIO.write(imageCode.getBufferedImage(), "JPEG", request.getResponse().getOutputStream());
        } catch (IOException e) {
            throw new ServletRequestBindingException(e.getMessage(), e);
        }
    }

    @Override
    public String validateCodeKey() {
        return ValidateCodeGenerator.VALIDATE_CODE_KEY_PREFIX + "IMAGE";
    }
}
