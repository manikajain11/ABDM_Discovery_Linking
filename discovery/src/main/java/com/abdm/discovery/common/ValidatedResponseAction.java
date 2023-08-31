package com.abdm.discovery.common;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
@Component
public interface ValidatedResponseAction {

    default Mono<Void> execute(String clientId, JsonNode updatedRequest, String routingKey) {
        return routeResponse(clientId, updatedRequest, routingKey)
                .onErrorResume(throwable -> handleError(throwable, clientId, updatedRequest));
    }

    Mono<Void> routeResponse(String id, JsonNode updatedRequest, String routingKey);

    Mono<Void> handleError(Throwable throwable, String id, JsonNode jsonNode);
}