# 바로인턴 12기 조규성

Java 직무 과제

---

## Swagger UI 주소

- http://15.164.94.191:8080/swagger-ui/index.html

---

## AWS EC2에서 실행중인 API의 엔드포인트 URL

- 일반 사용자 회원가입
    - http://15.164.94.191:8080/signup
- 관리자 회원가입
    - http://15.164.94.191:8080/admin/users/signup
- 로그인
    - http://15.164.94.191:8080/login
- 관리자 권한 부여
    - http://15.164.94.191:8080/admin/users/{userId}/roles

---

## 구현 포인트

- [x] 유저(User) 및 관리자(Admin) 회원가입, 로그인 API를 개발합니다.
- [x] JWT를 이용하여 Access Token을 발급하고 검증하는 로직을 적용합니다.
- [x] 일반 사용자(User)와 관리자(Admin) 역할(Role)을 구분하여 특정 API 접근을 제한합니다.
- [x] 각 API 엔드포인트 별 올바른 입력과 잘못된 입력에 대해 테스트 케이스를 작성합니다.
- [x] Swagger (또는 OpenAPI) 스펙을 기반으로 API 문서화 도구를 프로젝트에 추가합니다.
- [x] 각 API에 대한 설명, 파라미터, 요청/응답 예시 등을 Swagger UI에 등록하여 브라우저에서 쉽게 확인할 수 있도록 합니다.
