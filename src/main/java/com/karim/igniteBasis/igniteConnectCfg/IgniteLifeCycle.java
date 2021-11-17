package com.karim.igniteBasis.igniteConnectCfg;

import org.apache.ignite.Ignition;
import org.apache.ignite.client.ClientCache;
import org.apache.ignite.client.IgniteClient;
import org.apache.ignite.configuration.ClientConfiguration;

/**
 * Created by sblim
 * Date : 2021-11-17
 * Time : 오후 2:16
 */
public class IgniteLifeCycle {

    // 접속정보
    ClientConfiguration cfg = new ClientConfiguration()
            .setAddresses("192.168.124.250:10800")
            .setPartitionAwarenessEnabled(true);

    IgniteClient ignite = Ignition.startClient(cfg);

    public ClientCache IgniteConnect(){
        // 스키마설정
        ClientCache<Object, Object> cache = ignite.getOrCreateCache("PUBLIC");

        return cache;
    }

    public void IgniteClose(){
        try {
            ignite.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
