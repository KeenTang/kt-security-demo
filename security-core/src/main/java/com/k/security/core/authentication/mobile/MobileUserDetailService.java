package com.k.security.core.authentication.mobile;

import com.k.security.core.model.User;
import com.k.security.core.util.JdbcUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * Desc:
 *
 * @author: keen
 * Date: 2019-10-04
 * Time: 22:43
 */
@Component
public class MobileUserDetailService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String mobile) throws UsernameNotFoundException {
        User user = JdbcUtils.queryUserByMobile(mobile);
        if (user == null) {
            throw new UsernameNotFoundException("手机号" + mobile + "未注册");
        }
        String password = passwordEncoder.encode(user.getPassword());
        System.out.println("password==" + password);
        return new org.springframework.security.core.userdetails.User(user.getUserName(), password,
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
}
