package com.karim.igniteBasis.localCacheMode.event.cluster;

import org.apache.ignite.events.CacheEvent;
import org.apache.ignite.events.Event;
import org.apache.ignite.lang.IgniteBiPredicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

import static org.apache.ignite.events.EventType.*;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : org.example.ignite.cluster
 * @name : ignite_karim
 * @date : 2024. 04. 30. 030 오전 11:56
 * @modifyed :
 * @description :
 **/

public class LocalListener implements IgniteBiPredicate<UUID, Event> {

    private final static Logger logger = LoggerFactory.getLogger(LocalListener.class);

    @Override
    public boolean apply(UUID uuid, Event event) {

        switch (event.type()) {
            case EVT_CACHE_OBJECT_PUT:

                logger.info("sblim LocalListener data put data = {}", ((CacheEvent) event).newValue());
                break;
            case EVT_CACHE_OBJECT_READ:

                logger.info("sblim LocalListener data get data = {}", ((CacheEvent) event).newValue());
                break;
            case EVT_CACHE_OBJECT_EXPIRED:

                logger.info("sblim LocalListener data expire data = {}", ((CacheEvent) event).oldValue());
                break;
            default:

                break;
        }
        // 이벤트 처리 후 계속 리스닝
        return true;
    }
}
