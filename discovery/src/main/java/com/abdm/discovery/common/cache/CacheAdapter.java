package com.abdm.discovery.common.cache;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
@Component
public interface CacheAdapter<K, V> {
    Mono<V> get(K key);

    Mono<Void> put(K key, V value);

    Mono<Void> invalidate(K key);
}