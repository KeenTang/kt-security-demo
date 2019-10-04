package com.k.security.browser.service;

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
 * Date: 2019-10-03
 * Time: 13:07
 */
@Component
public class UsernameDetailService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = JdbcUtils.queryUserByName(userName);
        if (user == null) {
            throw new UsernameNotFoundException(userName);
        }
        String password = passwordEncoder.encode(user.getPassword());
        System.out.println("password==" + password);
        return new org.springframework.security.core.userdetails.User(userName,password,
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
}
