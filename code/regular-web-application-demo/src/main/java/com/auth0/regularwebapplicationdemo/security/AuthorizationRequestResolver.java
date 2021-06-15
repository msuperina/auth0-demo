package com.auth0.regularwebapplicationdemo.security;

import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.server.DefaultServerOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

final class AuthorizationRequestResolver extends DefaultServerOAuth2AuthorizationRequestResolver {
    AuthorizationRequestResolver(
            final String audience,
            final ReactiveClientRegistrationRepository clientRegistrationRepository
    ) {
        super(clientRegistrationRepository);
        setAuthorizationRequestCustomizer(authorizationRequestCustomizer(audience));
    }

    private Consumer<OAuth2AuthorizationRequest.Builder> authorizationRequestCustomizer(final String audience) {
        return builder -> {
            Map<String, Object> additionalParameters = new LinkedHashMap();
            additionalParameters.put("audience", audience);
            builder.additionalParameters(additionalParameters);
        };
    }
}
