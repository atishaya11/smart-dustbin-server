package com.jss.sdmp.config;

import com.jss.sdmp.config.jwt.CustomTokenEnhancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Arrays;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final TokenStore tokenStore;

    private final JwtAccessTokenConverter accessTokenConverter;

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;


    @Value("${app.client.id}")
    private String clientId;

    @Value("${app.client.secret}")
    private String clientSecret;


    @Autowired
    public AuthorizationServerConfig(TokenStore tokenStore, JwtAccessTokenConverter accessTokenConverter, AuthenticationManager authenticationManager, UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.tokenStore = tokenStore;
        this.accessTokenConverter = accessTokenConverter;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {
        configurer
        .inMemory()
                .withClient(clientId)
                .scopes("read", "write")
                .authorizedGrantTypes("password", "refresh_token")
                .authorities("ROLE_CLIENT","ROLE_TRUSTED_CLIENT")
                .secret(passwordEncoder.encode(clientSecret))
                .accessTokenValiditySeconds(1800)
        ;
    }


    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore)
                .reuseRefreshTokens(false)
                .accessTokenConverter(accessTokenConverter)
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .tokenEnhancer(tokenEnhancerChain());
    }

    @Bean
    public TokenEnhancerChain tokenEnhancerChain(){
        final TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), accessTokenConverter));
        return tokenEnhancerChain;
    }

    @Bean
    public TokenEnhancer tokenEnhancer() {
        return new CustomTokenEnhancer();
    }


    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.tokenKeyAccess("hasAuthority('ROLE_TRUSTED_CLIENT')").checkTokenAccess("hasAuthority('ROLE_TRUSTED_CLIENT')");
    }

}