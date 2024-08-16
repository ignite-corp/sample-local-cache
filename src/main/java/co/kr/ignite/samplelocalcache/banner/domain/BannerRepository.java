package co.kr.ignite.samplelocalcache.banner.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import co.kr.ignite.samplelocalcache.cache.LocalCache;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class BannerRepository implements LocalCache {
    List<Banner> cache = null;

    public List<Banner> findAllDisplayableBanners() {
        LocalDateTime now = LocalDateTime.now();
        return cache.stream().filter(banner -> banner.isDisplayable(now)).collect(Collectors.toList());
    }

    @EventListener(ApplicationStartedEvent.class)
    @Scheduled(cron = "0 * * * * *")
    public void load() {
        log.info("reloaded cache: {}", getCacheName());
        cache = Banner.createSamples();
    }

    @NonNull
    @Override
    public CacheName getCacheName() {
        return CacheName.BANNER;
    }
}
