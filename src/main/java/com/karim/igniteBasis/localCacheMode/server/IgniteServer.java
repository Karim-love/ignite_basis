package com.karim.igniteBasis.localCacheMode.server;

import com.karim.igniteBasis.localCacheMode.cluster.LocalListener;
import com.karim.igniteBasis.localCacheMode.cluster.RemoteListener;
import com.karim.igniteBasis.localCacheMode.single.SingleCacheEntryListener;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.events.EventType;
import org.apache.ignite.spi.communication.tcp.TcpCommunicationSpi;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;

import java.util.Arrays;

import static org.apache.ignite.events.EventType.*;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : org.example.ignite.server
 * @name : ignite_karim
 * @date : 2024. 04. 26. 026 오후 7:39
 * @modifyed :
 * @description :
 **/
public class IgniteServer {
    private static final boolean singleCache = false;
    private static volatile IgniteServer _instance = null;
    private final static String CACHE_NAME = "SUBIN-Cache";

    private Ignite ignite;
    private IgniteCache<Object, Object> igniteCache;

    public static IgniteServer getInstance() {

        if (_instance == null) {
            /* 제일 처음에만 동기화 하도록 함 */
            synchronized (IgniteServer.class) {
                if (_instance == null) {
                    _instance = new IgniteServer();
                }
            }
        }
        return _instance;
    }

    private IgniteServer() {
        init();
    }

    private void init() {

        // Ignite 설정
        IgniteConfiguration igniteCfg = new IgniteConfiguration();
        // 사용할 이벤트 타입 적용
        igniteCfg.setIncludeEventTypes(EventType.EVT_CACHE_OBJECT_PUT, EventType.EVT_CACHE_OBJECT_READ,
                EventType.EVT_CACHE_OBJECT_REMOVED, EventType.EVT_NODE_JOINED, EventType.EVT_NODE_LEFT);

        // 캐시 설정
        CacheConfiguration<Integer, String> cacheCfg = new CacheConfiguration<>(CACHE_NAME);

        igniteCfg.setCacheConfiguration(cacheCfg);

        if (singleCache) {

            // ig 싱글 설정
            singleSetting(igniteCfg);

        } else {

            // ignite cache cluster 설정
            clusterSetting(igniteCfg, cacheCfg, CacheMode.REPLICATED);
        }

        // 캐시 생성
        igniteCache = ignite.getOrCreateCache(CACHE_NAME);
    }

    private void singleSetting(IgniteConfiguration igniteCfg) {

        // ignite 서버 시작
        ignite = Ignition.start(igniteCfg);

        // 해당 캐시 이벤트 리스너 등록
        ignite.events().localListen(new SingleCacheEntryListener(), EventType.EVT_CACHE_OBJECT_PUT, EventType.EVT_CACHE_OBJECT_READ);
    }

    private void clusterSetting(IgniteConfiguration igniteCfg, CacheConfiguration<Integer, String> cacheCfg, CacheMode mode) {

        // 포트 설정
        TcpCommunicationSpi tcpCommunicationSpi = new TcpCommunicationSpi();
        tcpCommunicationSpi.setLocalPort(47100); // 통신 포트 설정
        igniteCfg.setCommunicationSpi(tcpCommunicationSpi);

        // 클러스터 노드 발견을 위한 설정
        TcpDiscoverySpi tcpDiscoverySpi = new TcpDiscoverySpi();
        TcpDiscoveryVmIpFinder tcpDiscoveryVmIpFinder = new TcpDiscoveryVmIpFinder();

        // 서버의 IP와 두 인스턴스의 디스커버리 포트를 명시합니다.
        tcpDiscoveryVmIpFinder.setAddresses(Arrays.asList("127.0.0.1:47500", "127.0.0.1:47501"));
        tcpDiscoverySpi.setIpFinder(tcpDiscoveryVmIpFinder);
        igniteCfg.setDiscoverySpi(tcpDiscoverySpi);

        // cache 저장 모드 ( 복제, 파티션 )
        cacheCfg.setCacheMode(mode);

        // ignite 서버 시작
        ignite = Ignition.start(igniteCfg);

        // 해당 캐시 이벤트 리스너 등록
        ignite.events().remoteListen(new LocalListener(), new RemoteListener(), EVT_CACHE_OBJECT_PUT, EVT_CACHE_OBJECT_READ, EVT_CACHE_OBJECT_REMOVED);
    }

    public void putDate() {

        // 캐시에 데이터 삽입
        igniteCache.put(1, "Hello");
        igniteCache.put(2, "World");
    }

    public void getDate() {

        // 캐시에서 데이터 검색
        System.out.println("get value for key = " + igniteCache.get(1));
        System.out.println("get value for key = " + igniteCache.get(2));
    }
}
