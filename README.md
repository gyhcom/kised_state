
## 프로젝트 개요
  #### 작성 필요
---

## 주요 기능



 #### 추가 작성 필요
---

## 기술 스택

- **Java 17**
- **Spring Boot 3.1.4**
- **Spring Security 6.x**
- **Spring Data JPA**
- **MariaDB**
- **Lombok**
- **Gradle**

---
```plaintext
main
└── java
    └── state
        ├── admin                            # 관리자 도메인
        │   └── userManage                   #
        │       ├── application              #
        │       │   ├── command              #
        │       │   ├── common               #
        │       │   │   ├── api              #
        │       │   │   ├── error            #
        │       │   │   ├── exception        #
        │       │   │   └── exceptionhandler #
        │       │   ├── fasade               #
        │       │   └── processor            #
        │       ├── domain                   #
        │       │   ├── auth                 #
        │       │   ├── entity               #
        │       │   └── repository           #
        │       ├── infrastructure           #
        │       │   └── jpa                  #
        │       └── presentation             #
        │           └── request              #
        │           └── response             #
        ├── common                           # 공통 설정 및 유틸리티
        │   └── config                       # 설정 파일
        │       └── StateSecurityConfig      # Spring Security 설정
        ├── edu                              # 교육 도메인
        └── member                           # 회원(Member) 도메인
            ├── application                  # 애플리케이션 계층
            │   ├── command                  # Command 객체 정의
            │   ├── fasade                   # 애플리케이션 파사드
            │   ├── processor                # 비즈니스 로직 처리
            │   └── result                   # 결과 데이터 정의
            ├── domain                       # 도메인 계층
            │   ├── entity                   # 엔티티
            │   ├── exception                # 예외 처리
            │   └── repository               # 도메인 레벨의 Repository 인터페이스
            ├── infrastructure               # 인프라스트럭처 계층
            │   └── JPA                      # JPA 기반 Repository 구현
            └── presentation                 # 프레젠테이션 계층 (REST 컨트롤러)
                ├── request                  # 요청 DTO
                └── response                 # 응답 DTO
```
