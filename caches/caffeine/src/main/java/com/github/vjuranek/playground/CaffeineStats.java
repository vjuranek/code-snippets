package com.github.vjuranek.playground;

import java.util.Random;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.stats.CacheStats;

public class CaffeineStats {

    public static void main(String[] args) {
        CaffeineStats cs = new CaffeineStats();
        cs.putAndStats();
    }

    public void putAndStats() {
        final int bound = 1000;
        
        Cache<Integer, Integer> cache = Caffeine.newBuilder().maximumSize(1000).recordStats().build();
        
        //populate cache
        for (int i = 0; i < bound; i++) {
            cache.put(i, i);
        }
        
        //random gets from cache
        Random rand = new Random();
        for (int i = 0; i < bound * 10; i++) {
            if (i % 1000 == 0) System.out.printf("Get %d from cache\n", i);
            cache.get(rand.nextInt(bound * 2), (k) -> {
                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                }
                return -1;
            });
        }
        
        CacheStats stat = cache.stats();
        System.out.println("\n\nStatistics");
        System.out.println("----------");
        System.out.printf("Hit count: %d\n", stat.hitCount());
        System.out.printf("Hit rate: %f\n", stat.hitRate());
        System.out.printf("Miss count: %d\n", stat.missCount());
        System.out.printf("Avg. load penalty: %f\n", stat.averageLoadPenalty());
    }

}
