package com.rent.api.configuration;

import com.netflix.appinfo.AmazonInfo;
import com.rent.utility.oauth.CustomAccessTokenConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.netflix.eureka.EurekaClientConfigBean;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Duck on 11/9/2016.
 */
@Configuration
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    GeneralProperties generalProperties;

    @Autowired
    EurekaClientConfigBean eurekaClientConfigBean;

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
        config.tokenServices(tokenServices());
    }

    @Primary
    @Bean
    public RemoteTokenServices tokenServices() {
        RemoteTokenServices tokenServices = new RemoteTokenServices();
        tokenServices.setCheckTokenEndpointUrl(System.getenv("OAUTH_CONNECTION"));
        tokenServices.setClientId(generalProperties.getAuthClient());
        tokenServices.setClientSecret(generalProperties.getAuthSecret());
        tokenServices.setAccessTokenConverter(accessTokenConverter());
        return tokenServices;
    }

    @Bean
    public CustomAccessTokenConverter accessTokenConverter() {
        return new CustomAccessTokenConverter();
    }
}
