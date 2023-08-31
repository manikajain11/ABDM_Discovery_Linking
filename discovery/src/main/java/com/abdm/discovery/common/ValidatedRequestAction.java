package com.abdm.discovery.common;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Map;
@Component
public interface ValidatedRequestAction {

    default Mono<Void> execute(String sourceId, String targetId, Map<String, Object> updatedRequest, String routingKey) {
        return routeRequest(sourceId, targetId, updatedRequest, routingKey)
                .onErrorResume(throwable -> handleError(throwable, targetId, updatedRequest, sourceId));
    }

    Mono<Void> routeRequest(String sourceId, String targetId, Map<String, Object> updatedRequest, String routingKey);

    Mono<Void> handleError(Throwable throwable, String id, Map<String, Object> map, String sourceId);
}