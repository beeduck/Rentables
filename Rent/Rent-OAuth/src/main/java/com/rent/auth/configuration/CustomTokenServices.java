package com.rent.auth.configuration;

import com.rent.utility.oauth.CustomOAuth2Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

import java.util.LinkedHashMap;

/**
 * Created by Duck on 2/3/2017.
 */
public class CustomTokenServices extends DefaultTokenServices {

    private ClientDetailsService clientDetailsService;

    private TokenStore tokenStore;

    @Override
    public CustomOAuth2Authentication loadAuthentication(String accessTokenValue) throws AuthenticationException, InvalidTokenException {
        OAuth2AccessToken accessToken = this.tokenStore.readAccessToken(accessTokenValue);
        if(accessToken == null) {
            throw new InvalidTokenException("Invalid access token: " + accessTokenValue);
        } else if(accessToken.isExpired()) {
            this.tokenStore.removeAccessToken(accessToken);
            throw new InvalidTokenException("Access token expired: " + accessTokenValue);
        } else {
            OAuth2Authentication result = this.tokenStore.readAuthentication(accessToken);
            if(this.clientDetailsService != null) {
                String clientId = result.getOAuth2Request().getClientId();

                try {
                    this.clientDetailsService.loadClientByClientId(clientId);
                } catch (ClientRegistrationException var6) {
                    throw new InvalidTokenException("Client not valid: " + clientId, var6);
                }
            }

            LinkedHashMap additionalInformation = (LinkedHashMap)accessToken.getAdditionalInformation();
            Integer userId = (Integer)additionalInformation.get("userId");
            CustomOAuth2Authentication customOAuth2Authentication = new CustomOAuth2Authentication(userId,
                    result.getOAuth2Request(), result.getUserAuthentication());
            return customOAuth2Authentication;
        }
    }

    @Override
    public void setClientDetailsService(ClientDetailsService clientDetailsService) {
        super.setClientDetailsService(clientDetailsService);
        this.clientDetailsService = clientDetailsService;
    }

    @Override
    public void setTokenStore(TokenStore tokenStore) {
        super.setTokenStore(tokenStore);
        this.tokenStore = tokenStore;
    }
}
