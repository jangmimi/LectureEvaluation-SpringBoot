# LectureEvaluation-SpringBoot
스프링부트를 활용한 인프런 강의 평가 웹 사이트입니다.

기존에 JSP로 구축한 강의 평가 웹 사이트를 Spring Boot로 재구현하였으며

인프런 강의 데이터 크롤링 및 SNS 로그인 등 추가적인 기능들을 통해 

전반적인 기능 및 코드를 개선하여 구축하고 있습니다. (~진행중)<br><br>

<h2>JSP프로젝트를 Spring Boot로 바꿔서 재구축한 이유?</h2>

**모던한 웹 애플리케이션 개발**
- 간편한 설정 및 개발 (ex. 데이터베이스 연결, 내장형 서버)
- JPA를 활용한 효율적인 쿼리 수행
- 기존 코드 리팩토링을 통해 가독성 향상, 유지보수성 향상 
- Spring Boot의 다양한 기능 활용 (ex. test, log)

<br>
<h2>구현 기능</h2>

- 회원가입 / 회원탈퇴
- 이메일 인증
- 로그인 / 로그아웃
- 회원정보 수정
- 강의평가 등록 / 삭제 / 목록조회 / 수정
- 페이징 처리
- 필터, 검색어에 따른 검색 기능
- 강의평가 페이지 내 신고 기능 (관리자 이메일 발송)
- 인프런 강의 데이터 크롤링
- 강의목록 페이지<br>
  


<br><hr>

<h2>인프런 강의목록 페이지</h2>

![image](https://github.com/jangmimi/LectureEvaluation-SpringBoot/assets/133731745/79cf70a2-64ef-4109-b8e1-eeac2ba78cf9)

- 인프런 사이트의 강의목록을 데이터 크롤링해서 표시<br>
- 이미지 또는 제목 클릭 시 해당 강의 페이지로 새 창 이동<br>
- 로그인 상태에서만 평가등록 가능

<h2>평가등록 모달창</h2>

![image](https://github.com/jangmimi/LectureEvaluation-SpringBoot/assets/133731745/d8dbf661-d8ea-4ddb-b419-5c617f8fd5b4)

- 선택한 강의의 제목, 강사명을 표시<br>
- 강의평가 내용 저장

<h2>강의평가 페이지</h2>

![image](https://github.com/jangmimi/LectureEvaluation-SpringBoot/assets/133731745/c5aa89d2-e378-40de-bbd1-0dfd26bd0d4b)

- 저장된 강의평가 조회 목록<br>
- 로그인 시에만 추천, 수정, 삭제 버튼 표시<br>
- 본인이 쓴 강의평가 글에만 수정 삭제 버튼 표시
- 추천 기능 (중복체크 후 추천취소까지 구현 예정*)

<h2>로그인 페이지</h2>

![image](https://github.com/jangmimi/LectureEvaluation-SpringBoot/assets/133731745/759a969a-382d-4f95-bd2a-1d6fffc0b249)

<h2>회원가입 페이지</h2>

![image](https://github.com/jangmimi/LectureEvaluation-SpringBoot/assets/133731745/400b86aa-6f09-47d3-80fe-2410dd7a6058)

<h2>회원정보수정 페이지</h2>

![image](https://github.com/jangmimi/LectureEvaluation-SpringBoot/assets/133731745/da141e06-8af0-45d4-a6a4-52fe43cdb57c)

- 로그인한 사용자의 정보를 표시<br>
- 이메일은 수정불가

