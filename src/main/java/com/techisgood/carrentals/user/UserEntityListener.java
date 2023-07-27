package com.techisgood.carrentals.user;

import com.techisgood.carrentals.global.ApplicationContextProvider;
import com.techisgood.carrentals.global.CacheService;
import com.techisgood.carrentals.model.DbUser;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import org.springframework.context.ApplicationContext;

public class UserEntityListener {

    @PostPersist
    @PostUpdate
    @PostRemove
    public void onUserChange(DbUser dbUser) {
        // Get the Spring context
        ApplicationContext context = ApplicationContextProvider.getApplicationContext();

        // Get the cache service bean
        CacheService cacheService = context.getBean(CacheService.class);

        // Evict cache using the cache service
        cacheService.evictUserCache(dbUser.getId());
    }
}

