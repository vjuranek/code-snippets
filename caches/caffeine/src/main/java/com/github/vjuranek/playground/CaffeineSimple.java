package com.github.vjuranek.playground;

import java.util.concurrent.TimeUnit;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

public class CaffeineSimple {

    public static void main(String[] args) {
        CaffeineSimple cs = new CaffeineSimple();
        cs.putAndGet();
    }

    public void putAndGet() {
        Cache<String, String> cache = Caffeine.newBuilder().expireAfterWrite(10, TimeUnit.MINUTES).maximumSize(100)
                .build();
        final String key = "key";
        cache.put(key, "value");
        System.out.printf("Getting key %s -> %s\n", key, cache.get(key, (k) -> "newly created value"));
        cache.invalidate(key);
        System.out.printf("Cache size (estimated) after invalidation: %d\n", cache.estimatedSize());
    }
}
