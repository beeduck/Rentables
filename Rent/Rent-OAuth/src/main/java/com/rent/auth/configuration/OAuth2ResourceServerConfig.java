package com.rent.auth.configuration;

import com.netflix.appinfo.AmazonInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.netflix.eureka.EurekaClientConfigBean;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Duck on 2/3/2017.
 */
@Configuration
@EnableResourceServer
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    GeneralProperties generalProperties;

    @Autowired
    EurekaClientConfigBean eurekaClientConfigBean;

    @Autowired
    TokenStore tokenStore;

    @Autowired
    AccessDeniedHandler accessDeniedHandler;

    @Bean
    @Profile("AWS")
    public EurekaInstanceConfigBean eurekaInstanceConfig(InetUtils inetUtils) {
        EurekaInstanceConfigBean config = new EurekaInstanceConfigBean(inetUtils);
        AmazonInfo info = AmazonInfo.Builder.newBuilder().autoBuild("eureka");
        config.setDataCenterInfo(info);
        info.getMetadata().put(AmazonInfo.MetaDataKey.publicHostname.getName(), info.get(AmazonInfo.MetaDataKey.publicIpv4));
        config.setHostname(info.get(AmazonInfo.MetaDataKey.publicHostname));
        config.setIpAddress(info.get(AmazonInfo.MetaDataKey.publicIpv4));
//        config.setNonSecurePort(port);
        return config;
    }

    @Override
    public void configure(final HttpSecurity http) throws Exception {

        Map<String, String> eurekaServiceUrlMap = new HashMap<String, String>();
        eurekaServiceUrlMap.put("defaultZone", System.getenv("CLOUD_CONNECTION"));
        eurekaClientConfigBean.setServiceUrl(eurekaServiceUrlMap);

        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            .and()
                .authorizeRequests()
                .antMatchers("/**").permitAll();
    }

    @Override
    public void configure(final ResourceServerSecurityConfigurer config) {
        config.accessDeniedHandler(accessDeniedHandler);
        config.tokenServices(customTokenServices());
    }

    private DefaultTokenServices defaultTokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore);
        return defaultTokenServices;
    }

    private CustomTokenServices customTokenServices() {
        CustomTokenServices customTokenServices = new CustomTokenServices();
        customTokenServices.setTokenStore(tokenStore);
        return customTokenServices;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
