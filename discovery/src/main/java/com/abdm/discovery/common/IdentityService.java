package com.abdm.discovery.common;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.abdm.discovery.clients.IdentityProperties;
import com.abdm.discovery.clients.IdentityServiceClient;
import com.abdm.discovery.clients.model.Session;
import com.abdm.discovery.common.cache.CacheAdapter;
import com.abdm.discovery.session.SessionRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import static java.lang.String.format;
@Component
@AllArgsConstructor
public class IdentityService {


    private final IdentityServiceClient identityServiceClient;
    private final IdentityProperties properties;
    @Autowired
    private final CacheAdapter<String, String> accessTokenCache;

    public Mono<String> authenticate() {
        return accessTokenCache.get(getKey())
                .switchIfEmpty(Mono.defer(this::tokenUsingSecret))
                .map(token -> format("%s %s", "Bearer", token));
    }

    private String getKey() {
        return "gateway:gateway:accessToken";
    }

    private Mono<String> tokenUsingSecret() {
        return identityServiceClient.getTokenFor(properties.getClientId(), properties.getClientSecret())
                .flatMap(session -> accessTokenCache.put(getKey(), session.getAccessToken())
                        .thenReturn(session.getAccessToken()));
    }

    @Bean
    public Mono<Session> getTokenFor(SessionRequest request) {
        return identityServiceClient.getTokenFor(request);
    }

    @Bean
    public Mono<JsonNode> configuration(String host) {
        return Mono.fromCallable(() -> new ObjectMapper().readTree(
                format("{\"jwks_uri\": \"%s" + Constants.CURRENT_VERSION + "/certs\"}", host)));
    }

    @Bean
    public Mono<JsonNode> certs() {
        return identityServiceClient.certs();
    }

    @Bean
    public Mono<String> tokenForAdmin() {
        return identityServiceClient.getUserToken(properties.getClientId(),
                properties.getClientSecret(),
                properties.getUserName(),
                properties.getPassword())
                .map(session -> format("%s %s", session.getTokenType(), session.getAccessToken()));
    }
}