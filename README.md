# 📃 ignite 기본 코드들
```
>> ignite gradle version
ignite : 2.11.0

>> 구축 ignite version
single-ignite : 2.11.0
cluster-ignite : 2.11.0
```

## ❓ ignite 동작에 대한 기본 기능
ignite의 기본 동작에 대해 pkg별로 구현한 프로젝트입니다.

## ✔️Pakage 별 정리

## 1. utils, logger
- 기본 기능을 제공하는 부속 기능 및 유틸 모음

## 2. define
- 공통 정의 변수 모음

## 3. igniteConnectCfg
- ignite 접속 및 종료 기능 구현

## 4. igniteSqlCommand
- 자체 캐쉬가 아닌, 서버단에 개별 설치된 ignite가 IMDB 형태 일 때, sql command 구현
> mode = Thread or normal