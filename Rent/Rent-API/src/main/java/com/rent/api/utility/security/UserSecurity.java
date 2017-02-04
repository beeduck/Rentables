package com.rent.api.utility.security;

import com.rent.utility.oauth.CustomOAuth2Authentication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by duck on 11/21/16.
 */
public class UserSecurity {

    public static String getUsername() {
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static int getUserId() {
        return ((CustomOAuth2Authentication)SecurityContextHolder.getContext()).getUserId();
    }

    public static List<GrantedAuthority> getRoles() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return new ArrayList<GrantedAuthority>(auth.getAuthorities());
    }
}
