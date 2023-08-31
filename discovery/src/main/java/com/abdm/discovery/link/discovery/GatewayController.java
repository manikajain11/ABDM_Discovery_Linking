package com.abdm.discovery.link.discovery;

import com.abdm.discovery.clients.DiscoveryServiceClient;
import com.abdm.discovery.common.Caller;
import com.abdm.discovery.common.RequestOrchestrator;
import com.abdm.discovery.common.ResponseOrchestrator;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static com.abdm.discovery.common.Constants.PATH_CARE_CONTEXTS_DISCOVER;
import static com.abdm.discovery.common.Constants.PATH_CARE_CONTEXTS_ON_DISCOVER;
import static com.abdm.discovery.common.Constants.API_CALLED;
import static com.abdm.discovery.common.Constants.X_CM_ID;
import static com.abdm.discovery.common.Constants.X_HIP_ID;
import static net.logstash.logback.argument.StructuredArguments.keyValue;

@RestController
//@RequestMapping("/v1.0")
@AllArgsConstructor
public class GatewayController {

//	private String accessToken="eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJBbFJiNVdDbThUbTlFSl9JZk85ejA2ajlvQ3Y1MXBLS0ZrbkdiX1RCdkswIn0.eyJleHAiOjE2OTI5NjY0NzUsImlhdCI6MTY5Mjk0NDg3NSwianRpIjoiOGVkZmFkMzgtYzNkNi00ZjU2LTk5MTgtMDllNDM1NmEwYzUzIiwiaXNzIjoiaHR0cHM6Ly9kZXYubmRobS5nb3YuaW4vYXV0aC9yZWFsbXMvY2VudHJhbC1yZWdpc3RyeSIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiI2Njg1NmU0YS0yZDMzLTQ3MGQtOWE4MC1iMWIxYzIyMmVmYjkiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJTQlhfMDAzNjE3Iiwic2Vzc2lvbl9zdGF0ZSI6IjE3MzA1N2Y2LTYxYWEtNDI5Mi1iODI0LWRkZThjNGViMDM2ZiIsImFjciI6IjEiLCJhbGxvd2VkLW9yaWdpbnMiOlsiaHR0cDovL2xvY2FsaG9zdDo5MDA3Il0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJoaXUiLCJvZmZsaW5lX2FjY2VzcyIsImhlYWx0aElkIiwicGhyIiwiT0lEQyIsImhpcCJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfSwiU0JYXzAwMzYxNyI6eyJyb2xlcyI6WyJ1bWFfcHJvdGVjdGlvbiJdfX0sInNjb3BlIjoib3BlbmlkIGVtYWlsIHByb2ZpbGUiLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsImNsaWVudElkIjoiU0JYXzAwMzYxNyIsImNsaWVudEhvc3QiOiIxMC4yMzMuNjcuMjA0IiwicHJlZmVycmVkX3VzZXJuYW1lIjoic2VydmljZS1hY2NvdW50LXNieF8wMDM2MTciLCJjbGllbnRBZGRyZXNzIjoiMTAuMjMzLjY3LjIwNCJ9.UGtoS6Fq_MY2pS9gB6oZd0jWD1TQ2BUTYx6HxVVzPnj5N9wtRPuXHr8bkgTeWVmPJJ1v2WIWMQK_Cz5s6pjnXi8P7m_YHfPdZs49ymPfwRuq-GJniZWWRmnU-ntXPMphYEAvAXm2dmEQcY-vuUeiq34Q5gQaCdI9LQdOVUXZ9pEvEWlDudjoDWuTzA1RAkqAVutFjrYTD8yNotdoil0fnNvwk971egbgsAd0ZwT-t2IwxR0RBaj9yC0m3rrQz1G3smchZur0FVGrqkewTFODpaPg4WaXfEeGj9e1-7Lf1jfOmB7KSsCo8Yl5lO0ZV9BqGgujZisJcUX08AHAGL8zWQ";

    private static final Logger logger = LoggerFactory.getLogger(GatewayController.class);
//    RequestOrchestrator<DiscoveryServiceClient> discoveryRequestOrchestrator;
    ResponseOrchestrator discoveryResponseOrchestrator;

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping(PATH_CARE_CONTEXTS_ON_DISCOVER)
    public Mono<Void> onDiscoverCareContext(HttpEntity<String> requestEntity) {
        logger.debug("Request from hip: {}", keyValue("discoveryResponse", requestEntity.getBody()));
        return discoveryResponseOrchestrator.processResponse(requestEntity, X_CM_ID)
                .subscriberContext(context -> context.put(API_CALLED, PATH_CARE_CONTEXTS_ON_DISCOVER));
    }
}