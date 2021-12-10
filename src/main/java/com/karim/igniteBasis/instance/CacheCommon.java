package com.karim.igniteBasis.instance;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by sblim
 * Date : 2021-12-09
 * Time : 오후 5:36
 */
public class CacheCommon {

    /**
     * 유일 인스턴스 개체
     */
    private volatile static CacheCommon _instance;
    private ConcurrentMap<String, String> cacheMap = null;
    private Cache<String, String> cache = null;
    // ---------------------------------------------------------------

    private CacheCommon() {
        cache = CacheBuilder.newBuilder()
                .expireAfterWrite(10000, TimeUnit.MILLISECONDS)
                .build();
        cache.invalidateAll();
        cacheMap = cache.asMap();
        ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
        ses.scheduleWithFixedDelay(
                new Runnable() {
                    public void run() {
                        cache.cleanUp();
                    }
                }, 0, 1000, TimeUnit.MILLISECONDS);
    }
    /**
     * 유일 인스턴스 개체 생성 및 반환
     * @return 유일 인스턴스 개체
     */
    public static CacheCommon getInstance() {
        if( _instance == null ) {
            /* 제일 처음에만 동기화 하도록 함 */
            synchronized(CacheCommon.class) {
                if( _instance == null ) {
                    _instance = new CacheCommon();
                }
            }
        }
        return _instance;
    }

    public void put(String key, String data) {
        if(key != null)
            this.cacheMap.put(key, data);
    }

    public void remove(String key) {
        if(key != null)
            this.cacheMap.remove(key);
    }

    public int getSize() {
        //cache.cleanUp();
        return this.cacheMap.size();
    }

    public int getTpsSize() {
        return this.cacheMap.size() / 10;
    }
}
