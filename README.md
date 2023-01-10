# 멋사스네스(MutsaSNS)

- gitlab
    - https://gitlab.com/mooonju/FinalProject_JoMunJu
- swagger
    - http://ec2-3-35-210-214.ap-northeast-2.compute.amazonaws.com:8080/swagger-ui/
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

### 댓글
- 댓글은 회원만이 권한을 가집니다.
- 글의 길이는 총 100자 이상을 넘을 수 없습니다.
- 회원은 다수의 댓글을 달 수 있습니다.

### 좋아요
- 좋아요는 회원만 권한을 가집니다.
- 좋아요 기능은 취소가 가능합니다.

### 알림
- 알림은 회원이 자신이 쓴 글에 대해 다른회원의 댓글을 올리거나 좋아요시 받는 기능입니다.
- 알림 목록에서 자신이 쓴 글에 달린 댓글과 좋아요를 확인할 수 있습니다.


## 아키텍처

![mutsasns_img1](/mutsasns_img1.png)

## ERD
![mutsasns_erd](/mutsasns_erd.png)

## 체크 리스트
- [x] 회원가입 구현
- [x] Swagger 
- [x] AWS EC2에 Docker 배포
- [x] Gitlab CI & Crontab CD 구현
- [x] 로그인 구현
- [x] 포스트 작성 / 수정 / 삭제 / 상세 조회 / 리스트 구현
- [x] 회원 가입 / 로그인 테스트 코드 작성
- [x] 포스트 작성 / 수정 / 삭제 / 상세 조회 / 리스트 테스트 코드 작성
- [x] 댓글 작성 / 수정 / 삭제 / 조회 / 리스트 구현
- [x] 좋아요 / 마이피드 / 알림 구현
- [x] 댓글 작성 / 수정 / 삭제 / 조회 / 리스트 테스트 코드 작성
- [x] 좋아요 / 마이피드 / 알림 테스트 코드 작성
- [x] Swagger에 ApiOperation을 써서 Controller 설명 추가

## EndPoint
**회원가입**
<span style="background-color: #f1f8ff;color:black">POST /api/v1/users/join</span>
- Request body
```json
{
"password" : "1234",
"userName" : "AAA"
}
```
- Response body
```json
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
```json
{
  "password": "1234",
  "userName": "AAA"
}
```
- Response body
```json
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
```json
{
  "body": "string",
  "title": "string"
}
```
- Response body
```json
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
```json
{
"body": "string",
"title": "string"
}
```
- Response body
```json
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
```json
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
```json
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
```json
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
**댓글 조회**
GET /posts/{postId}/comments[?page=0]
- Response body
```json
{
  "resultCode": "SUCCESS",
  "result": {
    "content": [
      {
        "id": 5,
        "comment": "hello-comment",
        "userName": "kyeongrok48",
        "postId": 14,
        "createdAt": "2023-01-05 00:46:59",
        "lastModifiedAt": "2023-01-05 00:46:59"
      }
    ],
    "pageable": {
      "sort": {
        "empty": false,
        "sorted": true,
        "unsorted": false
      },
      "offset": 0,
      "pageNumber": 0,
      "pageSize": 10,
      "paged": true,
      "unpaged": false
    },
    "totalPages": 1,
    "totalElements": 1,
    "last": true,
    "size": 10,
    "number": 0,
    "sort": {
      "empty": false,
      "sorted": true,
      "unsorted": false
    },
    "numberOfElements": 1,
    "first": true,
    "empty": false
  }
}
```

**댓글 작성**
POST /posts/{postsId}/comments
- Request Body
```json
{
	"comment" : "comment test4"
}
```

- Response Body
```json
{
	"resultCode": "SUCCESS",
	"result":{
		"id": 4,
		"comment": "comment test4",
		"userName": "test",
		"postId": 2,
		"createdAt": "2022-12-20T16:15:04.270741"
	}
}
```

**댓글 수정**
PUT /posts/{postId}/comments/{id}
- Request Body
```json
{
	"comment" : "modify comment"
}
```
- Response Body
```json
{
	"resultCode": "SUCCESS",
	"result":{
		"id": 4,
		"comment": "modify comment",
		"userName": "test",
		"postId": 2,
		"createdAt": "2022-12-20T16:15:04.270741",
		"lastModifiedAt": "2022-12-23T16:15:04.270741"
		}
}
```
**댓글 삭제**
DELETE /posts/{postsId}/comments/{id}

- Response Body
```json
{
	"resultCode": "SUCCESS",
	"result":{
		"message": "댓글 삭제 완료",
		"id": 4
		}
}
```

**좋아요 누르기**
POST /posts/{postId}/likes

- like를 한번 누를 때 마다 row가 1개씩 추가되는 방식으로 구현
- Response Body
```json
{
	"resultCode":"SUCCESS",
        "result": "좋아요를 눌렀습니다."
}
```
**좋아요 개수**
GET /posts/{postsId}/likes
- Response Body
```json
{
	"resultCode":"SUCCESS",
        "result": 0
}
```
**마이 피드 조회 기능**
GET /posts/my
- 로그인된 유저만의 피드목록을 필터링 하는 기능 (Pageable 사용)
- Response Body
```json
{
  "resultCode": "SUCCESS",
  "result": {
    "content": [
      {
        "id": 19,
        "userName": "test5",
        "title": "알람 테스트3",
        "body": "알람 테스트 3",
        "createdAt": "2023-01-10 00:21:14",
        "lastModifiedAt": "2023-01-10 00:21:14"
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
    "last": true,
    "totalPages": 1,
    "totalElements": 3,
    "size": 20,
    "number": 0,
    "sort": {
      "empty": true,
      "sorted": false,
      "unsorted": true
    },
    "first": true,
    "numberOfElements": 3,
    "empty": false
  }
}
```
**알람 리스트**
GET /alarms
- 최신 순으로 20개씩 표시 (Pageable 사용)
- **알람 리스트 조회 시 응답 필드**
  - `id` : 알람 ID
  - `alarmType` :알람 타입 (NEW_COMMENT_ON_POST, NEW_LIKE_ON_POST)
  - `fromUserId`: fromUserId(알림을 발생시킨 user id)
  - `targetId` : targetId(알림이 발생된 post id)
  - `text` : alarmType 따라 string 필드에 담아 줄 수 있도록 필드를 선언합니다.
    - *`NEW_COMMENT_ON_POST`* 일 때는 alarmText *→* `new comment!`
    - *`NEW_LIKE_ON_POST`* 일 때는 alarmText *→* `"new like!"`
  - `createdAt` : 등록일시
- Response Body
```json
{
	"resultCode":"SUCCESS",
    "result": {
	  "content": [
        {
	      "id": 1,
	      "alarmType": "NEW_LIKE_ON_POST",
          "fromUserId": 1,
          "targetId": 1,
	      "text": "new like!",
	      "createdAt": "2022-12-25T14:53:28.209+00:00"
	    }
	  ]
	}
}
```







