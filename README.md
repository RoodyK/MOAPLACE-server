# MOAPLACE
공연 및 공연관리와 공연장 대관이 가능한 공연사이트를 제작한다.

## 사용환경
- JAVA(JDK 11)
- STS 3.9.11
- Tomcat(9버전)
- VScode (Axios 데이터 전달)
- Oracle Cloud (19c) / SQLdeveloper
- ERDcloud

## 프로젝트 관리
- Jira (프로젝트 관리)
- Git / Github
- Slack
- Whimsical (와이어프레임)
- Google SpreadSheet
- diagrams(draw.io)

## 프로젝트 개요
주제선정 -> 요구분석사항서 작성 -> DB 모델링(정규화) -> 와이어프레임제작 -> VSCode로 Vue.js CLI를 통한 CREATE app 생성 및 기본 설정 -> 프론트엔드 작업 -> Oracle 테이블 및 시퀀스 생성 -> STS설정(Maven 모듈 dependency) -> 백엔드 작업 및 프론트엔드 백엔드 데이터 연결(@RestController / Axios) -> 전체 기능 종합 및 디버깅

**고객**
- 로그인, 회원가입 기능 사용
- 마이페이지 이용가능
- 공연목록 및 일정 확인
- 공연 관심목록 등록 및 관람평 작성
- 절차에따른 공연 예매 및 대관 신청 가능
- 공지사항 및 공연장에 대한 정보와 위치 확인가능
- 공연에대한 유의사항 및 혜택 확인
- FAQ 및 관리자에게 1:1문의 신청 가능

<br/>

**관리자**
- 공연 매출관리
- 회원 관리
- 공연 일정 및 공연 등록, 예매관리
- 대관 신청관리
- 공지사항 및 문의관리

## 프로젝트 ER 다이어그램
![moaplace-erd](https://user-images.githubusercontent.com/95058915/189040007-c7d8aae4-cb56-4637-80f1-0941c78fbf8c.png)


## 사용 API

- [카카오 로그인 API, 카카오 MAP API](https://developers.kakao.com) <br/>
- [구글차트 API](https://developers.google.com/chart) <br/>
- [다음postcode API](https://postcode.map.daum.net/guide) <br/>
- [아임포트 API](https://www.iamport.kr/) <br/>

## 주요기능
- 회원 기능은 JWT 토큰을 사용한 인증 처리
- javax.mail 이메일 인증처리
- junit4, Talend API를 사용한 단위테스트 진행
- 보안과 관련된 내용은 properties파일로 분리
- MVC 모델링 적용
