# 멋사스네스(MutsaSNS)

- gitlab
    - https://gitlab.com/mooonju/FinalProject_JoMunJu
- swagger
    - http://ec2-54-180-80-117.ap-northeast-2.compute.amazonaws.com:8080/swagger-ui/

## 개발환경
- 에디터 : Intellij Ultimate
- 개발 툴 : SpringBoot 2.7.5
- 자바 : JAVA 11
- 빌드 : Gradle 6.8
- 서버 : AWS EC2
- 배포 : Docker
- 데이터베이스 : MySql 8.0
- 필수 라이브러리 : SpringBoot Web, MySQL, Spring Data JPA, Lombok, Spring Security


## 멋사스네스(MutsaSNS) 서비스 소개

### **회원 인증·인가**

- 모든 회원은 회원가입을 통해 회원이 됩니다.
- 로그인을 하지 않으면 SNS 기능 중 피드를 보는 기능만 가능합니다.
- 로그인한 회원은 글쓰기, 수정, 댓글, 좋아요, 알림 기능이 가능합니다.

### 글쓰기

- 포스트를 쓰려면 회원가입 후 로그인(Token받기)을 해야 합니다.
- 포스트의 길이는 총 300자 이상을 넘을 수 없습니다.
- 포스트의 한 페이지는 20개씩 보이고 총 몇 개의 페이지인지 표시가 됩니다.
- 로그인 하지 않아도 글 목록을 조회 할 수 있습니다.
- 수정 기능은 글을 쓴 회원만이 권한을 가집니다.
- 포스트의 삭제 기능은 글을 쓴 회원만이 권한을 가집니다.

### 피드

- 로그인 한 회원은 자신이 작성한 글 목록을 볼 수 있습니다.


## 아키텍처

![mutsasns_img1](/mutsasns_img1.png)

## ERD
![mutsasns_img2](/mutsasns_img2.png)

## EndPoint


