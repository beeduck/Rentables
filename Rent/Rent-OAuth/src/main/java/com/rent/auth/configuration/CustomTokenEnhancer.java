package com.rent.auth.configuration;

import com.rent.auth.entities.user.UserInfo;
import com.rent.auth.service.login.UserDetailsServiceImpl;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Duck on 2/3/2017.
 */
public class CustomTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication auth2Authentication) {

        UserInfo userInfo = (UserInfo) auth2Authentication.getPrincipal();

        Map<String, Object> additionalInfo = new HashMap<String, Object>();

        additionalInfo.put("userId", userInfo.getUserId());

        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);

        return accessToken;
    }
}
