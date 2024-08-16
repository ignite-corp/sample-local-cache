package co.kr.ignite.samplelocalcache.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import co.kr.ignite.samplelocalcache.cache.LocalCache;
import co.kr.ignite.samplelocalcache.cache.controller.RefreshingCacheMessageListener;

@Configuration
public class RedisLocalCacheRefreshAutoConfiguration {
    public static final String REFRESHING_CACHE_TOPIC_NAME = "refreshing-cache";

    @Bean
    public RedisMessageListenerContainer redisMessageListener(RedisConnectionFactory connectionFactory, List<LocalCache> caches) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(refreshingCacheMessageListener(caches), redisLocalRefreshChannel());
        return container;
    }

    @Bean
    public ChannelTopic redisLocalRefreshChannel() {
        return new ChannelTopic(REFRESHING_CACHE_TOPIC_NAME);
    }

    @Bean
    public RefreshingCacheMessageListener refreshingCacheMessageListener(List<LocalCache> caches) {
        return new RefreshingCacheMessageListener(caches);
    }
}