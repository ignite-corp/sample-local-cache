package co.kr.ignite.samplelocalcache.cache;

import org.springframework.lang.NonNull;

public interface LocalCache {
    void load();

    @NonNull
    CacheName getCacheName();

    enum CacheName {
        BANNER
    }
}
