package com.karim.igniteBasis.normalMode.igniteConnectCfg;

import com.karim.igniteBasis.utils.propertiesUtils;
import org.apache.ignite.Ignition;
import org.apache.ignite.client.ClientCache;
import org.apache.ignite.client.ClientCluster;
import org.apache.ignite.client.IgniteClient;
import org.apache.ignite.cluster.ClusterState;
import org.apache.ignite.configuration.ClientConfiguration;

/**
 * Created by sblim
 * Date : 2021-11-17
 * Time : 오후 2:16
 */
public class IgniteLifeCycle {

    ClientConfiguration cfg;
    IgniteClient ignite;

    String[] url;
    String jdbcClusterUrl = "jdbc:ignite:thin://192.168.124.238:10800,192.168.124.244:10800,192.168.124.249:10800/PUBLIC";
    String jdbcSingleUrl = "jdbc:ignite:thin://192.168.124.250:10800/PUBLIC";

    public void igniteCfg(){

        url = propertiesUtils.getStringArray(propertiesUtils.urlParsing(jdbcSingleUrl));

        // 접속정보
        cfg = new ClientConfiguration()
                .setAddresses(url)
                .setPartitionAwarenessEnabled(true);

        ignite = Ignition.startClient(cfg);

        // url이 여러개면 cluster -> cluster 모드로
        if (url.length > 1){
            ClientCluster clientCluster = ignite.cluster();
            clientCluster.state(ClusterState.ACTIVE);

        }
    }

    public ClientCache IgniteConnect(){
        igniteCfg();
        // 스키마설정
        ClientCache<Object, Object> cache = ignite.getOrCreateCache("PUBLIC");

        return cache;
    }

    public void IgniteClose(){
        igniteCfg();
        try {
            ignite.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
