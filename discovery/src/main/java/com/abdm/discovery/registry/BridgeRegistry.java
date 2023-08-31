package com.abdm.discovery.registry;

import com.abdm.discovery.common.MappingRepository;
import com.abdm.discovery.common.cache.CacheAdapter;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;


import static com.abdm.discovery.common.Constants.BRIDGE_ID_PREFIX;
@Component
@AllArgsConstructor
public class BridgeRegistry {
    @Autowired
    private final CacheAdapter<String, String> bridgeMappings;
    private final MappingRepository mappingRepository;

    public Mono<String> getHostFor(String id, ServiceType serviceType) {
        return bridgeMappings.get(bridgeMappingKey(id, serviceType))
                .switchIfEmpty((id.startsWith(BRIDGE_ID_PREFIX)
                        ? mappingRepository.bridgeHost(id.substring(BRIDGE_ID_PREFIX.length()))
                        : mappingRepository.bridgeHost(Pair.of(id, serviceType)))
                        .filter(url -> StringUtils.hasText(url) && UrlUtils.isAbsoluteUrl(url))
                        .flatMap(url -> bridgeMappings.put(bridgeMappingKey(id, serviceType), url).thenReturn(url)));
    }

    private String bridgeMappingKey(String id, ServiceType serviceType) {
        return String.join("-", id, serviceType.name());
    }
}