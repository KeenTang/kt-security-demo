package com.k.security.demo;

import com.k.security.core.model.User;
import com.k.security.core.util.JdbcUtils;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * Desc:
 *
 * @author: keen
 * Date: 2019-10-07
 * Time: 15:05
 */
@Component
public class DemoConnectionSignUp implements ConnectionSignUp {
    @Override
    public String execute(Connection<?> connection) {
        //根据社交用户信息默认创建用户并返回唯一标识
        User user = new User();
        user.setUserName(connection.getDisplayName());
        user.setPassword("123456");
        user.setMobile("123456");
        try {
            user.setId(JdbcUtils.create(user));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user.getUserName();
    }
}
