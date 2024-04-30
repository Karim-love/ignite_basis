package com.karim.igniteBasis.localCacheMode.cluster;

import org.apache.ignite.events.CacheEvent;
import org.apache.ignite.events.EventType;
import org.apache.ignite.lang.IgnitePredicate;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : org.example.ignite.cluster
 * @name : ignite_karim
 * @date : 2024. 04. 30. 030 오전 10:53
 * @modifyed :
 * @description :
 **/
public class RemoteListener implements IgnitePredicate<CacheEvent> {

    @Override
    public boolean apply(CacheEvent event) {

        if ( event.type() == EventType.EVT_CACHE_OBJECT_PUT){
            System.out.println("sblim remote server data put data = " +  event.newValue() );
        }

        if ( event.type() == EventType.EVT_CACHE_OBJECT_READ) {
            System.out.println("sblim remote server data get data = " + event.newValue());
        }

        return true;
    }
}