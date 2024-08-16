package co.kr.ignite.samplelocalcache.cache.controller;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import co.kr.ignite.samplelocalcache.cache.LocalCache;

public class RefreshingCacheMessageListener implements MessageListener {
    private final Map<LocalCache.CacheName, LocalCache> cacheMap;

    public RefreshingCacheMessageListener(List<LocalCache> localCaches) {
        this.cacheMap = localCaches.stream().collect(Collectors.toMap(LocalCache::getCacheName, Function.identity()));
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String cacheNameValue = new String(message.getBody(), StandardCharsets.UTF_8);
        LocalCache localCache = cacheMap.get(LocalCache.CacheName.valueOf(cacheNameValue));
        localCache.load();
    }
}
