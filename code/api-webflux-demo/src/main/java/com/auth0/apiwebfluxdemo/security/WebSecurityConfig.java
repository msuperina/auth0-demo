package com.auth0.apiwebfluxdemo.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class WebSecurityConfig {
    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuer;

    @Value("${api.audience}")
    private String audience;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(final ServerHttpSecurity http) {
        return http
                .cors()
                .and().authorizeExchange()
                    .pathMatchers(HttpMethod.GET, "/health/**").permitAll()
                    .anyExchange().authenticated()
                .and().oauth2ResourceServer().jwt().and()
                .and().build();
    }

    @Bean
    ReactiveJwtDecoder jwtDecoder() {
        // By default, Spring Security does not validate the "aud" claim of the token,
        // Add our own validator is easy to ensure that this token is indeed intended for our app.
        NimbusReactiveJwtDecoder jwtDecoder = (NimbusReactiveJwtDecoder)
                ReactiveJwtDecoders.fromOidcIssuerLocation(issuer);

        OAuth2TokenValidator<Jwt> audienceValidator = new AudienceValidator(audience);
        OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(issuer);
        OAuth2TokenValidator<Jwt> withAudience = new DelegatingOAuth2TokenValidator<>(withIssuer, audienceValidator,
                new JwtTimestampValidator());

        jwtDecoder.setJwtValidator(withAudience);

        return jwtDecoder;
    }
}
