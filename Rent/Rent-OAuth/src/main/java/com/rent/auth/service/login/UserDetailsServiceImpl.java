package com.rent.auth.service.login;

import com.rent.auth.dao.user.UserDetailsRepository;
import com.rent.auth.entities.user.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by duck on 11/9/16.
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
    private Logger logger = LoggerFactory.getLogger(UserDetailsService.class);

    @Autowired
    UserDetailsRepository userDetailsRepository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        com.rent.auth.entities.user.UserDetails user = userDetailsRepository.findByUsername(username);

        if(user == null) {
            throw new UsernameNotFoundException("User not found in DB.");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return new UserInfo(user.getId(), user.getUsername(), user.getPassword(), user.isActive(),
                        true, true, true, authentication.getAuthorities());
    }
}