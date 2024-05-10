package com.karim.igniteBasis.localCacheMode.cluster;

import org.apache.ignite.events.CacheEvent;
import org.apache.ignite.events.EventType;
import org.apache.ignite.lang.IgniteBiPredicate;

import java.util.UUID;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : org.example.ignite.cluster
 * @name : ignite_karim
 * @date : 2024. 04. 30. 030 오전 11:56
 * @modifyed :
 * @description :
 **/

public class LocalListener implements IgniteBiPredicate<UUID, CacheEvent> {

    @Override
    public boolean apply(UUID uuid, CacheEvent event) {

        if ( event.type() == EventType.EVT_CACHE_OBJECT_PUT){

            System.out.println("sblim LocalListener data put data = " +  event.newValue());
        }

        if ( event.type() == EventType.EVT_CACHE_OBJECT_READ) {
            System.out.println("sblim LocalListener data get data = " +  event.newValue());
        }

        // 이벤트 처리 후 계속 리스닝
        return true;
    }
}
