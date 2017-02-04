package com.rent.utility.oauth;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;

import java.util.Map;

/**
 * Created by Duck on 2/3/2017.
 */
public class CustomAccessTokenConverter extends DefaultAccessTokenConverter {

    @Override
    public CustomOAuth2Authentication extractAuthentication(Map<String, ?> map) {
        OAuth2Authentication oAuth2Authentication = super.extractAuthentication(map);

        Integer userId = (Integer)map.get("userId");
        CustomOAuth2Authentication customOAuth2Authentication = new CustomOAuth2Authentication(userId,
                oAuth2Authentication.getOAuth2Request(), oAuth2Authentication.getUserAuthentication());

        return customOAuth2Authentication;
    }
}
