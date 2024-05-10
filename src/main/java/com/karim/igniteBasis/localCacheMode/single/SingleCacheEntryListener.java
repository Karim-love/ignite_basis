package com.karim.igniteBasis.localCacheMode.single;

import org.apache.ignite.events.CacheEvent;
import org.apache.ignite.events.EventType;
import org.apache.ignite.lang.IgnitePredicate;

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


    @Override
    public boolean apply(CacheEvent event) {

        if ( event.type() == EventType.EVT_CACHE_OBJECT_PUT){
            System.out.println("sblim SingleCacheEntryListener data put data = " +  event.newValue());
        }

        if ( event.type() == EventType.EVT_CACHE_OBJECT_READ) {
            System.out.println("sblim SingleCacheEntryListener data get data = " +  event.newValue());
        }

        // 이벤트 처리 후 계속 리스닝
        return true;
    }
}