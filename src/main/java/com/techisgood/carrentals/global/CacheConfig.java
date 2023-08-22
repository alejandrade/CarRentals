package com.techisgood.carrentals.global;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

@Configuration
@RequiredArgsConstructor
//@EnableCaching
public class CacheConfig implements CachingConfigurer {

//    private final RedisConnectionFactory redisConnectionFactory;
//
//    @Bean
//    @Override
//    public CacheManager cacheManager() {
//        return RedisCacheManager.builder(redisConnectionFactory).build();
//    }
}

