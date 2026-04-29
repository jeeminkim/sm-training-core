# SM Training Core

Java 1.8, Maven, Spring MVC 5.2.4, Servlet 4.0, JSP, Vanilla JavaScript 기반의 **SM 업무 구조 학습 프로젝트**입니다.

이 프로젝트는 특정 회사 프레임워크 이름을 그대로 흉내 내는 것이 아니라, 실제 SM 업무에서 자주 만나는 다음 구조에 익숙해지는 것을 목표로 합니다.

```text
JSP 화면
→ JavaScript Operation
→ Controller
→ Process Service
→ Interface
→ Implementation
→ DTO
→ DB 또는 외부 API
```

현재 프로젝트는 **콘솔 실행**과 **웹 실행**을 모두 지원합니다.

---

## 1. 프로젝트 목표

이 프로젝트의 목적은 다음과 같습니다.

- Java 1.8 + Maven 환경에 익숙해지기
- Spring MVC 기반 웹 요청 흐름 이해하기
- JSP + Vanilla JS Operation 구조 익히기
- Controller → Service → Interface → Implementation → DTO 흐름 익히기
- Eclipse + Tomcat 9 실행 방식 익히기
- GitHub + Eclipse EGit 협업 루틴 익히기
- 이후 DB / 외부 API 연동으로 확장하기

---

## 2. 기술 스택

| 구분 | 기술 |
|---|---|
| Java | OpenJDK 1.8 |
| Build | Maven |
| Framework | Spring MVC 5.2.4.RELEASE |
| Servlet | Servlet 4.0 |
| View | JSP |
| Frontend | Vanilla JavaScript |
| WAS | Apache Tomcat 9 |
| IDE | Eclipse |
| Version Control | Git / GitHub / Eclipse EGit |

---

## 3. 현재 구현된 흐름

### 3.1 콘솔 실행 흐름

```text
App.java
→ WatchItemProcessService
→ WatchItemStore Interface
→ InMemoryWatchItemStore Implementation
→ WatchItemDto
```

### 3.2 웹 실행 흐름

```text
MarketView.jsp
→ JavaScript Operation
→ MarketController
→ WatchItemProcessService
→ WatchItemStore Interface
→ InMemoryWatchItemStore Implementation
→ WatchItemDto
```

---

## 4. 회사 시스템과의 개념 매핑

| 학습 프로젝트 | 회사/SM 시스템에서의 의미 |
|---|---|
| `MarketView.jsp` | JSP 화면 |
| JavaScript `onLoad`, `selectList`, `insertOne`, `deleteAll`, `selectDetail` | 화면 Operation |
| `MarketController` | Controller / Action / Handler |
| `WatchItemProcessService` | Process Service / 업무 Service / PSC 역할 |
| `WatchItemStore` | Interface / DSI 역할 |
| `InMemoryWatchItemStore` | Implementation / DSC 역할 |
| `WatchItemDto` | DTO / VO / 데이터 전달 객체 |
| `App.java` | 콘솔 테스트용 실행부 |

---

## 5. 프로젝트 구조

```text
sm-training-core
│
├─ pom.xml
├─ README.md
├─ .gitignore
│
└─ src
   └─ main
      ├─ java
      │  └─ com
      │     └─ example
      │        └─ smtraining
      │           │
      │           ├─ App.java
      │           │
      │           ├─ config
      │           │  └─ AppConfig.java
      │           │
      │           ├─ web
      │           │  └─ MarketController.java
      │           │
      │           ├─ dto
      │           │  └─ WatchItemDto.java
      │           │
      │           ├─ contract
      │           │  └─ WatchItemStore.java
      │           │
      │           ├─ implementation
      │           │  └─ InMemoryWatchItemStore.java
      │           │
      │           └─ process
      │              └─ WatchItemProcessService.java
      │
      └─ webapp
         ├─ index.jsp
         │
         └─ WEB-INF
            ├─ web.xml
            │
            └─ views
               └─ MarketView.jsp
```

---

## 6. 계층별 역할

### 6.1 `MarketView.jsp`

사용자가 보는 JSP 화면입니다.

주요 역할은 다음과 같습니다.

- 화면 렌더링
- 사용자 입력 수집
- 버튼 클릭 이벤트 처리
- JavaScript Operation 실행
- Controller API 호출
- 응답 결과를 화면에 표시

현재 주요 Operation은 다음과 같습니다.

```text
onLoad
selectList
insertOne
selectDetail
deleteAll
```

---

### 6.2 JavaScript Operation

JSP 안에서 실행되는 화면 단위 함수입니다.

예를 들어 `selectList()`는 다음 흐름으로 동작합니다.

```text
selectList()
→ fetch("/market/selectList.do")
→ MarketController.selectList()
→ WatchItemProcessService.selectList()
→ InMemoryWatchItemStore.selectList()
→ JSON 응답
→ renderList()
```

회사 설계서의 Operation과 유사한 개념으로 볼 수 있습니다.

---

### 6.3 `MarketController`

웹 요청의 진입점입니다.

주요 역할은 다음과 같습니다.

- URL 요청 매핑
- 요청 파라미터 수신
- DTO 바인딩
- Process Service 호출
- JSON 응답 반환

현재 URL 매핑은 다음과 같습니다.

| URL | Method | 설명 |
|---|---:|---|
| `/market/view.do` | GET | 화면 진입 |
| `/market/selectList.do` | GET | 목록 조회 |
| `/market/selectDetail.do` | GET | 상세 조회 |
| `/market/insertOne.do` | POST | 단건 등록 |
| `/market/deleteAll.do` | POST | 전체 삭제 |

---

### 6.4 `WatchItemProcessService`

업무 흐름을 담당하는 Process Service 계층입니다.

주요 역할은 다음과 같습니다.

- 업무 흐름 제어
- 필수값 검증
- Interface 호출
- 구현체와 화면 사이 중간 조정
- 향후 트랜잭션 단위 관리

실무에서는 이 계층에 다음 로직이 들어갈 수 있습니다.

```text
validation
권한 체크
상태 체크
트랜잭션 처리
여러 DAO/API 조합
업무 예외 처리
```

---

### 6.5 `WatchItemStore`

Interface입니다.

Interface는 기능의 계약입니다.

현재 정의된 기능은 다음과 같습니다.

```text
selectList()
selectDetail()
insertOne()
deleteAll()
```

중요한 점은 다음과 같습니다.

```text
Interface에는 실제 처리 로직을 두지 않는다.
메서드 이름, 입력값, 반환값만 정의한다.
실제 구현은 Implementation 계층이 담당한다.
```

---

### 6.6 `InMemoryWatchItemStore`

`WatchItemStore` Interface의 실제 구현 클래스입니다.

현재는 DB를 붙이지 않았기 때문에 Java `List`를 임시 저장소로 사용합니다.

```text
현재:
Implementation → Memory List

향후:
Implementation → DB
Implementation → 외부 API
Implementation → EAI
```

---

### 6.7 `WatchItemDto`

DTO, 즉 Data Transfer Object입니다.

화면, Controller, Service, Implementation 사이에서 데이터를 전달하기 위한 객체입니다.

현재 필드는 다음과 같습니다.

| 필드 | 설명 | 예시 |
|---|---|---|
| `marketCode` | 마켓 코드 | `KRW-BTC` |
| `marketName` | 마켓명 | `비트코인` |
| `useYn` | 사용 여부 | `Y`, `N` |

실무에서는 DTO 필드명이 다음과 연결되는 경우가 많습니다.

```text
화면 input name
HTTP parameter
JSON key
DB column alias
Mapper parameter
API request/response field
```

---

## 7. 구조도

```text
┌──────────────────────────────┐
│ MarketView.jsp               │
│ - JSP 화면                   │
│ - Vanilla JS Operation        │
└───────────────┬──────────────┘
                │ fetch
                ▼
┌──────────────────────────────┐
│ MarketController             │
│ - URL 요청 매핑              │
│ - DTO 바인딩                 │
│ - JSON 응답                  │
└───────────────┬──────────────┘
                │
                ▼
┌──────────────────────────────┐
│ WatchItemProcessService      │
│ - 업무 흐름 제어             │
│ - validation                 │
│ - Interface 호출             │
└───────────────┬──────────────┘
                │
                ▼
┌──────────────────────────────┐
│ WatchItemStore               │
│ - Interface                  │
│ - 기능 계약                  │
└───────────────┬──────────────┘
                │ implements
                ▼
┌──────────────────────────────┐
│ InMemoryWatchItemStore       │
│ - 구현체                     │
│ - 현재는 Memory List 사용    │
└───────────────┬──────────────┘
                │
                ▼
┌──────────────────────────────┐
│ WatchItemDto                 │
│ - 데이터 전달 객체           │
└──────────────────────────────┘
```

---

## 8. 실행 방법

### 8.1 환경 확인

```powershell
java -version
javac -version
mvn -version
```

정상 기준은 다음과 같습니다.

```text
java version: 1.8.x
javac version: 1.8.x
mvn -version 결과의 Java version: 1.8.x
```

---

### 8.2 Maven 빌드

```powershell
cd C:\Work\sm-training-core
mvn clean package
```

성공 시 다음 메시지가 표시됩니다.

```text
BUILD SUCCESS
```

WAR 파일 위치는 다음과 같습니다.

```text
target/sm-training-core.war
```

---

### 8.3 콘솔 실행

```powershell
mvn exec:java
```

또는 Eclipse에서 다음 순서로 실행합니다.

```text
App.java 우클릭
→ Run As
→ Java Application
```

---

### 8.4 웹 실행

Eclipse에서 Tomcat 9를 등록한 뒤 다음 순서로 실행합니다.

```text
프로젝트 우클릭
→ Run As
→ Run on Server
→ Tomcat 9 선택
```

접속 URL은 다음과 같습니다.

```text
http://localhost:8080/sm-training-core/
```

또는 다음 URL로 직접 접근할 수 있습니다.

```text
http://localhost:8080/sm-training-core/market/view.do
```

---

## 9. 웹 화면 Operation

| Operation | 설명 | Controller URL |
|---|---|---|
| `onLoad` | 화면 최초 로딩 시 자동 실행 | 내부에서 `selectList()` 호출 |
| `selectList` | 목록 조회 | `GET /market/selectList.do` |
| `selectDetail` | 상세 조회 | `GET /market/selectDetail.do` |
| `insertOne` | 단건 등록 | `POST /market/insertOne.do` |
| `deleteAll` | 전체 삭제 | `POST /market/deleteAll.do` |

---

## 10. 실행 검증 결과

웹 화면에서 다음 Operation 흐름을 확인했습니다.

```text
onLoad
→ selectList
→ selectDetail
→ insertOne
→ selectList with condition
```

검증된 호출 흐름은 다음과 같습니다.

```text
MarketView.jsp
→ JavaScript Operation
→ MarketController
→ WatchItemProcessService
→ WatchItemStore Interface
→ InMemoryWatchItemStore Implementation
→ WatchItemDto
```

확인된 기능은 다음과 같습니다.

| 기능 | 결과 |
|---|---|
| 화면 진입 | 정상 |
| `onLoad` 자동 실행 | 정상 |
| 전체 목록 조회 | 정상 |
| 조건 검색 | 정상 |
| 상세 조회 | 정상 |
| 단건 등록 | 정상 |
| 전체 삭제 | 정상 |

예시 로그:

```text
onLoad 실행
selectList 요청: /sm-training-core/market/selectList.do?marketName=
selectList 응답: KRW-BTC, KRW-ETH

selectDetail 요청: /sm-training-core/market/selectDetail.do?marketCode=KRW-BTC
selectDetail 응답: KRW-BTC

insertOne 요청
insertOne 응답: 관심 항목이 등록되었습니다.
```

---

## 11. Eclipse 설정 요약

### 11.1 JDK 1.8 설정

```text
Window
→ Preferences
→ Java
→ Installed JREs
→ JDK 1.8 등록 및 선택
```

프로젝트별 확인:

```text
프로젝트 우클릭
→ Properties
→ Java Build Path
→ Libraries
→ JRE System Library [JavaSE-1.8]
```

컴파일러 확인:

```text
프로젝트 우클릭
→ Properties
→ Java Compiler
→ Compiler compliance level: 1.8
```

---

### 11.2 Tomcat 9 설정

```text
Window
→ Preferences
→ Server
→ Runtime Environments
→ Add
→ Apache Tomcat v9.0
```

주의 사항은 다음과 같습니다.

```text
Tomcat 10 사용 금지
현재 프로젝트는 javax.servlet 기반이므로 Tomcat 9 사용
```

---

## 12. Git / GitHub 연동

현재 GitHub Repository는 다음과 같습니다.

```text
https://github.com/jeeminkim/sm-training-core
```

로컬 경로는 다음과 같습니다.

```text
C:\Work\sm-training-core
```

Eclipse에서 Git 연동은 다음 위치에서 확인할 수 있습니다.

```text
프로젝트 우클릭
→ Team
```

아래 메뉴가 보이면 정상입니다.

```text
Commit...
Push to origin
Fetch from origin
Push Branch 'main'...
Pull
Synchronize Workspace
Show in Repositories View
```

---

## 13. Git 협업 기본 원칙

여러 사람이 함께 작업할 때 핵심은 다음입니다.

```text
내 작업 전에는 최신 버전을 받는다.
내 작업 후에는 충돌 없이 올린다.
```

가장 기본 루틴은 다음과 같습니다.

```text
Pull → 작업 → Commit → Pull → Push
```

---

## 14. 작업 시작 전: 최신 버전 받기

프로젝트 우클릭:

```text
Team
→ Pull
```

이 버튼은 GitHub의 최신 변경사항을 내 로컬 프로젝트에 가져와서 병합합니다.

작업 시작 전 추천 순서는 다음과 같습니다.

```text
1. Team → Pull
2. Maven → Update Project
3. Tomcat Restart
4. 작업 시작
```

---

## 15. 작업 중: 변경사항 확인

내가 파일을 수정한 뒤에는 다음 메뉴로 변경사항을 확인할 수 있습니다.

```text
Team
→ Synchronize Workspace
```

또는 다음 메뉴를 사용할 수 있습니다.

```text
Team
→ Commit...
```

보통은 `Commit...` 화면에서 변경 파일을 확인하면 충분합니다.

---

## 16. 작업 완료 후: 커밋하기

프로젝트 우클릭:

```text
Team
→ Commit...
```

Git Staging 화면에서 다음 순서로 진행합니다.

```text
1. Unstaged Changes에서 변경 파일 확인
2. 커밋할 파일만 Staged Changes로 이동
3. Commit Message 작성
4. Commit
```

커밋 메시지 예시:

```text
Add server-side validation for market code
```

주의 사항은 다음과 같습니다.

```text
Commit은 내 PC 로컬 Git에만 저장하는 것이다.
아직 GitHub에는 올라가지 않은 상태이다.
```

---

## 17. GitHub에 올리기 전: 한 번 더 Pull

여러 사람이 작업한다면 push 전에 한 번 더 최신 버전을 받아야 합니다.

```text
Team
→ Pull
```

이유는 다음과 같습니다.

```text
내가 작업하는 동안 다른 사람이 GitHub에 먼저 push했을 수 있기 때문
```

추천 순서는 다음과 같습니다.

```text
1. Commit
2. Pull
3. 충돌 없으면
4. Push
```

---

## 18. 내 작업 GitHub에 반영하기

프로젝트 우클릭:

```text
Team
→ Push to origin
```

또는 다음 메뉴를 사용할 수 있습니다.

```text
Team
→ Push Branch 'main'...
```

성공하면 내 커밋이 GitHub에 올라갑니다.

---

## 19. 가장 추천하는 협업 루틴

### 작업 시작

```text
1. Team → Pull
2. Maven → Update Project
3. Tomcat Restart
```

### 작업 중

```text
4. 코드 수정
5. 화면 테스트
6. mvn clean package 또는 Eclipse 빌드 확인
```

### 작업 완료

```text
7. Team → Commit...
8. 변경 파일 확인
9. Commit Message 작성
10. Commit
11. Team → Pull
12. Team → Push to origin
```

한 줄 요약:

```text
Pull → 작업 → Commit → Pull → Push
```

---

## 20. `Commit and Push`를 바로 써도 되는가?

혼자 작업할 때는 가능합니다.

```text
Team
→ Commit...
→ Commit and Push
```

하지만 여러 사람이 함께 작업할 때는 아래 방식을 더 권장합니다.

```text
Commit
→ Pull
→ Push
```

이유는 다음과 같습니다.

```text
Push 전에 다른 사람 변경사항을 먼저 받아서 충돌 여부를 확인할 수 있기 때문
```

---

## 21. 충돌이 나는 경우

`Pull` 했을 때 충돌이 나면 Eclipse가 충돌 파일을 표시합니다.

충돌 파일 안에는 보통 이런 표시가 생깁니다.

```text
<<<<<<< HEAD
내가 수정한 내용
=======
다른 사람이 수정한 내용
>>>>>>> branch
```

이 경우 처리 순서는 다음과 같습니다.

```text
1. 충돌 파일 열기
2. 내 코드와 상대방 코드 중 무엇을 살릴지 정리
3. <<<<<<<, =======, >>>>>>> 표시 제거
4. 저장
5. Team → Add to Index 또는 Git Staging에서 Stage
6. Commit
7. Push
```

Eclipse에서는 충돌 파일 우클릭 후 다음 기능을 사용할 수도 있습니다.

```text
Team
→ Merge Tool
```

---

## 22. Git 버튼 의미 정리

| 버튼 | 의미 | 언제 사용 |
|---|---|---|
| `Pull` | GitHub 최신 변경사항 가져오기 | 작업 시작 전, Push 전 |
| `Commit` | 내 변경사항을 로컬 Git에 저장 | 기능 단위 작업 완료 후 |
| `Push to origin` | 내 커밋을 GitHub에 업로드 | Pull 후 충돌 없을 때 |
| `Synchronize Workspace` | 로컬/원격 차이 비교 | 변경사항 확인할 때 |
| `Fetch from origin` | 원격 정보만 가져오고 병합은 안 함 | 차이만 보고 싶을 때 |
| `Merge` | 가져온 변경사항을 내 브랜치에 병합 | Fetch 후 수동 병합할 때 |
| `Rebase` | 내 커밋을 최신 커밋 위로 재정렬 | 익숙해진 뒤 사용 권장 |

초반에는 이것만 기억하면 됩니다.

```text
Pull = 남의 최신 작업 받기
Commit = 내 작업 저장
Push = 내 작업 공유
```

---

## 23. 실무에서 안전한 Git 습관

작은 단위로 자주 커밋하는 것이 좋습니다.

나쁜 방식:

```text
하루 종일 여러 파일 수정
→ 한 번에 commit
→ push
→ 충돌 대량 발생
```

좋은 방식:

```text
validation 추가
→ 테스트
→ commit

README 수정
→ 테스트
→ commit

Controller 수정
→ 테스트
→ commit
```

---

## 24. 브랜치 전략

협업 중에는 `main`에 바로 작업하기보다 보통 브랜치를 나눕니다.

예시는 다음과 같습니다.

```text
main
feature/market-validation
feature/upbit-api-client
fix/tomcat-context-path
```

다만 현재 학습 초기에는 `main` 기준으로 아래 루틴부터 익히면 충분합니다.

```text
Pull → 작업 → Commit → Pull → Push
```

---

## 25. Git에 올리지 않을 파일

`.gitignore` 예시는 다음과 같습니다.

```gitignore
# Maven build output
target/

# Eclipse workspace metadata
.metadata/

# Eclipse project files
.classpath
.project
.settings/

# IntelliJ
.idea/
*.iml

# VS Code
.vscode/

# OS
.DS_Store
Thumbs.db

# Logs
*.log
```

특히 아래 두 개는 GitHub에 올리지 않는 것이 좋습니다.

```text
.metadata/
target/
```

---

## 26. 다음 확장 계획

현재는 메모리 기반 구현체를 사용합니다.

다음 단계에서는 아래 순서로 확장할 수 있습니다.

```text
1. 서버 validation 강화
2. Upbit API Client 추가
3. selectDetail에서 실제 현재가 조회 연결
4. DB 저장소 구현체 추가
5. Interface 뒤에서 Memory 구현체와 DB 구현체 교체
6. 외부 API 장애/timeout/예외처리 학습
7. 로그 및 공통 응답 구조 정리
```

---

## 27. 현재까지 완료된 항목

```text
OpenJDK 1.8 설치 완료
Maven 설치 완료
mvn clean package 성공
mvn exec:java 성공
Eclipse Maven Project Import 완료
JRE System Library JavaSE-1.8 설정 완료
Tomcat 9 실행 성공
Spring MVC 웹 화면 실행 성공
JSP Operation → Controller → Service → Interface → Implementation 흐름 검증 완료
GitHub Repository 생성 완료
Eclipse EGit 연동 완료
```
