package com.karim.igniteBasis.igniteConnectCfg;

import com.karim.igniteBasis.define.TableDefine;
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

    public void igniteCfg(){

        if (TableDefine.CLIENT_CLUSTER_MODE){
            // 접속정보
            cfg = new ClientConfiguration()
                    .setAddresses("192.168.124.238:10800","192.168.124.244:10800","192.168.124.249:10800")
                    .setPartitionAwarenessEnabled(true);

            ignite = Ignition.startClient(cfg);

            ClientCluster clientCluster = ignite.cluster();
            clientCluster.state(ClusterState.ACTIVE);

        }else {
            // 접속정보
            cfg = new ClientConfiguration()
                    .setAddresses("192.168.124.250:10800")
                    .setPartitionAwarenessEnabled(true);

            ignite = Ignition.startClient(cfg);
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
