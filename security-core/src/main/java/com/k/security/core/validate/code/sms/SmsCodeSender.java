package com.k.security.core.validate.code.sms;

/**
 * Created with IntelliJ IDEA.
 * Desc:
 *
 * @author: keen
 * Date: 2019-10-04
 * Time: 16:22
 */
public interface SmsCodeSender {
    /**
     * 发送验证码
     * @param mobile
     * @param code
     */
    void send(String mobile,String code);
}
