# 📃 ignite 기본 코드들
```
>> ignite gradle version
ignite : 2.16.0

>> 구축 ignite version
single-ignite : 2.16.0
cluster-ignite : 2.16.0
```

## ❓ ignite 동작에 대한 기본 기능
ignite의 기본 동작에 대해 pkg별로 구현한 프로젝트입니다.

## ✔️Pakage 별 정리

## 1. utils, logger
- 기본 기능을 제공하는 부속 기능 및 유틸 모음

## 2. define
- 공통 정의 변수 모음

## 3. normalMode (Thin Client 로 구현)

3-1. igniteConnectCfg
- ignite 접속 및 종료 기능 구현

3-2. igniteSqlCommand
- 자체 캐쉬가 아닌, 서버단에 개별 설치된 ignite가 IMDB 형태 일 때, sql command 구현
> mode = Thread or normal

3-3. IgniteJdbcSelect
- ignite jdbc 구현

## 4. clientMode (Thick Client 로 구현)
> 서버단에 개별 설치된 ignite에 클러스터 처럼 연결하는 모드 따라서 Cluster 모드여야 함

4-1. igniteConnectClientModeCfg
- ignite 자바 Thick Client 구현

## 5. instance
- tps 측정 하기 위한 cacheCommon -> Google.Guava 이용하여 구현  
- 데이터가 들어오는 곳에서 해당 인스턴스에 put 하면 됨
```java
CacheCommon.getInstance().put(UUID.randomUUID().toString(), "");
```
- tps 모니터를 확인 하기 위해서는 해당 인스턴스에 getTpsSize 를 스케줄러로 뿌려주면 됨
```java
logger.info("{} => {}" ,"[IGNITE_BASIS_TPS_MONITOR]", CacheCommon.getInstance().getTpsSize());
```

## 6. localCacheMode
- 서버 단 Local Cache 로 싱글 및 클러스터 구현
- cache 작업 시 이벤트 리스터 구현