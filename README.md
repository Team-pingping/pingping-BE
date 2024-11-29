# 📌 모일 때 맵핀, MOPING
https://www.moping.co.kr

![모핑 메인](https://github.com/user-attachments/assets/a1bd3835-2a42-481f-93c8-e7c6694be1f6)

<br>

# 👨‍👧‍👦 팀 소개

### **팀명 : 핑핑이들**

| **분야** | **이름** | **포지션** | **내용** |
| :---: | :---: | --- | --- |
| 기획 | 박가은 | 📈 PM, 서비스 기획 | 유저 리서치, 와이어프레임 작성, 서비스 정책 확립, <br /> 비즈니스 모델 구축  |
| 기획 | 김규리 | 📋 서비스 기획 | 유저 리서치, 와이어프레임 작성, 서비스 정책 확립, <br /> 비즈니스 모델 구축, 서비스 마케팅 리드 |
| 기획 | 안재형 | 📊 서비스 기획 | 유저 리서치, 와이어프레임 작성, 서비스 정책 확립, <br /> 비즈니스 모델 구축, 서비스 마케팅 리드 |
| 디자인 | 김윤서 | 🎨 디자인 리드 | ux/ui디자인, gui 디자인 |
| 디자인 | 이어령 | 🎨 디자인 | ux/ui디자인, gui 디자인 |
| 개발 | 최호 | 📱  프론트엔드 리드 | 화면 UI 구현, API 연동 |
| 개발 | 최서희 | 📱 프론트엔드 | 화면 UI 구현, API 연동 |
| 개발 | 문희상 | 💻 백엔드 리드 | API 구현, ERD 설계, 서버 배포 |
| 개발 | 윤소민 | 💻 백엔드 | API 구현, ERD 설계, 서버 배포 |

<br>

# 📱 서비스 기능
![모핑 기능1](https://github.com/user-attachments/assets/925b1d01-71fb-4a44-8fef-2d5d174309dd)

![모핑 기능2](https://github.com/user-attachments/assets/480055d4-cb68-4918-bc68-bcf62bae9fa4)

![모핑 기능3](https://github.com/user-attachments/assets/3c74868c-a653-4114-a34a-29dd17200bdb)

![모핑 기능4](https://github.com/user-attachments/assets/1c8c93b2-a61e-447e-8040-4e5bc43dfaf6)


<br>

# 📜 API 명세서
[moping API 명세서 다운로드](https://github.com/user-attachments/files/17946614/Moping-Backend.API.Docs.pdf)

<br>

# 📁 ERD
### MySQL
![image](https://github.com/user-attachments/assets/f1e44e08-739c-4a63-8d58-3fa763f85a66)

### MongoDB
![image](https://github.com/user-attachments/assets/5dcfc6e4-5371-4538-bdfe-1f819b465bff)


<br>

# 🗺️ 시스템 아키텍처
![image](https://github.com/user-attachments/assets/ef58b9e5-1f48-4a02-9fd8-48a2f9599ce9)

<br>

# 👍 공통 사항

- 단위 테스트 작성(service 메소드 별로) : Junit 사용
- 다른 사람이 알아보기 쉽도록 주석처리해야 합니다. (controller, service 메서드마다)
    - javadoc 형식 https://jake-seo-dev.tistory.com/59
- issue 생성 및 PR을 통해 본인이 구현한 부분에 대한 기록을 남겨야 합니다.
- 테스트 및 원할한 서버 운영을 위한 로그를 작성해야 합니다.(에러나 운영에 필요한 로그. 검색시 검색어와 같은 로그)
- 예외처리는 항상 잘 만들어두기 (code, message, data)
- 개발 기간 : 9/30 ~ 11/28
- 스프린트 (3일간격) 진행 (해올 것을 정해서 해오기)
    - 수요일, 토요일

<br>

# 🛠️ 기술 스택

- #### Language, Framework, Library
  ![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=flat-square&logo=Kotlin&logoColor=FFFFFF)
  ![Springboot](https://img.shields.io/badge/Springboot-6DB33F?style=flat-square&logo=springboot&logoColor=white)
  ![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=flat-square&logo=Gradle&logoColor=white)
  ![Spring Data JPA](https://img.shields.io/badge/Spring%20Data%20JPA-6DB33F?style=flat-square&logo=spring&logoColor=white)
    - Kotlin은 간결하고 직관적인 문법으로 코드 생산성을 높이며, Null 안정성을 제공하여 오류를 사전에 방지
    - JPA를 통해 SQL을 직접 작성하지 않아도 되므로 데이터베이스 작업에 소요되는 시간을 줄이고, 비즈니스 로직 구현에 집중 가능

- #### Test
  ![JUnit](https://img.shields.io/badge/JUnit-25A162?style=flat-square&logo=JUnit5&logoColor=white)
  ![MockK](https://img.shields.io/badge/MockK-FFCA28?style=flat-square&logo=MockK&logoColor=white)
    - JUnit은 간단한 어노테이션 기반 설정으로 테스트 작성과 실행을 직관적이고 효율적으로 만들어줌
    - MockK는 코틀린에 특화된 모킹 라이브러리로, 코루틴과 같은 코틀린 고유 기능을 쉽게 모킹할 수 있어 비동기 코드 테스트에 강점이 있음

- #### CICD
  ![Jenkins](https://img.shields.io/badge/Jenkins-D24939?style=flat-square&logo=Jenkins&logoColor=white)
  ![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=flat-square&logo=docker&logoColor=white)
    - Jenkins를 사용한 CI/CD 파이프라인은 자동화된 테스트, 빌드, 배포를 통해 개발 프로세스를 효율적으로 관리
    - Docker는 애플리케이션을 컨테이너로 패키징하여 일관된 실행 환경을 제공하고, 배포를 빠르고 효율적으로 수행 가능

- #### Database
  ![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=flat-square&logo=mysql&logoColor=white)
  ![MongoDB](https://img.shields.io/badge/MongoDB-47A248?style=flat-square&logo=MongoDB&logoColor=white)
  ![Redis](https://img.shields.io/badge/redis-%23DD0031.svg?style=flat-square&logo=redis&logoColor=white)
    - MySQL은 뛰어난 성능과 확장성을 제공하며, 광범위한 커뮤니티 지원과 다양한 플랫폼에서의 안정성을 보장  
    - MongoDB는 유연한 스키마 설계를 통해 다양한 데이터를 효율적으로 저장하고, 빠른 쿼리 성능을 제공
    - Redis는 인메모리 데이터 저장소로 초고속 데이터 접근과 다양한 데이터 구조를 지원

- #### API 테스트, 명세서
  ![Notion](https://img.shields.io/badge/Notion-%23000000.svg?style=flat-square&logo=notion&logoColor=white)
  ![Postman](https://img.shields.io/badge/Postman-FF6C37?style=flat-square&logo=postman&logoColor=white)
  ![Spring REST Docs](https://img.shields.io/badge/Spring%20REST%20Docs-6DB33F?style=flat-square&logo=spring&logoColor=white)
  ![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=flat-square&logo=swagger&logoColor=white)
    - RestDocs를 통해 생성된 문서를 Swagger UI로 시각화하여, 개발자와 비개발자 모두가 실시간으로 API를 테스트 가능
    - 테스트 코드 작성과 함께 API 문서가 자동으로 생성되어, 실제 코드와 문서의 동기화 문제가 발생하지 않음
    - 테스트 시에 문서를 검증할 수 있어 신뢰성을 높임

- #### 🙏 협업 툴
  ![Slack](https://img.shields.io/badge/Slack-4A154B.svg?style=flat-square&logo=slack&logoColor=white)
  ![Notion](https://img.shields.io/badge/Notion-000000.svg?style=flat-square&logo=notion&logoColor=white)

<br>

# 🤙 개발규칙

### ⭐ Code Convention

---

<details>
<summary style = " font-size:1.3em;">Naming</summary>
<div markdown="1">

- 패키지 : 언더스코어(`_`)나 대문자를 섞지 않고 소문자를 사용하여 작성합니다.
- 클래스 : 클래스 이름은 명사나 명사절로 지으며, 대문자 카멜표기법(Upper camel case)을 사용합니다.
- 메서드 : 메서드 이름은 동사/전치사로 시작하며, 소문자 카멜표기법(Lower camel case)를 사용합니다. 의도가 전달되도록 최대한 간결하게 표현합니다.
- 변수 : 소문자 카멜표기법(Lower camel case)를 사용합니다.
- ENUM, 상수 : 상태를 가지지 않는 자료형이면서 `static final`로 선언되어 있는 필드일 때를 상수로 간주하며, 대문자와 언더스코어(UPPER_SNAKE_CASE)로 구성합니다.
- DB 테이블: 소문자와 언더스코어로(lower_snake_case) 구성합니다.
- 컬렉션(Collection): **복수형**을 사용하거나 **컬렉션을 명시합니다**. (Ex. userList, users, userMap)
- LocalDateTime: 접미사에 *Time**를 붙입니다.

</div>
</details>
<details>
<summary style = " font-size:1.3em;">Comment</summary>
<div markdown="1">

### 1. 한줄 주석은 // 를 사용한다.

```java
// 하이~

```

### 2. 한줄 주석 외에 설명을 위한 주석은 JavaDoc을 사용한다.

```java
/**
 * 두 정수를 더합니다.
 *
 * <p>이 메소드는 두 개의 정수를 입력받아 그 합계를 반환합니다.</p>
 *
 * @param a 첫 번째 정수
 * @param b 두 번째 정수
 * @return 두 정수의 합
 * @throws ArithmeticException 만약 계산 중 오류가 발생하면
 */

```

</div>
</details>
<details>
<summary style = " font-size:1.3em;">Import</summary>
<div markdown="1">

### 1. 소스파일당 1개의 탑레벨 클래스를 담기

> 탑레벨 클래스(Top level class)는 소스 파일에 1개만 존재해야 한다. ( 탑레벨 클래스 선언의 컴파일타임 에러 체크에 대해서는 Java Language Specification 7.6 참조 )
>

### 2. static import에만 와일드 카드 허용

> 클래스를 import할때는 와일드카드(*) 없이 모든 클래스명을 다 쓴다. static import에서는 와일드카드를 허용한다.
>

### 3. 애너테이션 선언 후 새줄 사용

> 클래스, 인터페이스, 메서드, 생성자에 붙는 애너테이션은 선언 후 새줄을 사용한다. 이 위치에서도 파라미터가 없는 애너테이션 1개는 같은 줄에 선언할 수 있다.
>

### 4. 배열에서 대괄호는 타입 뒤에 선언

> 배열 선언에 오는 대괄호([])는 타입의 바로 뒤에 붙인다. 변수명 뒤에 붙이지 않는다.
>

### 5. `long`형 값의 마지막에 `L`붙이기

> long형의 숫자에는 마지막에 대문자 'L’을 붙인다. 소문자 'l’보다 숫자 '1’과의 차이가 커서 가독성이 높아진다.
>

</div>
</details>
<details>
<summary style = " font-size:1.3em;">URL</summary>
<div markdown="1">

### URL

URL은 RESTful API 설계 가이드에 따라 작성합니다.

- HTTP Method로 구분할 수 있는 get, put 등의 행위는 url에 표현하지 않습니다.
- 마지막에 `/` 를 포함하지 않습니다.
- `_` 대신 ``를 사용합니다.
- 소문자를 사용합니다.
- 확장자는 포함하지 않습니다.

</div>
</details>

<br>

### ☀️ Commit Convention

---

<details>
<summary style = " font-size:1.3em;">Rules</summary>
<div markdown="1">

### 1. Git Flow

작업 시작 시 선행되어야 할 작업은 다음과 같습니다.

> issue를 생성합니다.feature branch를 생성합니다.add → commit → push → pull request 를 진행합니다.pull request를 develop branch로 merge 합니다.이전에 merge된 작업이 있을 경우 다른 branch에서 진행하던 작업에 merge된 작업을 pull 받아옵니다.종료된 issue와 pull request의 label을 관리합니다.
>

### 2. IntelliJ

IntelliJ로 작업을 진행하는 경우, 작업 시작 시 선행되어야 할 작업은 다음과 같습니다.

> 깃허브 프로젝트 저장소에서 issue를 생성합니다.생성한 issue 번호에 맞는 feature branch를 생성함과 동시에 feature branch로 checkout 합니다.feature branch에서 issue 단위 작업을 진행합니다.작업 완료 후, add → commit을 진행합니다.remote develop branch의 변경 사항을 확인하기 위해 pull 받은 이후 push를 진행합니다.만약 코드 충돌이 발생하였다면, IntelliJ에서 코드 충돌을 해결하고 add → commit을 진행합니다.push → pull request (feature branch → develop branch) 를 진행합니다.pull request가 작성되면 작성자 이외의 다른 팀원이 code review를 진행합니다.최소 한 명 이상의 팀원에게 code review와 approve를 받은 경우 pull request 생성자가 merge를 진행합니다.종료된 issue와 pull request의 label과 milestone을 관리합니다.
>

### 3. Etc

준수해야 할 규칙은 다음과 같습니다.

> develop branch에서의 작업은 원칙적으로 금지합니다. 단, README 작성은 develop branch에서 수행합니다.commit, push, merge, pull request 등 모든 작업은 오류 없이 정상적으로 실행되는 지 확인 후 수행합니다.
>

</div>
</details>

<details>
<summary style = " font-size:1.3em;">Branch</summary>
<div markdown="1">

### 1. Branch

branch는 작업 단위 & 기능 단위로 생성된 issue를 기반으로 합니다.

### 2. Branch Naming Rule

branch를 생성하기 전 issue를 먼저 작성합니다. issue 작성 후 생성되는 번호와 domain 명을 조합하여 branch의 이름을 결정합니다. `<Prefix>/<Issue_Number>-<Domain>` 의 양식을 준수합니다.

### 3. Prefix

- `main` : 개발이 완료된 산출물이 저장될 공간입니다.
- `develop`: feature branch에서 구현된 기능들이 merge될 default branch 입니다.
- `feature`: 기능을 개발하는 branch 입니다. 이슈 별 & 작업 별로 branch를 생성 후 기능을 개발하며 naming은 소문자를 사용합니다.

### 4. Domain

- `user`, `map`, (`error`, `config`)

### 5. Etc

- `feature/7-user`, `feature/5-config`

</div>
</details>

<details>
<summary style = " font-size:1.3em;">Issue</summary>
<div markdown="1">

### 1. Issue

작업 시작 전 issue 생성이 선행되어야 합니다. issue 는 작업 단위 & 기능 단위로 생성하며 생성 후 표시되는 issue number 를 참조하여 branch 이름과 commit message를 작성합니다.

issue 제목에는 기능의 대표적인 설명을 적고 내용에는 세부적인 내용 및 작업 진행 상황을 작성합니다.

issue 생성 시 github 오른편의 assignee, label을 적용합니다. assignee는 해당 issue 담당자, label은 작업 내용을 추가합니다.

### 2. Issue Naming Rule

`[<Prefix>] <Description>` 의 양식을 준수하되, prefix는 commit message convention을 따릅니다.

### 3. Etc

<aside>
[feat] 약속 잡기 API 구현
<br/>[chore] spring data JPA 의존성 추가

</aside>

---

</div>
</details>

<details>
<summary style = " font-size:1.3em;">Commit</summary>
<div markdown="1">

### 1. Commit Message Convention

`[<Prefix>] #<Issue_Number> <Description>` 의 양식을 준수합니다.

- **feat** : 새로운 기능 구현 `[feat] #11 구글 로그인 API 기능 구현`
- **fix** : 코드 오류 수정 `[fix] #10 회원가입 비즈니스 로직 오류 수정`
- **del** : 쓸모없는 코드 삭제 `[del] #12 불필요한 import 제거`
- **docs** : README나 wiki 등의 문서 개정 `[docs] #14 리드미 수정`
- **refactor** : 내부 로직은 변경 하지 않고 기존의 코드를 개선하는 리팩터링 `[refactor] #15 코드 로직 개선`
- **chore** : 의존성 추가, yml 추가와 수정, 패키지 구조 변경, 파일 이동 `[chore] #21 yml 수정`, `[chore] #22 lombok 의존성 추가`
- **test**: 테스트 코드 작성, 수정 `[test] #20 로그인 API 테스트 코드 작성`
- **style** : 코드에 관련 없는 주석 달기, 줄바꿈
- **rename** : 파일 및 폴더명 수정

</div>
</details>

<details>
<summary style = " font-size:1.3em;">Pull Request</summary>
<div markdown="1">

### 1. Pull Request

develop & main branch로 merge할 때에는 pull request가 필요합니다. pull request의 내용에는 변경된 사항에 대한 설명을 명시합니다.

### 2. Pull Request Naming Rule

`[<Prefix>] <Description>` 의 양식을 준수하되, prefix는 commit message convention을 따릅니다.

### 3. Etc

[feat] 약속 잡기 API 구현
<br/>[chore] spring data JPA 의존성 추가

</div>
</details>


<!--
- #### Infra
  ![Kubernetes](https://img.shields.io/badge/Kubernetes-326CE5?style=flat-square&logo=Kubernetes&logoColor=white)
  ![Grafana](https://img.shields.io/badge/Grafana-F46800?style=flat-square&logo=Grafana&logoColor=white)
  ![Prometheus](https://img.shields.io/badge/Prometheus-E6522C?style=flat-square&logo=Prometheus&logoColor=white)
  ![Elasticsearch](https://img.shields.io/badge/Elasticsearch-005571?style=flat-square&logo=Elasticsearch&logoColor=white)
  ![Logstash](https://img.shields.io/badge/Logstash-005571?style=flat-square&logo=Logstash&logoColor=white)
  ![Kibana](https://img.shields.io/badge/Kibana-005571?style=flat-square&logo=Kibana&logoColor=white)
  ![Filebeat](https://img.shields.io/badge/Filebeat-005571?style=flat-square&logo=Filebeat&logoColor=white)
  ![Vault](https://img.shields.io/badge/Vault-000000?style=flat-square&logo=Vault&logoColor=white)
  ![Kafka](https://img.shields.io/badge/Apache%20Kafka-231F20?style=flat-square&logo=Apache%20Kafka&logoColor=white)
    - Kubernetes는 컨테이너화된 애플리케이션의 배포와 확장을 자동화하여, 대규모 인프라 관리가 용이
    - Grafana, Prometheus는 모니터링과 알림 시스템을 구축해 시스템 성능 및 상태를 실시간으로 추적하고 대응
    - Elasticsearch, Logstash, Kibana, Filebeat(ELK 스택)는 로그 수집, 분석, 시각화를 통해 애플리케이션 상태 및 문제를 쉽게 파악하고 대응
    - Kafka는 대용량의 데이터를 실시간으로 처리하고, 분산 환경에서 높은 확장성과 안정성을 제공하는 메시징 플랫폼
-->
