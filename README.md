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
**회원가입**
<span style="background-color: #f1f8ff;color:black">POST /api/v1/users/join</span>
- Request body
```
"password" : "1234"
"userName" : "AAA"
```
- Response body
```
{
  "resultCode": "SUCCESS",
  "result": {
    "userName": "AAA",
    "userId": 22
  }
}
```

**로그인**
<span style="background-color: #f1f8ff;color:black">POST /api/v1/users/login</span>
- Request body
```
{
  "password": "1234",
  "userName": "AAA"
}
```
- Response body
```
{
  "resultCode": "SUCCESS",
  "result": {
    "jwt": "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyTmFtZSI6IkFBQSIsImlhdCI6MTY3MjEyMTc3OCwiZXhwIjoxNjcyMTI1Mzc4fQ.ULGJMYWR3y_uMVXnNbkqDfSdubTZzZ3v5Ef5Igs-p_o"
  }
}
```

**포스트 작성**
<span style="background-color: #f1f8ff;color:black">POST /api/v1/posts</span>

- Request body
```
{
  "body": "string",
  "title": "string"
}
```
- Response body
```
{
  "resultCode": "SUCCESS",
  "result": {
    "message": "포스트 등록 완료",
    "postId": 54
  }
}
```

**포스트 수정**
<span style="background-color: #f1f8ff;color:black">PUT /api/v1/posts/{id}</span>

- Request body
```
{
"body": "string",
"title": "string"
}
```
- Response body
```
{
  "resultCode": "SUCCESS",
  "result": {
    "message": "포스트 수정 완료",
    "postId": 54
  }
}
```

**포스트 삭제**
<span style="background-color: #f1f8ff;color:black">DELETE /api/v1/posts/{id}</span>

- 매개변수 postId 입력
- Response body
```
{
  "resultCode": "SUCCESS",
  "result": {
    "message": "포스트 삭제 완료",
    "postId": 55
  }
}
```

**포스트 리스트 조회**
<span style="background-color: #f1f8ff;color:black">GET /api/v1/posts</span>
- Response body
```
{
  "resultCode": "SUCCESS",
  "result": {
    "content": [
      {
        "id": 19,
        "userName": "kyeongrok22",
        "title": "hello-title",
        "body": "hello-body",
        "createdAt": "2022-12-26T08:50:08.361683",
        "lastModifiedAt": "2022-12-26T08:50:08.361683"
      },
      {
        "id": 20,
        "userName": "kyeongrok22",
        "title": "hello-title",
        "body": "hello-body",
        "createdAt": "2022-12-26T08:59:17.683221",
        "lastModifiedAt": "2022-12-26T08:59:17.683221"
      }
    ],
    "pageable": {
      "sort": {
        "empty": true,
        "sorted": false,
        "unsorted": true
      },
      "offset": 0,
      "pageNumber": 0,
      "pageSize": 20,
      "paged": true,
      "unpaged": false
    },
    "last": false,
    "totalPages": 3,
    "totalElements": 49,
    "size": 20,
    "number": 0,
    "sort": {
      "empty": true,
      "sorted": false,
      "unsorted": true
    },
    "first": true,
    "numberOfElements": 20,
    "empty": false
  }
}
```

**포스트 상세 조회**
<span style="background-color: #f1f8ff;color:black">GET /api/v1/posts/{postId}</span>
- 매개변수 postId 입력
- Response body
```
{
  "resultCode": "SUCCESS",
  "result": {
    "id": 20,
    "userName": "kyeongrok22",
    "title": "hello-title",
    "body": "hello-body",
    "createdAt": "2022-12-26T08:59:17.683221",
    "lastModifiedAt": "2022-12-26T08:59:17.683221"
  }
}
```
