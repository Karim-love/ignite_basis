package com.karim.igniteBasis.localCacheMode.cluster;

import org.apache.ignite.events.CacheEvent;
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

/**
 * 이중화 일 시 local event도 remoteListener에 이벤트로 작용됨
 * 따라서 localListener는 객체만 생성하고 리스닝중으로만 상태 적용
 */
public class LocalListener implements IgniteBiPredicate<UUID, CacheEvent> {

    @Override
    public boolean apply(UUID uuid, CacheEvent event) {

        // 이벤트 처리 후 계속 리스닝
        return true;
    }
}
