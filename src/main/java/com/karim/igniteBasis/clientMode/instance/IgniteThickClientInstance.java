package com.karim.igniteBasis.clientMode.instance;

import com.karim.igniteBasis.utils.LoggerUtils;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.eviction.lru.LruEvictionPolicyFactory;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.NearCacheConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by sblim
 * Date : 2021-12-07
 * Time : 오후 5:07
 */
public class IgniteThickClientInstance {

    private final static Logger logger = LoggerFactory.getLogger(IgniteThickClientInstance.class);
    private static volatile IgniteThickClientInstance _instance = null;
    private Ignite ignite;
    private IgniteCache<Object, Object> igniteCache;

    public static IgniteThickClientInstance getInstance() {
        if( _instance == null ) {
            /* 제일 처음에만 동기화 하도록 함 */
            synchronized(IgniteThickClientInstance.class) {
                if( _instance == null ) {
                    _instance = new IgniteThickClientInstance();
                }
            }
        }
        return _instance;
    }

    private IgniteThickClientInstance() {
        init();
    }

    private void init() {
        // Enable client mode locally.
        Ignition.setClientMode(true);

        // 접속정보
        // Start Ignite in client mode.
        ignite = Ignition.start("config/ignite-configuration.xml");

        NearCacheConfiguration<Object, Object> nearCfg = new NearCacheConfiguration<>();
        nearCfg.setNearEvictionPolicyFactory(new LruEvictionPolicyFactory<>(100_000));

        igniteCache = ignite.getOrCreateCache(new CacheConfiguration<>("PUBLIC"), nearCfg);
    }

    public Ignite getIgnite() {
        return ignite;
    }

    public void selectTableAll(){
        List<?> resultList;
        SqlFieldsQuery qry = new SqlFieldsQuery("SELECT * FROM TF_ASSET");

        try {
            // 쿼리값 cache에 있는 데이터 전부 가져오기
            resultList = igniteCache.query(qry).getAll();
            System.out.println(resultList.get(0));
        }catch (Exception e){
            logger.error("{} => {}", "ignite setInsertData exception", LoggerUtils.getStackTrace(e));
        }
    }
}
