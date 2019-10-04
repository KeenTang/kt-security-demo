package com.k.security.core.validate.code.sms;

/**
 * Created with IntelliJ IDEA.
 * Desc:
 *
 * @author: keen
 * Date: 2019-10-04
 * Time: 16:23
 */
public class DefaultSmsCodeSender implements SmsCodeSender {
    @Override
    public void send(String mobile, String code) {
        System.out.println("向电话" + mobile + "发送验证码" + code);
    }
}
