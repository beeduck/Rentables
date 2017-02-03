package com.rent.auth.configuration;

import com.rent.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

/**
 * Created by duck on 2/3/17.
 */
@Configuration
public class TokenStoreConfig {

    @Autowired
    private DataSource dataSource;

    @Bean
    @Primary
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }

    @Bean
    @Primary
    public AuthorizationServerTokenServices authorizationServerTokenServices() {
        DefaultTokenServices tokenService = new DefaultTokenServices();
        tokenService.setAccessTokenValiditySeconds(Constants.OAUTH_TOKEN_DURATION);
        tokenService.setTokenStore(tokenStore());
        tokenService.setSupportRefreshToken(true);
        return tokenService;
    }
}
