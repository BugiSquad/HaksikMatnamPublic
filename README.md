# HaksikMatnam - release

BugiSquad - HaksikMatnam Capstone project - Backend

혼밥러 탈출을 위한 매칭 & 키오스크 서비스

# 서비스 개발 배경 및 목적
- 코로나 이후 학우들간 교류가 감소하여 혼자 밥을 먹는 학우들이 증가하였습니다. 저희 팀은 이러한 문제점을 개선하고 기존 불편했던 학식당 어플을 보완하여 학우들간 가볍게 밥 한끼를 먹을 수 있도록 하는 프로젝트 입니다.

# 사용 기술
서버 부분
- AWS EC2, S3
- 알림 서비스를 위한 각 브라우저 사의 PUSH SERVER 구독

백엔드 부분
- Spring Framework, SpringBoot
- JPA, Spring Data JPA, QueryDSL
- JWT
- nl.martijndwars:web-push:5.1.1 (web push notificatoin)
- org.bouncycastle:bcprov-jdk15on:1.70 (cryptographic algorithms)
 
- 작업한 커밋이 기록되어 있는 repository는 key 들이 작성되어 있는 application.yml 파일이 기록되어 있어 public repository로 분리.
