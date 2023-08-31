package com.abdm.discovery.clients;

import com.abdm.discovery.common.Constants;
import com.abdm.discovery.common.IdentityService;
import com.abdm.discovery.common.cache.ServiceOptions;
import com.abdm.discovery.registry.BridgeRegistry;
import com.abdm.discovery.registry.CMRegistry;
import com.abdm.discovery.registry.ServiceType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class LinkConfirmServiceClient extends ServiceClient {
    private final CMRegistry cmRegistry;
    private final BridgeRegistry bridgeRegistry;

    public LinkConfirmServiceClient(WebClient.Builder webClientBuilder,
                                    ServiceOptions serviceOptions,
                                    IdentityService identityService,
                                    CMRegistry cmRegistry,
                                    BridgeRegistry bridgeRegistry) {
        super(serviceOptions, webClientBuilder, identityService);
        this.cmRegistry = cmRegistry;
        this.bridgeRegistry = bridgeRegistry;
    }

    @Override
    protected Mono<String> getResponseUrl(String clientId, ServiceType serviceType) {
        return cmRegistry.getHostFor(clientId).map(host -> host+Constants.PATH_LINK_ON_CONFIRM);
    }

    @Override
    protected Mono<String> getRequestUrl(String clientId, ServiceType serviceType) {
        return bridgeRegistry.getHostFor(clientId, serviceType).map(host -> host+Constants.PATH_LINK_CONFIRM);
    }
}