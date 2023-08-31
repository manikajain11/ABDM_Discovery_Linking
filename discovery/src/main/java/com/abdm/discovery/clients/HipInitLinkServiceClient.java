package com.abdm.discovery.clients;

import com.abdm.discovery.common.Constants;
import com.abdm.discovery.common.IdentityService;
import com.abdm.discovery.common.cache.ServiceOptions;
import com.abdm.discovery.registry.BridgeRegistry;
import com.abdm.discovery.registry.CMRegistry;
import com.abdm.discovery.registry.ServiceType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class HipInitLinkServiceClient extends ServiceClient{

    private final CMRegistry cmRegistry;
    private final BridgeRegistry bridgeRegistry;

    public HipInitLinkServiceClient(ServiceOptions serviceOptions,
                                   WebClient.Builder webClientBuilder,
                                   IdentityService identityService,
                                   CMRegistry cmRegistry,
                                   BridgeRegistry bridgeRegistry) {
        super(serviceOptions, webClientBuilder, identityService);
        this.cmRegistry = cmRegistry;
        this.bridgeRegistry = bridgeRegistry;
    }

    @Override
    protected Mono<String> getResponseUrl(String clientId, ServiceType serviceType) {
        return bridgeRegistry.getHostFor(clientId, serviceType).map(host -> host + Constants.PATH_ON_ADD_CARE_CONTEXTS);
    }

    @Override
    protected Mono<String> getRequestUrl(String clientId, ServiceType serviceType) {
        return cmRegistry.getHostFor(clientId).map(host -> host + Constants.PATH_ADD_CARE_CONTEXTS);
    }
}