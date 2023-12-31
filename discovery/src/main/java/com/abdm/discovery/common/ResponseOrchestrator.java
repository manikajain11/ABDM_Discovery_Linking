package com.abdm.discovery.common;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.UUID;

import static com.abdm.discovery.common.Constants.CORRELATION_ID;
import static com.abdm.discovery.common.Constants.nameMap;
import static com.abdm.discovery.common.Utils.updateRequestId;
import static net.logstash.logback.argument.StructuredArguments.keyValue;
@Component
@AllArgsConstructor
public class ResponseOrchestrator {
    @Autowired
    Validator validator;
    ValidatedResponseAction validatedResponseAction;
    private static final Logger logger = LoggerFactory.getLogger(ResponseOrchestrator.class);

    public Mono<Void> processResponse(HttpEntity<String> maybeResponse, String routingKey) {
        StringBuilder apiCalled = new StringBuilder();
        return Mono.subscriberContext()
                .flatMap(context -> {
                    apiCalled.append((String) context.get("apiCalled"));
                    return validator.validateResponse(maybeResponse, routingKey);
                }).doOnSuccess(validatedResponse -> offloadThis(validatedResponse, routingKey, apiCalled.toString()))
                .then();
    }

    private void offloadThis(ValidatedResponse response, String routingKey, String apiCalled) {
        Mono.defer(() -> {
            var updatedJsonNode = updateRequestId(response.getDeSerializedJsonNode(), response.getCallerRequestId());
            logger.info("Passing the response back {} {} {} {}", keyValue("requestId", response.getCallerRequestId())
                    , keyValue("target", nameMap.get(routingKey))
                    , keyValue("targetId", response.getId())
                    , keyValue("apiCalled", apiCalled));
            return validatedResponseAction.execute(response.getId(), updatedJsonNode, routingKey);
        }).subscriberContext(ctx -> {
            Optional<String> correlationId = Optional.ofNullable(MDC.get(CORRELATION_ID));
            return correlationId.map(id -> ctx.put(CORRELATION_ID, id))
                    .orElseGet(() -> ctx.put(CORRELATION_ID, UUID.randomUUID().toString()));
        }).subscribe();
    }
}