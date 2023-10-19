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
- 강의평가 등록 / 삭제 / 목록조회 / 목록페이징 (수정 기능 추가 예정*)
- 필터, 검색어에 따른 검색 기능
- 강의평가 신고 시 관리자 이메일 발송
- 인프런 강의 데이터 크롤링
- 인프런 강의와 강의평가 연결 (~진행중)<br>
  강의목록 페이지<br>
  1. 평가등록 클릭 시 해당 강의 제목, 강사명 표시<br>
  2. 평가 저장 기존 로직에서 수정*<br>
  


<br><hr>

<h2>인프런 강의목록 페이지</h2>

![image](https://github.com/jangmimi/LectureEvaluation-SpringBoot/assets/133731745/e6d4f281-49f5-4353-8f6b-0dc7b875cafe)

<h2>평가등록 모달창</h2>

![image](https://github.com/jangmimi/LectureEvaluation-SpringBoot/assets/133731745/b6493a07-f5ed-4289-b883-012077b6f8c5)



<h2>강의평가 페이지</h2>

![image](https://github.com/jangmimi/LectureEvaluation-SpringBoot/assets/133731745/c7780b0e-8e52-41a1-9fc4-3efb4162fad7)


