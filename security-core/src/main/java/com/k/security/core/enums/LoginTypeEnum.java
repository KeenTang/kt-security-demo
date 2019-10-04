package com.k.security.core.enums;

/**
 * Created with IntelliJ IDEA.
 * Desc:
 *
 * @author: keen
 * Date: 2019-10-03
 * Time: 22:51
 */
public enum  LoginTypeEnum {
    /**
     * 登录类型，如果是JSON表示前端异步登录，则返回Json信息，
     * 如果是REDIRECT是一个表单登录，则执行页面跳转
     */
    REDIRECT,JSON
}
