//package com.rent.cloud.Configuration;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
//import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
//
///**
// * Created by Duck on 11/9/2016.
// */
//@Configuration
//@EnableResourceServer
//public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {
//
//    @Autowired
//    GeneralProperties generalProperties;
//
//    @Override
//    public void configure(final HttpSecurity http) throws Exception {
//        http
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//            .and()
//                .formLogin()
//            .and()
//                .authorizeRequests()
//                .antMatchers("/**").authenticated();
//    }
//
//    @Override
//    public void configure(final ResourceServerSecurityConfigurer config) {
//        config.tokenServices(tokenServices());
//    }
//
//    @Primary
//    @Bean
//    public RemoteTokenServices tokenServices() {
//        RemoteTokenServices tokenServices = new RemoteTokenServices();
//        tokenServices.setCheckTokenEndpointUrl(generalProperties.getAuthServerEndpoint());
////        tokenServices.setCheckTokenEndpointUrl(System.getProperty("OAUTH_CONNECTION"));
//        tokenServices.setClientId(generalProperties.getAuthClient());
//        tokenServices.setClientSecret(generalProperties.getAuthSecret());
//        return tokenServices;
//    }
//}
