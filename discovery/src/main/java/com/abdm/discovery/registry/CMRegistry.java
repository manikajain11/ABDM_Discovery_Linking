package com.abdm.discovery.registry;

import com.abdm.discovery.common.MappingRepository;
import com.abdm.discovery.common.cache.CacheAdapter;
import lombok.AllArgsConstructor;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;
@Component
@AllArgsConstructor
public class CMRegistry {
    private final CacheAdapter<String, String> consentManagerMappings;
    private final MappingRepository mappingRepository;

    public Mono<String> getHostFor(String id) {
        return consentManagerMappings.get(id)
                .switchIfEmpty(mappingRepository.cmHost(id)
                        .filter(url -> StringUtils.hasText(url) && UrlUtils.isAbsoluteUrl(url))
                        .flatMap(url -> consentManagerMappings.put(id, url).thenReturn(url)));
    }
}