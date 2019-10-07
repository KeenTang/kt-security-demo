package com.k.security.core.util;

import com.k.security.core.context.SpringContext;
import com.k.security.core.model.User;
import org.springframework.core.env.Environment;

import java.sql.*;

/**
 * Created with IntelliJ IDEA.
 * Desc:
 *
 * @author: keen
 * Date: 2019-10-03
 * Time: 11:08
 */
public final class JdbcUtils {
    /**
     * spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
     * spring.datasource.username=root
     * spring.datasource.password=root123
     * spring.datasource.url=jdbc:mysql://localhost:3306/security-demo?characterEncoding=utf-8&useSSL=false&serverTimezone=UTC
     */
    private static String url;
    private static String userName;
    private static String password;
    private static String driverClassName;

    static {
        Environment environment = SpringContext.context().getEnvironment();
        driverClassName = environment.getProperty("spring.datasource.driver-class-name");
        url = environment.getProperty("spring.datasource.url");
        userName = environment.getProperty("spring.datasource.username");
        password = environment.getProperty("spring.datasource.password");
        try {
            Class.forName(driverClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static User queryUserByName(String userName) {
        try {
            return query(" where user_name=?", userName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static User queryUserByMobile(String mobile) {
        try {
            return query(" where mobile=?", mobile);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static User query(String conditions, Object... args) throws SQLException {
        Connection connection = getConnection();
        String sql = "select * from t_user " + conditions;
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            preparedStatement.setObject(i + 1, args[i]);
        }
        ResultSet resultSet = preparedStatement.executeQuery();
        return createUser(resultSet);
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, JdbcUtils.userName, password);
    }

    public static int create(User user) throws SQLException {
        Connection connection = getConnection();
        String sql = "insert into t_user(user_name,password,mobile) values(?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, user.getUserName());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setString(3, user.getMobile());
        preparedStatement.execute();
        ResultSet keys = preparedStatement.getGeneratedKeys();
        if (keys.next()) {
            return keys.getInt(1);
        } else {
            throw new RuntimeException("生成ID失败");
        }
    }

    private static User createUser(ResultSet resultSet) throws SQLException {
        if (resultSet.first()) {
            User user = new User();
            user.setId(resultSet.getInt("id"));
            user.setUserName(resultSet.getString("user_name"));
            user.setPassword(resultSet.getString("password"));
            return user;
        } else {
            return null;
        }
    }
}
