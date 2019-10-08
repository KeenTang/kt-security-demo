package com.k.security.core.service;

import com.k.security.core.model.User;
import com.k.security.core.util.JdbcUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
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
public class UsernameDetailService implements UserDetailsService, SocialUserDetailsService {

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
        return new org.springframework.security.core.userdetails.User(userName, password,
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }

    /**
     * 社交登录用户查询
     *
     * @param userId
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public SocialUserDetails loadUserByUserId(String userId)
            throws UsernameNotFoundException {
        String password = passwordEncoder.encode("123456");
        return new SocialUser(userId, password, true, true, true, true,
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin,ROLE_USER"));
    }
}
