# 한성맛남 백엔드 프로젝트
2023년 한성대학교 컴퓨터공학부 캡스톤 4팀 부기스쿼드의 백엔드 프로젝트입니다.
 
## 개요 
혼밥러 탈출을 위한 매칭 & 키오스크 서비스  

### 포스터   
<img src="https://github.com/BugiSquad/HaksikMatnam_Frontend/blob/master/src/images/Poster.jpg?raw=true" width="50%" height="50%"/>

### 구성도   
<img src="./ProjectFlow.png" width="50%" height="50%"/>

## 서비스 개발 배경 및 목적
- 코로나 이후 학우들간 교류가 감소하여 혼자 밥을 먹는 학우들이 증가하였습니다. 저희 팀은 이러한 문제점을 개선하고 기존 불편했던 학식당 어플을 보완하여 학우들간 가볍게 밥 한끼를 먹을 수 있도록 하는 프로젝트 입니다.

서버 백엔드에 사용된 기술은 스프링을 기반으로 스프링 데이터 JPA, QueryDSL 등이 사용되었고 MYSQL DB, 사용자 인증으로 JWT를 사용하였고 서버는 AWS의 EC2 인스턴스를 기본으로 이미지 저장을 위한 S3, 알림 서비스를 위한 HTTPS 업그레이드와 고정 도메인 연결 후 각 브라우저 사의 PUSH SERVER와 연동 등이 사용되었습니다.
 
## 참고사항
aws 키 관련 설정을 공개할 수 없는 관계로 가장 마지막 커밋만 이 리포지토리에 올렸습니다.

## 사용한 라이브러리

### 스프링부트 관련
[spring-boot-starter-data-jpa]( https://search.maven.org/artifact/org.springframework.boot/spring-boot-starter-data-jpa)  
[spring-boot-starter-web]( https://search.maven.org/artifact/org.springframework.boot/spring-boot-starter-web)  
[lombok]( https://projectlombok.org/)  
[h2]( https://www.h2database.com/html/main.html)  
[mysql-connector-j]( https://dev.mysql.com/doc/connector-j/8.0/en/)  
[spring-boot-starter-thymeleaf]( https://search.maven.org/artifact/org.springframework.boot/spring-boot-starter-thymeleaf)  
[spring-boot-devtools]( https://search.maven.org/artifact/org.springframework.boot/spring-boot-devtools)  
[thymeleaf-layout-dialect]( https://github.com/ultraq/thymeleaf-layout-dialect)

### JWT인증
[spring-boot-starter-security]( https://search.maven.org/artifact/org.springframework.boot/spring-boot-starter-security)  
[jjwt]( https://github.com/jwtk/jjwt)  
[jakarta.xml.bind-api]( https://search.maven.org/artifact/jakarta.xml.bind/jakarta.xml.bind-api)  
[jaxb-api]( https://search.maven.org/artifact/javax.xml.bind/jaxb-api)  

## AWS S3
[spring-cloud-starter-aws]( https://search.maven.org/artifact/org.springframework.cloud/spring-cloud-starter-aws)  
[bcprov-jdk15on]( https://www.bouncycastle.org/java.html)  

### 웹 푸시
[web-push]( https://github.com/web-push-libs/web-push-java)  

### ORM
[querydsl-jpa]( https://search.maven.org/artifact/com.querydsl/querydsl-jpa)  
[querydsl-apt]( https://search.maven.org/artifact/com.querydsl/querydsl-apt)  
[jakarta.annotation-api]( https://search.maven.org/artifact/jakarta.annotation/jakarta.annotation-api)  
[jakarta.persistence-api]( https://search.maven.org/artifact/jakarta.persistence/jakarta.persistence-api)  

### 트랜젝션
[spring-tx]( https://search.maven.org/artifact/org.springframework/spring-tx)  

### 폼 검증
[validation-api]( https://search.maven.org/artifact/javax.validation/validation-api)  
[hibernate-validator]( https://hibernate.org/validator/)  

