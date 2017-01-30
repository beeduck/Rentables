package com.rent.auth.configuration;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Duck on 1/19/2017.
 */
@Configuration
public class FeignInterceptor {

    @Autowired
    TokenStore tokenStore;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {

                OAuth2AccessToken token = getAccessToken();

                requestTemplate.header("Authorization", "Bearer " + token);
            }
        };
    }

    private OAuth2AccessToken getAccessToken() {
        Map<String, String> authorizationParameters = new HashMap<String, String>();
        authorizationParameters.put("scope", "user");
        authorizationParameters.put("client_id", "rentAuthServer");
        authorizationParameters.put("grant", "password");

        OAuth2Request authorizationRequest = new OAuth2Request(authorizationParameters, "rentAuthServer", null, true, null, null, null, null, null);

        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_MODULE"));

        // Create principal and auth token
        User userPrincipal = new User("rentAuthServer", "", true, true, true, true, authorities);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userPrincipal, null, authorities) ;

        OAuth2Authentication authenticationRequest = new OAuth2Authentication(authorizationRequest, authenticationToken);
        authenticationRequest.setAuthenticated(true);

        // Token Enhancer
        TokenEnhancer tokenEnhancer = new TokenEnhancer() {
            @Override
            public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
                return oAuth2AccessToken;
            }
        };

        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenEnhancer(tokenEnhancer);
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setTokenStore(tokenStore);

        OAuth2AccessToken accessToken = tokenServices.createAccessToken(authenticationRequest);

        tokenStore.storeAccessToken(accessToken, authenticationRequest);

        return accessToken;
    }

}
