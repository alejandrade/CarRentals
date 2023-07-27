package com.techisgood.carrentals.global;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CacheService {

    private final CacheManager cacheManager;

    public void evictUserCache(String key) {
        Cache usersCache = cacheManager.getCache("users");
        if (usersCache != null) {
            usersCache.evict(key);
        }
    }
}
