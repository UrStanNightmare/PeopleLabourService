package ru.academicians.myhelper.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import static ru.academicians.myhelper.defaults.DefaultKeys.PRIVATE_KEY;
import static ru.academicians.myhelper.defaults.DefaultKeys.PUBLIC_KEY;

@Configuration
@EnableResourceServer
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenServices(tokenResServerServices());
    }

    @Bean
    public TokenStore tokenResServerStore() {
        return new JwtTokenStore(accessResServerTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessResServerTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setVerifierKey(PUBLIC_KEY);
        converter.setSigningKey(PRIVATE_KEY);
        return converter;
    }

    @Bean
    @Qualifier("tokenResServerServices")
    public DefaultTokenServices tokenResServerServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenResServerStore());
        return defaultTokenServices;
    }
}
