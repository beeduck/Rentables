package com.rent.auth.utility.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by duck on 2/3/17.
 */
public class UserSecurity {

    public static String getUsername() {
        return (String) ((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                            .getUsername();
    }

    public static List<GrantedAuthority> getRoles() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return new ArrayList<GrantedAuthority>(auth.getAuthorities());
    }
}
