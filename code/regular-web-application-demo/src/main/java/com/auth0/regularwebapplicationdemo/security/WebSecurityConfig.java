package com.auth0.regularwebapplicationdemo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.logout.RedirectServerLogoutSuccessHandler;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@EnableWebFluxSecurity
class WebSecurityConfig {
    @Value("${spring.security.oauth2.client.provider.auth0.issuer-uri}")
    private String issuer;
    @Value("${spring.security.oauth2.client.registration.auth0.client-id}")
    private String clientId;
    @Value("${logout.returnTo}")
    private String logoutReturnTo;
    @Value("${demoapi.audience}")
    private String audience;
    @Autowired
    private ReactiveClientRegistrationRepository clientRegistrationRepository;

    @Bean
    SecurityWebFilterChain configure(ServerHttpSecurity http) throws Exception {
        return http.authorizeExchange()
                .anyExchange().authenticated()
                .and().oauth2Login()
                    .authorizationRequestResolver(
                            new AuthorizationRequestResolver(audience, clientRegistrationRepository)
                    )
                .and().logout().logoutSuccessHandler(logoutSuccessHandler())
                .and().build();
    }

    /**
     * Configures the logout handling to log users out of Auth0 after successful logout from the application.
     * @return a {@linkplain ServerLogoutSuccessHandler} that will be called on successful logout.
     */
    @Bean
    ServerLogoutSuccessHandler logoutSuccessHandler() {
        String logoutUrl = UriComponentsBuilder
                .fromHttpUrl(issuer + "v2/logout?client_id={clientId}&returnTo={returnTo}")
                .encode()
                .buildAndExpand(clientId, logoutReturnTo)
                .toUriString();

        RedirectServerLogoutSuccessHandler handler = new RedirectServerLogoutSuccessHandler();
        handler.setLogoutSuccessUrl(URI.create(logoutUrl));
        return handler;
    }
}
