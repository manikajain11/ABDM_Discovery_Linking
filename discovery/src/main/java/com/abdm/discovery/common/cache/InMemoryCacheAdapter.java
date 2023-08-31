package com.abdm.discovery.common.cache;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryCacheAdapter<K, V> implements CacheAdapter<K, V> {

    private final Map<K, V> cache = new HashMap<>();

    @Override
    public Mono<V> get(K key) {
        return Mono.justOrEmpty(cache.get(key));
    }

    @Override
    public Mono<Void> put(K key, V value) {
        cache.put(key, value);
        return Mono.empty();
    }

    @Override
    public Mono<Void> invalidate(K key) {
        cache.remove(key);
        return Mono.empty();
    }
}