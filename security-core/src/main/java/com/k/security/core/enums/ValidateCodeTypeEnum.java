package com.k.security.core.enums;

/**
 * Created with IntelliJ IDEA.
 * Desc:
 *
 * @author: keen
 * Date: 2019-10-04
 * Time: 17:11
 */
public enum ValidateCodeTypeEnum {
    SMS("smsCodeGenerator"), IMAGE("imageCodeGenerator");
    private String code;

    ValidateCodeTypeEnum(String code) {
        this.code = code;
    }

    public String code() {
        return this.code;
    }
}
