package com.rent.auth.service.login;

import com.rent.data.dataaccess.auth.dao.user.UserDetailsDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by duck on 11/9/16.
 */
@ComponentScan("com.rent.data.dataaccess.auth")
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
    private Logger logger = LoggerFactory.getLogger(UserDetailsService.class);

    @Autowired
    UserDetailsDAO userDetailsDAO;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        com.rent.data.dataaccess.auth.entities.user.UserDetails user = userDetailsDAO.getUserByUsername(username);

        if(user == null) {
            throw new UsernameNotFoundException("User not found in DB.");
        }

        return new User(user.getUsername(), user.getPassword(), user.isActive(), true, true, true, buildUserAuthority());
    }

    private List<GrantedAuthority> buildUserAuthority() {
        Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
        setAuths.add(new SimpleGrantedAuthority("ROLE_USER"));  // TODO: Adjust for additional roles in the future
        return new ArrayList<GrantedAuthority>(setAuths);
    }
}