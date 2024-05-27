package com.karim.igniteBasis.localCacheMode.server;

import com.karim.igniteBasis.localCacheMode.event.cluster.LocalListener;
import com.karim.igniteBasis.localCacheMode.event.cluster.RemoteListener;
import com.karim.igniteBasis.localCacheMode.event.single.SingleCacheEntryListener;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.cluster.ClusterState;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.DataStorageConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.events.EventType;
import org.apache.ignite.spi.communication.tcp.TcpCommunicationSpi;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.cache.Cache;
import javax.cache.expiry.EternalExpiryPolicy;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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

    private final static Logger logger = LoggerFactory.getLogger(IgniteServer.class);
    private static final boolean SINGLE_MODE = false;
    private static final boolean PERSISTENCE_MODE  = true;
    private final static String CACHE_NAME = "karim-cache";
    private final static int CACHE_TTL_MILLISECOND = 10000;
    private final static List<String> IGNITE_CONNECT_ADDRESS = Arrays.asList("127.0.0.1:47500", "127.0.0.1:47501");
    private final static String PERSISTENCE_STORE_PATH = "../karim-storage";
    private final static String INSTANCE_NAME = "KARIM_INSTANCE";
    private final static String STORE_GROUP_NAME = "STORE_KARIM_GROUP";
    private final static int[] EVENT_LISTENER_USE_TYPES = { EventType.EVT_CACHE_OBJECT_PUT, // 데이터가 캐시에 추가될 때 발생하는 이벤트
                                                            EventType.EVT_CACHE_OBJECT_READ, // 캐시에서 데이터를 읽을 때 발생하는 이벤트
                                                            EventType.EVT_CACHE_OBJECT_REMOVED, // 캐시에서 데이터가 제거될 때 발생하는 이벤트
                                                            EventType.EVT_CACHE_OBJECT_EXPIRED, // 캐시에서 데이터가 만료되어 제거될 때 발생하는 이벤트
                                                            EventType.EVT_NODE_JOINED, // 새로운 노드가 클러스터에 가입되었을 때 발생하는 이벤트
                                                            EventType.EVT_CLIENT_NODE_DISCONNECTED, // 클라이언트 노드가 클러스터에서 연결이 끊겼을 때 발생하는 이벤트
                                                            EventType.EVT_CLIENT_NODE_RECONNECTED, // 클라이언트 노드가 클러스터에 재연결되었을 때 발생하는 이벤트
                                                            EventType.EVT_CACHE_ENTRY_EVICTED, // 캐시에서 엔트리가 제거되었을 때 발생하는 이벤트
                                                            EventType.EVT_NODE_LEFT // 노드가 클러스터에서 탈퇴할 때 발생하는 이벤트
                                                            };
    private static volatile IgniteServer _instance = null;
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
        igniteCfg.setIncludeEventTypes(EVENT_LISTENER_USE_TYPES);

        // 캐시 설정
        CacheConfiguration<Integer, String> cacheCfg = new CacheConfiguration<>(CACHE_NAME);

        // TTL 설정
//        cacheCfg.setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(new Duration(TimeUnit.MILLISECONDS, CACHE_TTL_MILLISECOND)));
        cacheCfg.setExpiryPolicyFactory(EternalExpiryPolicy.factoryOf());
        cacheCfg.setEagerTtl(true);

        igniteCfg.setCacheConfiguration(cacheCfg);

        if (PERSISTENCE_MODE){
            //data storage configuration
            DataStorageConfiguration storageCfg = new DataStorageConfiguration();
            // 영구 저장소 활성화
            /*
               *분리된 환경 유지: 서로 다른 업무 부서 또는 서비스 간에 독립된 환경을 유지하고 싶을 때 클러스터 그룹을 사용합니다. 각 그룹은 자체적인 구성 및 정책을 가지고 있으며, 서로 간섭하지 않고 독립적으로 운영될 수 있습니다.
               *리소스 격리: 각 클러스터 그룹은 자체적인 리소스 할당과 관리를 수행할 수 있습니다. 이를 통해 서로 다른 그룹 간의 리소스 경합을 방지하고, 각 그룹이 필요한 만큼의 리소스를 독점적으로 사용할 수 있습니다.
               *보안: 각 그룹은 독립적인 보안 정책을 적용할 수 있습니다. 서로 다른 그룹 간의 데이터 접근 권한을 구분하여 보안을 강화할 수 있습니다.
               *스케일 아웃: 클러스터 그룹을 사용하면 애플리케이션의 요구에 따라 동적으로 그룹을 추가하거나 확장할 수 있습니다. 이를 통해 시스템의 확장성을 향상시킬 수 있습니다.
               *작업 분할: 서로 다른 그룹 간에 작업을 분할하여 병렬 처리를 촉진할 수 있습니다. 각 그룹은 서로 독립적으로 작업을 처리하고, 전체 시스템의 처리량을 향상시킬 수 있습니다.
               */
            storageCfg.getDefaultDataRegionConfiguration().setPersistenceEnabled(PERSISTENCE_MODE);
            // 저장 경로
            storageCfg.setStoragePath(PERSISTENCE_STORE_PATH);

            igniteCfg.setDataStorageConfiguration(storageCfg);
        }

        // 인스턴스 이름 설정
        igniteCfg.setIgniteInstanceName(INSTANCE_NAME);

        if (SINGLE_MODE) {

            // ignite cache single 설정
            singleSetting(igniteCfg);

        } else {

            // ignite cache cluster 설정
            clusterSetting(igniteCfg, cacheCfg, CacheMode.REPLICATED);
        }

        if (PERSISTENCE_MODE){

            // 클러스터의 영구 저장소를 활성화
            assert ignite != null;
            ignite.cluster().state(ClusterState.ACTIVE);

            //인 메모리 클러스터가 있는 경우 기본적으로 서버 노드가 클러스터를 떠나거나 클러스터에 합류할 때 기본 토폴로지가 자동으로 변경되므로 전환이 투명해야 합니다.
//            ignite.cluster().baselineAutoAdjustEnabled(true);
        }

        // 캐시 생성
        assert ignite != null;
        igniteCache = ignite.getOrCreateCache(CACHE_NAME);
    }

    private void singleSetting(IgniteConfiguration igniteCfg) {

        // ignite 서버 시작
        ignite = Ignition.start(igniteCfg);

        // 해당 캐시 이벤트 리스너 등록
        ignite.events().localListen(new SingleCacheEntryListener(), EVENT_LISTENER_USE_TYPES );
    }

    // 참고 링크 : https://ignite.apache.org/docs/latest/clustering/tcp-ip-discovery
    private void clusterSetting(IgniteConfiguration igniteCfg, CacheConfiguration<Integer, String> cacheCfg, CacheMode mode) {

        // 노드가 서로를 발견하고 클러스터가 형성된 후 노드는 통신 SPI(TCP/IP 프로토콜)를 통해 메시지를 교환
        TcpCommunicationSpi tcpCommunicationSpi = new TcpCommunicationSpi();
        // 각 노드는 다른 노드가 연결되어 메시지를 보내는 로컬 통신 포트와 주소를 엽니다. 시작 시 노드는 지정된 통신 포트(기본값은 47100)에 바인드를 시도합니다.
        tcpCommunicationSpi.setLocalPort(47100); // 노드가 통신에 사용하는 로컬
        igniteCfg.setCommunicationSpi(tcpCommunicationSpi);

        // 클러스터 노드 발견을 위한 설정
        TcpDiscoverySpi tcpDiscoverySpi = new TcpDiscoverySpi();
        TcpDiscoveryVmIpFinder tcpDiscoveryVmIpFinder = new TcpDiscoveryVmIpFinder();

        // 서버의 IP와 두 인스턴스의 디스커버리 포트를 명시합니다.
        // 디스커버리 포트(Discovery Port)는 Apache Ignite 클러스터의 멤버가 서로 통신하고 상호간에 발견(discovery)되는 데 사용되는 포트
        // 클러스터 노드가 시작될 때, 이 포트를 통해 노드들은 서로를 찾아서 클러스터에 가입하게 됩니다.
        tcpDiscoveryVmIpFinder.setAddresses(IGNITE_CONNECT_ADDRESS);
        tcpDiscoverySpi.setIpFinder(tcpDiscoveryVmIpFinder);
        igniteCfg.setDiscoverySpi(tcpDiscoverySpi);

        // cache 저장 모드 ( 복제, 파티션 )
        cacheCfg.setCacheMode(mode);

        // 클러스터 상태를 ACTIVE로 설정
        igniteCfg.setActiveOnStart(true);

        // Group the cache belongs to.
        cacheCfg.setGroupName(STORE_GROUP_NAME);

        // ignite 서버 시작
        ignite = Ignition.start(igniteCfg);

        // 해당 캐시 이벤트 리스너 등록
        ignite.events().remoteListen(new LocalListener(), new RemoteListener(), EVENT_LISTENER_USE_TYPES);

        // 클러스터를 활성화합니다.
        ignite.cluster().active(true);

        // 현재 클러스터의 모든 노드를 가져옴
        Collection<ClusterNode> baselineNodes = ignite.cluster().nodes();

        // 가져온 노드들을 기반으로 Baseline Topology를 설정
        // Baseline Topology는 클러스터의 데이터 분산 및 복제를 위한 기준이 되는 노드들의 집합
        // Baseline Topology는 클러스터의 데이터 저장소의 기준이 되는 노드들의 집합
        // 클러스터에 새로운 노드를 추가하거나 기존 노드를 제거할 때, Baseline Topology는 클러스터의 데이터 분산 및 복제를 위해 기준이 되는 노드들을 정의.
        ignite.cluster().setBaselineTopology(baselineNodes);

    }

    public void putDate(String key, String value) {

        // 캐시에 데이터 삽입
        igniteCache.put(key, value);
    }

    public void getDate(String key) {

        // 캐시에서 데이터 검색
        igniteCache.get(key);
    }

    public void removeDate(String key) {

        // 캐시에서 데이터 삭제
        igniteCache.remove(key);
    }

    public void clearDate(String key) {

        // 캐시에서 데이터 초기화
        igniteCache.clear(key);
    }

    public void getSize() {

        // 캐시에서 데이터 사이즈
        igniteCache.size();
        logger.info( "sblim get size = {}", igniteCache.size());

    }

    public void getAllData(){

        // Cache.Entry 인터페이스는 이벤트 리스너에 직접적으로 연결되지 않습니다.
        // 이벤트 리스너는 캐시의 상태 변경에 대한 이벤트를 수신하고 처리하는 데 사용
        // Cache.Entry는 이러한 이벤트의 콘텐츠를 나타내는 것이 아니라, 캐시의 각 항목을 표현하는 인터페이스
        for (Cache.Entry<Object, Object> entry : igniteCache) {
            logger.info( "sblim get All Data key = {}, value = {}", entry.getKey(), entry.getValue());
        }
    }
}
