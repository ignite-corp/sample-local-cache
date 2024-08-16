package co.kr.ignite.samplelocalcache.cache.controller;

import static co.kr.ignite.samplelocalcache.config.RedisLocalCacheRefreshAutoConfiguration.*;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.kr.ignite.samplelocalcache.cache.LocalCache;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/caches")
@RestController
public class CacheController {
    private final RedisTemplate<String, String> redisTemplate;

    @RequestMapping("/{cacheName}/refresh")
    public void refresh(@PathVariable LocalCache.CacheName cacheName) {
        redisTemplate.convertAndSend(REFRESHING_CACHE_TOPIC_NAME, cacheName.name());
    }
}