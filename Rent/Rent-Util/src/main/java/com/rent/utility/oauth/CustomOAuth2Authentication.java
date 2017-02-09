package com.rent.utility.oauth;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;

/**
 * Created by Duck on 2/3/2017.
 */
public class CustomOAuth2Authentication extends OAuth2Authentication {
    private Integer userId;

    public CustomOAuth2Authentication(Integer userId, OAuth2Request storedRequest, Authentication userAuthentication) {
        super(storedRequest, userAuthentication);
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }
}
