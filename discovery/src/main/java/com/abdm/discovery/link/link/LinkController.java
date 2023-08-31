package com.abdm.discovery.link.link;

import com.abdm.discovery.clients.HipInitLinkServiceClient;
import com.abdm.discovery.clients.LinkConfirmServiceClient;
import com.abdm.discovery.clients.LinkInitServiceClient;
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

import static com.abdm.discovery.common.Constants.API_CALLED;
import static com.abdm.discovery.common.Constants.PATH_ADD_CARE_CONTEXTS;
import static com.abdm.discovery.common.Constants.PATH_LINK_CONFIRM;
import static com.abdm.discovery.common.Constants.PATH_LINK_INIT;
import static com.abdm.discovery.common.Constants.PATH_LINK_ON_CONFIRM;
import static com.abdm.discovery.common.Constants.PATH_LINK_ON_INIT;
import static com.abdm.discovery.common.Constants.PATH_ON_ADD_CARE_CONTEXTS;
import static com.abdm.discovery.common.Constants.X_CM_ID;
import static com.abdm.discovery.common.Constants.X_HIP_ID;
import static com.abdm.discovery.common.Constants.bridgeId;
import static net.logstash.logback.argument.StructuredArguments.keyValue;

@RestController

//private String accessToken= "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJBbFJiNVdDbThUbTlFSl9JZk85ejA2ajlvQ3Y1MXBLS0ZrbkdiX1RCdkswIn0.eyJleHAiOjE2OTI5NjY0NzUsImlhdCI6MTY5Mjk0NDg3NSwianRpIjoiOGVkZmFkMzgtYzNkNi00ZjU2LTk5MTgtMDllNDM1NmEwYzUzIiwiaXNzIjoiaHR0cHM6Ly9kZXYubmRobS5nb3YuaW4vYXV0aC9yZWFsbXMvY2VudHJhbC1yZWdpc3RyeSIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiI2Njg1NmU0YS0yZDMzLTQ3MGQtOWE4MC1iMWIxYzIyMmVmYjkiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJTQlhfMDAzNjE3Iiwic2Vzc2lvbl9zdGF0ZSI6IjE3MzA1N2Y2LTYxYWEtNDI5Mi1iODI0LWRkZThjNGViMDM2ZiIsImFjciI6IjEiLCJhbGxvd2VkLW9yaWdpbnMiOlsiaHR0cDovL2xvY2FsaG9zdDo5MDA3Il0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJoaXUiLCJvZmZsaW5lX2FjY2VzcyIsImhlYWx0aElkIiwicGhyIiwiT0lEQyIsImhpcCJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfSwiU0JYXzAwMzYxNyI6eyJyb2xlcyI6WyJ1bWFfcHJvdGVjdGlvbiJdfX0sInNjb3BlIjoib3BlbmlkIGVtYWlsIHByb2ZpbGUiLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsImNsaWVudElkIjoiU0JYXzAwMzYxNyIsImNsaWVudEhvc3QiOiIxMC4yMzMuNjcuMjA0IiwicHJlZmVycmVkX3VzZXJuYW1lIjoic2VydmljZS1hY2NvdW50LXNieF8wMDM2MTciLCJjbGllbnRBZGRyZXNzIjoiMTAuMjMzLjY3LjIwNCJ9.UGtoS6Fq_MY2pS9gB6oZd0jWD1TQ2BUTYx6HxVVzPnj5N9wtRPuXHr8bkgTeWVmPJJ1v2WIWMQK_Cz5s6pjnXi8P7m_YHfPdZs49ymPfwRuq-GJniZWWRmnU-ntXPMphYEAvAXm2dmEQcY-vuUeiq34Q5gQaCdI9LQdOVUXZ9pEvEWlDudjoDWuTzA1RAkqAVutFjrYTD8yNotdoil0fnNvwk971egbgsAd0ZwT-t2IwxR0RBaj9yC0m3rrQz1G3smchZur0FVGrqkewTFODpaPg4WaXfEeGj9e1-7Lf1jfOmB7KSsCo8Yl5lO0ZV9BqGgujZisJcUX08AHAGL8zWQ";
@AllArgsConstructor
public class LinkController {
    private static final Logger logger = LoggerFactory.getLogger(LinkController.class);
    RequestOrchestrator<LinkInitServiceClient> linkInitRequestOrchestrator;
    RequestOrchestrator<LinkConfirmServiceClient> linkConfirmRequestOrchestrator;
    RequestOrchestrator<HipInitLinkServiceClient> hipInitLinkRequestOrchestrator;
    ResponseOrchestrator linkInitResponseOrchestrator;
    ResponseOrchestrator linkConfirmResponseOrchestrator;
    ResponseOrchestrator hipInitLinkResponseOrchestrator;


    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping(PATH_LINK_INIT)
    public Mono<Void> linkInit(HttpEntity<String> requestEntity) {
        return ReactiveSecurityContextHolder.getContext()
                .map(securityContext -> (Caller) securityContext.getAuthentication().getPrincipal())
                .map(Caller::getClientId)
                .flatMap(clientId ->
                        linkInitRequestOrchestrator.handleThis(requestEntity, X_HIP_ID, X_CM_ID, clientId))
                .subscriberContext(context -> context.put(API_CALLED, PATH_LINK_INIT));
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping(PATH_LINK_ON_INIT)
    public Mono<Void> linkOnInit(HttpEntity<String> requestEntity) {
        return linkInitResponseOrchestrator.processResponse(requestEntity, X_CM_ID)
                .subscriberContext(context -> context.put(API_CALLED, PATH_LINK_ON_INIT));
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping(PATH_LINK_CONFIRM)
    public Mono<Void> linkConfirm(HttpEntity<String> requestEntity) {
        return ReactiveSecurityContextHolder.getContext()
                .map(securityContext -> (Caller) securityContext.getAuthentication().getPrincipal())
                .map(Caller::getClientId)
                .flatMap(clientId ->
                        linkConfirmRequestOrchestrator.handleThis(requestEntity, X_HIP_ID, X_CM_ID, clientId))
                .subscriberContext(context -> context.put(API_CALLED, PATH_LINK_CONFIRM));
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping(PATH_LINK_ON_CONFIRM)
    public Mono<Void> linkOnConfirm(HttpEntity<String> requestEntity) {
        return linkConfirmResponseOrchestrator.processResponse(requestEntity, X_CM_ID)
                .subscriberContext(context -> context.put(API_CALLED, PATH_LINK_ON_CONFIRM));
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping(PATH_ADD_CARE_CONTEXTS)
    public Mono<Void> addCareContexts(HttpEntity<String> requestEntity) {
        return ReactiveSecurityContextHolder.getContext()
                .map(securityContext -> (Caller) securityContext.getAuthentication().getPrincipal())
                .map(Caller::getClientId)
                .flatMap(clientId ->
                        hipInitLinkRequestOrchestrator
                                .handleThis(requestEntity, X_CM_ID, X_HIP_ID, bridgeId(clientId))
                                .subscriberContext(context -> context.put(API_CALLED, PATH_ADD_CARE_CONTEXTS)));

    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping(PATH_ON_ADD_CARE_CONTEXTS)
    public Mono<Void> onAddCareContexts(HttpEntity<String> requestEntity) {
        logger.debug("Request from cm: {}", keyValue("Add Care context response", requestEntity.getBody()));
        return hipInitLinkResponseOrchestrator.processResponse(requestEntity, X_HIP_ID)
                .subscriberContext(context -> context.put(API_CALLED, PATH_ON_ADD_CARE_CONTEXTS));
    }
}