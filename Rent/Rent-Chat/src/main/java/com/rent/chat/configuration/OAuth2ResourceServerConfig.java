package com.rent.chat.configuration;

import com.rent.utility.oauth.CustomAccessTokenConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.web.session.SessionManagementFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Duck on 11/9/2016.
 */
@Configuration
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {

//    @Autowired
//    GeneralProperties generalProperties;
//
//    @Autowired
//    EurekaClientConfigBean eurekaClientConfigBean;
//
//    @Bean
//    @Profile("AWS")
//    public EurekaInstanceConfigBean eurekaInstanceConfig(InetUtils inetUtils) {
//        EurekaInstanceConfigBean config = new EurekaInstanceConfigBean(inetUtils);
//        AmazonInfo info = AmazonInfo.Builder.newBuilder().autoBuild("eureka");
//        config.setDataCenterInfo(info);
//        info.getMetadata().put(AmazonInfo.MetaDataKey.publicHostname.getName(), info.get(AmazonInfo.MetaDataKey.publicIpv4));
//        config.setHostname(info.get(AmazonInfo.MetaDataKey.publicHostname));
//        config.setIpAddress(info.get(AmazonInfo.MetaDataKey.publicIpv4));
////        config.setNonSecurePort(port);
//        return config;
//    }

//    @Bean
//    CorsFilter corsFilter() {
//        CorsFilter filter = new CorsFilter();
//        return filter;
//    }

    @Override
    public void configure(final HttpSecurity http) throws Exception {

//        Map<String, String> eurekaServiceUrlMap = new HashMap<String, String>();
//        eurekaServiceUrlMap.put("defaultZone", System.getenv("CLOUD_CONNECTION"));
//        eurekaClientConfigBean.setServiceUrl(eurekaServiceUrlMap);

        http
//                .addFilterBefore(corsFilter(), SessionManagementFilter.class)
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//            .and()
                .authorizeRequests()
                .antMatchers("/**").hasRole("USER")
                .and().cors()
            .and().csrf().disable();

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
        tokenServices.setClientId("rentApiServer");
        tokenServices.setClientSecret("rental490");
        tokenServices.setAccessTokenConverter(accessTokenConverter());
        return tokenServices;
    }

    @Bean
    public CustomAccessTokenConverter accessTokenConverter() {
        return new CustomAccessTokenConverter();
    }
}

//class CorsFilter implements Filter {
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//        HttpServletRequest request= (HttpServletRequest) servletRequest;
//
//        response.setHeader("Access-Control-Allow-Origin", "*");
//        response.setHeader("Access-Control-Allow-Methods", "GET,POST,DELETE,PUT,OPTIONS");
//        response.setHeader("Access-Control-Allow-Headers", "*");
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//        response.setHeader("Access-Control-Max-Age", "180");
//        filterChain.doFilter(servletRequest, servletResponse);
//    }
//
//    @Override
//    public void destroy() {
//
//    }
//}
