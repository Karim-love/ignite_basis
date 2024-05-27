package com.karim.igniteBasis.localCacheMode.event.single;

import org.apache.ignite.events.CacheEvent;
import org.apache.ignite.lang.IgnitePredicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.apache.ignite.events.EventType.*;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : org.example.ignite.listener
 * @name : ignite_karim
 * @date : 2024. 04. 30. 030 오전 9:25
 * @modifyed :
 * @description :
 **/
public class SingleCacheEntryListener implements IgnitePredicate<CacheEvent> {

    private final static Logger logger = LoggerFactory.getLogger(SingleCacheEntryListener.class);

    @Override
    public boolean apply(CacheEvent event) {

        switch (event.type()) {
            case EVT_CACHE_OBJECT_PUT:

                logger.info("sblim SingleCacheEntryListener data put data = {}", event.newValue());
                break;
            case EVT_CACHE_OBJECT_READ:

                logger.info("sblim SingleCacheEntryListener data get data = {}", event.newValue());
                break;
            case EVT_CACHE_OBJECT_EXPIRED:

                logger.info("sblim SingleCacheEntryListener data expire data = {}", event.oldValue());
                break;
            default:

                break;
        }
        // 이벤트 처리 후 계속 리스닝
        return true;
    }
}