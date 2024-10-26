
- - -
***FilmFellows README***
# 0. CineMates
![image](https://github.com/user-attachments/assets/7ca3ee07-d56b-4d44-b814-18f39b548499)
# 영화 예매와 사람들 간의 연결을 중심으로 한 사이트
## 프로젝트명
![image](https://github.com/user-attachments/assets/36d486b8-b8e7-41b0-833c-e8a3a32f4d59)
## 팀소개
![image](https://github.com/user-attachments/assets/68b5917d-9218-4bee-850d-1c6450deec5e)
## 팀원소개
![image](https://github.com/user-attachments/assets/82e1f3fd-0a35-4e33-8cca-93bc3798d8bc)
## 기획의도
![image](https://github.com/user-attachments/assets/c90deb35-7e84-44f1-9683-4c886b967972)
## 차별성
![image](https://github.com/user-attachments/assets/104f3773-f434-49fe-a618-1fa4f5024c8c)
- - -
## 프로젝트 개발환경
- DB : Oracle 11g xe 버전 이용
- Intellij IDEA 이용
## 프로젝트 실행 환경 구축(로컬)
1. 오라클 11g 설치 및 application.properties의
```properties
# Oracle Connection Setting
spring.datasource.driver-class-name=oracle.jdbc.driver.OracleDriver
spring.datasource.url=jdbc:oracle:thin:@localhost:1521:xe
spring.datasource.username
spring.datasource.password
```
를 참고하여 로컬에 db 계정 생성
계정 생성 후 권한 부여 및 테이블 생성

테이블, 시퀀스 생성 및 데이터 삽입 스크립트


# 1. 기술스택
![Oracle](https://img.shields.io/badge/Oracle-F80000?style=for-the-badge&logo=oracle&logoColor=white)
![java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)<br>
![html5](https://img.shields.io/badge/html5-%23E34F26.svg?style=for-the-badge&logo=html5&logoColor=white)
![CSS3](https://img.shields.io/badge/css3-%231572B6.svg?style=for-the-badge&logo=css3&logoColor=white)
![java  Script](https://img.shields.io/badge/javascript-%23323330.svg?style=for-the-badge&logo=javascript&logoColor=%23F7DF1E)<br>
![GitHub](https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white)
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-%23005C0F.svg?style=for-the-badge&logo=Thymeleaf&logoColor=white)
![Slack](https://img.shields.io/badge/Slack-4A154B?style=for-the-badge&logo=slack&logoColor=white)<br>
![Kakao](https://img.shields.io/badge/Kakao-FFCD00?style=for-the-badge&logo=Kakao&logoColor=black)
![Naver Maps](https://img.shields.io/badge/Naver%20Maps-03C75A?style=for-the-badge&logo=Naver&logoColor=white)

# 2. 브랜치 전략
## Git Flow 방식
- 팀원 각자의 브랜치를 만들어 개발 진행
- 기능 구현이 완료되면 Main 브랜치에 병합
  ![git_flow](https://github.com/2405PublicjavaDev/sulgilddara/blob/main/img/github_flow.png?raw=true)
# 3. 프로젝트 구조
[프로젝트 구조.pdf](https://github.com/user-attachments/files/17508497/default.pdf)


# 4. 프로젝트 역할 분담
## 이충무:
- 커뮤니티 : 채팅방 개설, 웹소켓 활용한 채팅, 예매 정보 선택 후 예매페이지 이동, 채팅방 리스트 전체, 내채팅방, 검색 페이지 구현 (목록, 카드형 전환)
## 조홍빈:
- 예매: 사용자가 원하는 극장을 선택하고, 상영 시간과 좌석을 빠르게 선택할 수 있는 인터페이스 구현. 결제 페이지로 연결하여 예매 완료 후  예매번호를 발급.
영화 및 극장 상세페이지에서의 예매: 영화 상세페이지 , 극장 상세페이지, 커뮤니티 페이지에서 예매 가능한 좌석 정보와 상영시간을 제공하고, 예매 과정을 간소화.
- 결제: 입력된 예매 정보에 따른 결제를 진행할 수 있는 시스템 구현 . 영화 관람권 결제와 신용카드&간편결제 두가지 방식으로 빠른 온라인 결제 기능 활성화
## 엄태운:
* 회원 가입, 로그인, 회원 정보 수정 등 회원 관련 기능 개발
* 예매 조회, 구매내역 조회, 1:1 문의 기능 등 마이페이지 활동 내역 관리
## 이경학:
### 영화, 배너 정보 관리
- 영화 데이터베이스와 배너를 관리하고, 사용자가 영화 정보를 검색하고 관람평, 평점을 남길 수 있는 데이터베이스 기반 웹 서비스 개발
## 오준석:
- 영화관 극장 페이지,극장 정보관리(추가,수정,삭제) 극장 상영관 관리(추가,수정,삭제) 극장 상영정보 관리(추가,수정,삭제) 신고 페이지, 신고처리 기능
## 김창호:
  - 스토어 페이지 상품 장바구니, 선물하기, 구매(결제) 기능 담당.

# 5. 개발 기간 및 작업 관리
![image](https://github.com/user-attachments/assets/c26fcb5b-3348-4ecd-81b8-d820c8c84c13)

# 6. 페이지별 기능
## 이충무:
### [채팅방 조회]
#### 전체
![전체](https://github.com/user-attachments/assets/b848a7cd-7f25-4382-84db-76daffc1776e)
- 개설된 채팅방을 페이지당 9개씩 볼 수 있습니다.
- 목록형, 카드형 전환이 가능합니다.
#### 검색
![검색](https://github.com/user-attachments/assets/5a9e5363-0391-4653-9722-94eeac7be171)
- 검색결과를 페이지당 5개씩 볼 수 있습니다.
- 목록형으로 확인 가능합니다.
- 검색창으로 검색 시 채팅방이름, 영화, 지역 중 선택하여 검색어를 뒤에 추가해 검색이 가능합니다.
- 가장 많이 등록된 태그 리스트가 조회되며, 각 태그 클릭 시 해당 태그를 사용한 채팅방 검색 결과가 조회됩니다.
#### 내 채팅방
![내 채팅방](https://github.com/user-attachments/assets/5aac98f4-0d58-45c4-9ae3-872aff8fc0a8)
- 목록, 카드형 전환이 가능합니다.
- 내가 만든 채팅방 또는 내가 참여한 채팅방 리스트가 조회되며 색으로 이를 구분할 수 있습니다.

### [채팅방 개설]
![채팅방 개설](https://github.com/user-attachments/assets/bf9a8e1e-883a-4e75-9ead-495f28bc0bba)
- 채팅방 개설 시 카테고리를 선택할 수 있습니다.
- 채팅방 개설 시 예매가능한 영화리스트, 해당 영화를 상영하는 지역의 극장 리스트가 조회되며 이를 선택합니다.
- 채팅방 이름, 태그를 등록해 최종적으로 채팅방을 개설합니다.

### [채팅]
![예매전 채팅](https://github.com/user-attachments/assets/c5ea6349-0980-483e-a52d-9355d5371251)
![채팅방 기능 및 예매](https://github.com/user-attachments/assets/4925d631-ebd0-4840-8b5e-3cfd37cd12c0)
#### 일반 참여자
- 각 채팅방의 참여버튼을 눌러 참여가 가능합니다. (로그인 시 가능)
- 채팅방에 들어가면 채팅방에 참여한 유저들의 프로필을 볼 수 있습니다.
- 최초 입장 시 "..님이 입장했습니다." 의 메세지가 공지됩니다.
- 채팅방 나가기 버튼을 눌러 나갈 시 "..님이 나갔습니다."의 메세지가 공지됩니다.
- 채팅방을 나갈 시 다른 사용자는 대화내용을 확인 가능하지만 나간 사용자는 다시 입장 시 이전 기록을 확인할 수 없습니다.
- 채팅방 페이지에 접속 시 프로필 이미지가 투명해집니다.
- 채팅방 페이지에 접속 시 프로필 이미지가 불투명해집니다.
- 예매 동의여부를 체크할 수 있습니다. 이를 체크해야 방장이 예매 진행이 가능합니다.
#### 방장
- 일반 참여자와 기능은 동일하되 채팅방 삭제, 상영날짜 선택, 예매하기 기능을 추가적으로 사용할 수 있습니다.
- 채팅방에서 대화 후에 상영선택을 눌러 영화를 보기로 한 날짜를 조회하고 시간대를 선택합니다.
- 모든 참여자가 영화관람권을 1개이상, 예매동의하기 버튼을 눌렀을 시 예매하기 버튼을 눌러 예매 (좌석 선택) 페이지로 이동 가능합니다.
- 채팅방 삭제 시 모든 채팅, 채팅방 정보가 삭제됩니다.

## 조홍빈:
### [예매]
- 원하는 영화,상영지역,영화관,예매날짜,상영관과 상영시간을 선택할 수 있습니다. 
- 좌석/인원 버튼 클릭 시 다음 페이지로 이동합니다.
![예매GIF](https://github.com/user-attachments/assets/93e5d7b8-1cab-470a-b173-24f661d2addb)

### [인원/좌석]
- 선택한 영화에 대한 정보 그리고 상영한 정보에 대해 표시합니다.
 - 영화를 볼 인원을 연령에 맞게 선택할 수 있습니다.
 - 선택한 인원에 맞게 좌석 선택이 가능하며 이미 예매가 완료 되거나 사용이 불가능한 좌석은 선택이 불가능합니다.
 - 커뮤니티 페이지에서 이동했을 경우 대화방 인원의 수만큼만 인원 선택이 가능합니다.
   ![좌석 ](https://github.com/user-attachments/assets/23b9091b-4bc9-4f31-9849-6c20ddcc50b3)

 ### [결제]
 - 결제 방식에는 관람권과 신용카드&간편결제 방식이 있습니다.
 - 관람권 결제 시 스토어에서 구매했던 관람권의 개수가 좌석 선택 수 만큼 차감됩니다.
 - 신용카드 결제 시 결제 API 를 통해 간편 결제 혹은 신용카드 결제를 선택하여 결제를 진행합니다.
 - 커뮤니티 페이지에서 이동한 결제 페이지 경우 관람권 결제 방식만 가능합니다.
 - 마이페이지에서 예매번호를 통해 결제 취소가 가능합니다.
  ![결제 1 GIF (1)](https://github.com/user-attachments/assets/5583a730-33e4-411b-8339-1cd5350e2ceb)
 ![결제 2 GIF](https://github.com/user-attachments/assets/871f62dc-b44a-41d6-b8f0-3ecb99beda20)
 ![결제 3 GIF](https://github.com/user-attachments/assets/60620c8a-3ee3-4912-9332-801b7438dfb1)

  ## 엄태운:
 ## [로그인]
* 로그인은 일반 로그인과 소셜(네이버, 카카오) 로그인을 구현하였습니다.
![login](https://github.com/user-attachments/assets/c3fa4f96-c83b-43fe-ad7b-b16e82278e4b)

* 신고 누적으로 인해 정지된 계정은 로그인이 불가능합니다.
![banLogin](https://github.com/user-attachments/assets/61019e06-ee70-48b7-928d-7f12525763a2)


* 소셜 로그인
소셜 로그인은 네이버와 카카오 로그인 API를 사용하여 구현하였습니다. 버튼을 누르면 해당 소셜 로그인창으로 넘어가게 되고 정보제공 동의 후 회원가입이 완료되어 메인 화면으로 이동합니다.
![snsLogin](https://github.com/user-attachments/assets/3c6ce4c1-19cc-4bf2-8ca5-a8c42098dcc5)

* 로그인 성공 화면
로그인을 성공할 경우 우측 상단에 프로필 사진을 확인할 수 있으며 클릭 시 드롭다운이 나옵니다.
![dropdown](https://github.com/user-attachments/assets/2ec9adeb-a8c4-44d1-8d69-e42784dbd4ed)

## [회원가입]
* 회원가입은 @Validation 어노테이션과 ajax를 활용해 실시간으로 유효성 검사가 이루어지도록 구현했습니다.
* 아이디 중복 검사, 비밀번호 일치, 이메일 중복 검사 등 회원의 고유 정보를 입력받을 수 있도록 하였습니다.
* Gmail SMTP를 활용한 이메일 인증을 통해 인증번호 입력이 이루어져야 회원 가입이 가능하도록 구현했습니다.
* 첨부파일 기능을 이용해 회원은 프로필 사진을 등록할 수 있으며, 등록하지 않을 경우 기본 이미지로 보여지도록 했습니다.
![register](https://github.com/user-attachments/assets/c013165d-5bd6-4989-a56f-a0d745e8f4d6)
![email](https://github.com/user-attachments/assets/326d71fd-6332-4034-9134-7effa93b75d4)
![profileImg](https://github.com/user-attachments/assets/8dbf8e49-cc31-4eb8-abc2-8aef8e9fb7f8)


## [아이디 찾기]
* 회원은 이름과 이메일 주소를 입력하여 본인의 아이디를 찾을 수 있습니다.
* 정보를 입력하면 아이디가 화면에 마스킹 처리되어 보여집니다.
![findId](https://github.com/user-attachments/assets/288132e5-a61a-4c97-a44b-066465db80d0)


## [비밀번호 찾기]
* 회원은 아이디와 이메일 주소를 입력하여 비밀번호 재설정 링크를 전송받습니다.
* 이메일의 재설정 링크를 통해 새로운 비밀번호를 설정할 수 있습니다.
![findPw](https://github.com/user-attachments/assets/8fac776b-9d22-464f-b0c4-3c1cbd8e10ea)
![resetPw](https://github.com/user-attachments/assets/0e8687d3-9fa6-4aad-835b-f27bf890e9eb)


## [마이페이지]
1. 예매내역
* 예매된 예매번호를 입력하면 예매 정보를 확인 및 취소할 수 있습니다.
* 쿼리문에서 세 테이블을 조인 및 가공된 데이터가 화면에 보여집니다.
![findReservation](https://github.com/user-attachments/assets/1d879a5e-e904-4f31-8aee-f5d05a966f05)

2. 상품 구매내역
* 기간을 설정하고 조회하면 구매한 상품에 대해 조회할 수 있습니다.
* 상품명을 클릭하면 해당 상품의 상세 페이지로 이동합니다.
![findOrder](https://github.com/user-attachments/assets/cd767466-cbd7-4e6a-afc7-7bd3a49bf99f)

3. 1:1문의내역
* 본인이 등록한 문의를 확인할 수 있으며, 등록 버튼을 통해 글과 첨부파일을 등록합니다.
![qnaList](https://github.com/user-attachments/assets/f64c821a-01c6-4e81-85ad-ecd4ee592b32)
![qnaRegister](https://github.com/user-attachments/assets/e0b836da-4116-43a5-9067-01a4767dee0b)

* 관리자는 관리자 페이지에서 등록된 문의에 대한 답변을 달 수 있습니다.
* 문의 내역은 미답변된 문의가 먼저 보여집니다.
![adminQnaList](https://github.com/user-attachments/assets/8938a2a1-16b7-4ea5-984e-05b97cf45077)
![replyRegister](https://github.com/user-attachments/assets/b6f9380e-faeb-423a-93d1-d23d037b187e)

4. 회원정보수정
* 회원이 본인의 계정 정보와 프로필 사진을 수정할 수 있도록 구현하였습니다.
* 소셜 회원은 정보 수정 대신 일반 회원가입을 할 수 있도록 버튼이 보여집니다.
![update](https://github.com/user-attachments/assets/f7bbf9b4-3cda-4e66-80ce-3fb351e0d927)

5. 회원탈퇴
* 회원은 본인의 비밀번호를 올바르게 입력하면 회원 탈퇴를 진행할 수 있도록 구현하였습니다.
* 소셜 회원은 비밀번호 입력 없이 탈퇴가 가능합니다.
![remove](https://github.com/user-attachments/assets/e99547e1-b5d9-4850-8a21-2386a44f3c32)

## 이경학:
## [메인페이지]
### 배너
-   배너 등록 시 페이지 타입: 메인 페이지, 게시상태: 게시를 선택한 영상 배너만 랜덤으로 자동 재생
-   배너의 상세 보기 클릭 시 해당 영화의 상세페이지로 이동  
![메인-1 배너](https://github.com/user-attachments/assets/285724c8-4fa3-4b60-8b6d-c8b01e958de2)

### 무비차트
-   무비 차트는 현재 1건이라도 예약이 있는 영화의 출력
-   예매율 순으로 출력하며 포스터, 영화명, 예매율, 평점을 출력
-   포스터 클릭 시 해당 영화 상세페이지로 이동
-   슬라이더 버튼 클릭 시 다음 영화를 확인
-   전체 보기 버튼 클릭 시 영화 리스트 페이지로 이동  
![메인-2 무비차트](https://github.com/user-attachments/assets/14d8aaca-6dd3-40c7-a643-9cf2122cd5b9)

### 커뮤니티
-   현재 개설된 채팅방의 리스트를 참여한인원수순으로 정렬하여 출력
-   '<','>'버튼을 눌러 전체 리스트 확인 가능
-   각 채팅방의 이름, 태그, 내용, 인원수, 예매예정정보 등 확인가능
-   태그의 경우 'shift'+ 마우스 휠로 전체 태그 확인 가능
-   클릭 시 채팅방 입장 가능  
![메인-커뮤니티](https://github.com/user-attachments/assets/4aacdb11-37f7-4a29-8c8a-c30c7bc063ae)

---
## [영화페이지]
### 배너
-   배너 등록 시 페이지 타입: 영화 리스트, 게시상태: 게시를 선택한 이미지 배너만 출력하며 5초마다 다음 배너로 넘어갑니다.
-   배너 클릭 시 해당 영화 상세페이지로 이동  
<img width="1678" alt="영화-1 영화_배너" src="https://github.com/user-attachments/assets/00cb675e-b4f2-4606-8f6f-bbaba45a0af7">

### 영화 리스트
-   현재 상영작 : 현재 상영 중인 영화 리스트를 확인할 수 있으며 예매율 순, 관람평 많은 순으로 정렬
-   상영 예정작 : 상영 예정작인 영화 리스트를 확인할 수 있으며 개봉일 순, 예매율 순으로 정렬
-   영화는 영화 포스터, 영화명, 예매율, 개봉일을 출력
-   영화 포스터, 영화명 클릭 시 해당 영화의 상세정보 페이지로 이동
-   영화 리스트는 20개씩 출력하며 더 보기 버튼 클릭 시 다음 영화 20개 출력  
<img width="1678" alt="영화-2 리스트" src="https://github.com/user-attachments/assets/b61ab7c8-efcd-44a3-97bf-8716126c8935">
<img width="1678" alt="영화-3 리스트_더보기" src="https://github.com/user-attachments/assets/9d234c3e-c00e-49f0-8d7b-85839ab4befb">

### 영화 상세 정보
-   상단에서 영화 포스터, 영화명, 개봉 날짜, 관람등급, 러닝타임, 줄거리를 출력
-   영화예매 버튼 클릭 시 해당 영화 선택하여 예매 페이지로 이동
-   상세정보 탭 상단에서 영화정보(감독, 장르, 제작 국가, 출연자)를 출력
-   영화 포스터 클릭 시 모달창으로 출력  
<img width="1678" alt="영화-4  상세정보1" src="https://github.com/user-attachments/assets/749e96b9-262c-4c4f-ba77-0260a836536f">
<img width="1678" alt="영화-5 포스터 모달" src="https://github.com/user-attachments/assets/f6080eb6-03f9-4aa3-be6d-e64b40a34ec3">

-   상세정보 탭 하단에서 트레일러(총개수), 스틸컷(총개수)을 출력  
<img width="1678" alt="영화-6 상세정보2_트레일러, 스틸컷" src="https://github.com/user-attachments/assets/3410024e-d1ec-4f3f-abd7-b84f441cc0d4">

-   스틸컷은 개수가 3개가 넘을 시 슬라이더 버튼 생성
-   트레일러 클릭 시 해당 트레일러 모 들창으로 재생  
<img width="1678" alt="영화-7 트레일러 모달" src="https://github.com/user-attachments/assets/2186cc55-d8e4-4fec-8c38-2e98a4bab79e">

-   스틸컷 클릭 시 해당 스틸컷 모 들창으로 출력  
<img width="1678" alt="영화-8 스틸컷 모달" src="https://github.com/user-attachments/assets/51d5ea9d-d28d-48e2-9946-5af084e85fd8">

### 영화 관람평
-   관람평 섹션에서 리스트 출력 및 관람평 작성, 삭제, 신고 기능 제공  
<img width="1678" alt="관람평-1 관람평 섹션" src="https://github.com/user-attachments/assets/e7baf0f6-383d-4574-973d-60cd7b538357">

-   로그인 후 관람평 작성 버튼 클릭 시 작성창 생성
-   별점, 관람평 작성 기능
-   로그인 아이디의 관람평은 리스트 상단에 고정  
<img width="1678" alt="관람평-2 관람평 작성창" src="https://github.com/user-attachments/assets/c10851f5-c0b6-47a3-a5e6-947278bf1ed6">

-   관람평 버튼 클릭 시 로그인, 중복 작성 확인하여 메시지 전달
-   한 아이디로 한 영화에 하나의 관람평만 작성 가능  
<img width="1678" alt="관람평-3 관람평 중복" src="https://github.com/user-attachments/assets/b108b3f5-c45f-44d9-ab9b-de6f1d1cbf89">

---
## [관리자 - 배너]
### 배너 리스트
-   DB에 저장한 배너 리스트
-   배너 번호, 제목, 페이지 타입, 게시상태 출력
-   등록 버튼 클릭 시 등록 페이지로 이동
-   배너 제목 클릭 시 배너 상세페이지로 이동  
<img width="1678" alt="어드민(배너)-1 배너 리스트" src="https://github.com/user-attachments/assets/0db513a2-696e-4cfc-9ec5-eaf51bfb6060">

### 배너 등록
-   배너 등록 페이지
-   제목, 내용, 배너 Url, 링크 Url, 시작 날짜, 종료 날짜, 페이지 타입, 게시상태를 입력
-   배너 Url 확장자에 따라 영상, 이미지로 확인 가능
-   페이지 타입 : 메인 페이지, 영화 페이지, 기타로 나누어 해당 페이지에서 선택하여 사용
-   게시 상태 : 게시, 기게 시로 나누어 게시를 선택한 배너만 해당 페이지에서 사용  
<img width="1678" alt="어드민(배너)-2 배너 등록" src="https://github.com/user-attachments/assets/6f1c06da-04d9-44f7-bf09-4e29ea25a53c">

### 배너 상세정보
-   현재 등록한 배너의 상세정보
-   각 정보를 수정 후 수정 버튼을 통해 정보 수정
-   삭제 버튼을 통해 배너 삭제  
<img width="1678" alt="어드민(배너)-3 배너 상세정보" src="https://github.com/user-attachments/assets/fb98caa5-982a-43bb-91fc-9cd028b9ad31">

---
## [관리자 - 영화]
### 영화 리스트
-   DB에 저장한 영화 리스트
-   영화 번호, 영화명으로 검색 기능
-   등록 버튼 클릭 시 등록 페이지로 이동
-   각 영화의 영화 번호, 영화명, 개봉일, 상영 상태 정보를 리스트로 출력  
   <img width="1678" alt="어드민(영화)1 영화 리스트" src="https://github.com/user-attachments/assets/f9ce7bd7-3b1e-40e9-b8dc-c52946aa78ed">

### 영화 등록
-   영화 등록 페이지
-   영화명, 포스터 URl, 개봉일, 상영시간, 줄거리, 감독, 배우, 장르 등 영화 기본 정보 입력
-   입력한 포스터 Url 이미지 미리 보기  
<img width="1678" alt="어드민(영화)2 영화 등록" src="https://github.com/user-attachments/assets/1a8bb890-5336-4dcf-9167-f0e6c2030ce1">
<img width="1678" alt="어드민(영화)3 영화 등록2" src="https://github.com/user-attachments/assets/52c32d9b-b9b0-4495-86b2-0c4860773121">

-   트레일러, 스틸컷 등록
-   추가 버튼을 통해 개수에 맞게 추가
-   등록 버튼을 통해 DB에 저장  
<img width="1678" alt="어드민(영화)4 영화등록3" src="https://github.com/user-attachments/assets/e727c70a-2ced-40ac-9a49-373ab4bceb5c">

### 영화 상세정보
-   DB에 저장한 영화 정보 출력 및 수정
-   트레일러, 스틸컷 개별로 삭제
-   수정 버튼을 통해 수정한 영화정보 DB에 저장
-   삭제 버튼을 통해 해당 영화정보 삭제  
<img width="1678" alt="어드민(영화)5 영화정보" src="https://github.com/user-attachments/assets/ba39d7ea-f14c-4208-899b-3bf04810a589"><img width="1678" alt="어드민(영화)6 영화정보2" src="https://github.com/user-attachments/assets/10474c96-db4f-4541-a0a2-ac73558eadbe">
<img width="1678" alt="어드민(영화)7 영화정보3" src="https://github.com/user-attachments/assets/6f812a02-b7b4-4f4e-a927-f277f3d52bc8">


  ## 오준석:
  ### 1. 극장 메인페이지
  #### 1-1 극장 선택  
  ![image](https://github.com/user-attachments/assets/a5feaba7-607b-48d5-bc04-ab8537b302a1)
  
  - 지역을 8개로 분리하여 극장을 보여줄수있도록 하였읍니다.

  - 기본으로 들어왔을시 서울, 강남지점을 선택해서 들어와집니다.
  
  - 자기가 원하는 극장을 클릭시 아래에 해당 극장정보와 상영정보가 나옵니다.

  #### 1-2 극장 정보
  ![image](https://github.com/user-attachments/assets/b3c822b7-ccab-430b-89f9-7634a242512d)
  
- 해당 극장 상세정보가 출력 됩니다.

- 주소, 전화번호, 상영관수, 총좌석수

- 위치/길찾기 를 통해서 교통정보와 위치를 확인할수 있습니다.
#### 1-3 상영 정보
![image](https://github.com/user-attachments/assets/3d669fb5-f5ba-4be3-90e7-e99690164df4)

- 해당 극장의 해당일자의 상영정보를 보여줍니다.

- 상영일자 눌렀을시 해당 일자의 상영 일정을 보여줍니다.

- 로그인상태에서 눌렀을시 해당 정보를가지고 예매페이지로 이동합니다.

### 2. 위치 길찾기(팝업)
![image](https://github.com/user-attachments/assets/148f2257-9f0e-428a-84b0-b52efb3fa1bd)
![image](https://github.com/user-attachments/assets/ccd43b1f-cf8f-4adf-ba9f-cc1d00027e42)

- 해당 극장의 간단한 약도와 대중교통안내, 주차를 안내 해줍니다.

- 길찾기눌렀을시 해당 주소로 네이버 길찾기로 이동합니다.

### 3. 관리자 페이지
#### 3.1 관리자 메인 페이지
![image](https://github.com/user-attachments/assets/faf64ad5-7bbb-426a-9113-13afeeb810e7)

- 현재 극장정보를 출력해줍니다.

- 상영관리를 하기위해서는 해당 지역과, 해당 극장을 클릭하면 해당 극장의 상영관리로 이동 합니다.

- 극장관리를 눌렀을시 전체 극장을 관리하는 페이지로 이동합니다.

#### 3.2 극장 관리 페이지
![image](https://github.com/user-attachments/assets/9a3b9dc3-ab5a-4e33-b0c0-ef2343b3ade5)
![image](https://github.com/user-attachments/assets/f75d378d-7d5c-4df1-a45d-0fa261da3de2)
![image](https://github.com/user-attachments/assets/77fafea0-4003-419f-992c-d88bf24a88ac)

- 전체 극장 리스트를 보여줍니다.
  
- 새로운 극장을 추가할수있습니다.
  
- 현재 극장 정보를 수정, 삭제 할수있습니다.

#### 3.3 상영 관리페이지
![image](https://github.com/user-attachments/assets/98b36b4c-ee9a-4bd8-b8ab-ad2086feb539)
![image](https://github.com/user-attachments/assets/1243870c-df45-47e1-88d6-cd8b0cef741e)
![image](https://github.com/user-attachments/assets/d317ad26-396d-4614-9200-3b225e1ce540)
![image](https://github.com/user-attachments/assets/1ec7258f-f341-4987-bd43-d398f3243f9e)


- 해당 극장의 오늘 상영정보와, 전체 상영 일정을 보여줍니다.

- 상영정보를 추가 할수 있습니다.

- 상영 정보를 수정,삭제 할수있습니다.

- 상영관 관리 페이지로 이동 가능합니다.

#### 3.4 상영관 관리페이지
![image](https://github.com/user-attachments/assets/838bbbdb-4d5f-4644-a17f-10a8d328609a)
![image](https://github.com/user-attachments/assets/a5444c6d-125e-46c5-b20d-d435ab45fb87)
![image](https://github.com/user-attachments/assets/85d6877f-eb1d-4363-b488-47e89527c55c)

- 해당 극장의 상영관 정보를 보여줍니다.

- 해당 극장의 상영관정보를 추가 할수있습니다.
  
- 해당 극장의 상영관 정보를 수정, 삭제할수있습니다.

### 4.신고

#### 4.1 신고 페이지
![image](https://github.com/user-attachments/assets/fd56c043-1c3c-48dc-a488-de3f5413ccb5)
![image](https://github.com/user-attachments/assets/d23b6a66-9e47-4b1f-a120-c953a7e5d119)
- 신고 를 눌렀을시 해당 팝업이 등장하며 신고유형에 맞는 내용을 선택하여 신고 할수 있습니다.

#### 4.2 신고 관리페이지
![image](https://github.com/user-attachments/assets/35295d9d-625c-4f83-acb3-b44ef0bdf7de)

- 관리자는 해당 신고 건에 대해서 수락,거절을 할수 있으며 수락시 신고횟수가 1회 증가하며, 거절시에는 횟수 증가 없이 해당 건은 완료 처리 됩니다.
  
![image](https://github.com/user-attachments/assets/694ec679-d5eb-4987-bac1-33069022c250)

- 3회이상 신고시 재제 페이지인 날짜 선택이 뜨며 해당 날짜 선택히 해당 유저는 재제기간동안 로그인을 제한 합니다.
  
![image](https://github.com/user-attachments/assets/d1a07799-d07f-48f3-ac2e-ada8ecc1f093)

- 완료 처리 한건에 대해서 볼수있습니다.

### 5. 관리자 상품관리 페이지

#### 5.1 상품관리메인페이지
![image](https://github.com/user-attachments/assets/57200e64-ae28-4c05-b9e7-8ce0bb1dc646)
![image](https://github.com/user-attachments/assets/a497e45a-d554-4777-958e-4ea730c4147b)
![image](https://github.com/user-attachments/assets/0f73255b-faad-415d-84d7-30f4fc7f47ed)

- 등록된 전세 상품 리스트를 볼수있습니다.
  
- 상품을 추가 할수있습니다.
  
- 상품을 수정,삭제 할수있습니다.



  ## 김창호:
### 스토어 모든 페이지 공통 카테고리
![모든페이지 공통 카테고리](https://github.com/user-attachments/assets/b8ee2415-f030-4581-89dd-f0076ed5d0dd)
- 스토어 상품을 카테고리 별로 나누어서 내비게이션바를 만들었습니다.
- 모든 스토어 페이지에서 언제든지 상품에 접근할 수 있도록 했습니다.
### 스토어 메인 페이지
![스토어 메인 상품확인](https://github.com/user-attachments/assets/c1b9a0e7-2db8-4c67-8f25-72fc34a61b45)
- 카테고리별로 상품들을 확인할 수 있습니다.
- ![스토어 메인 더보기](https://github.com/user-attachments/assets/9e0e5b5c-df72-45d6-9de7-bbc4c07c22c7)
- 카테고리마다 더보기 버튼을 만들었고 클릭하면 해당 카테고리 페이지로 이동합니다.
- ![스토어 메인 상세보기](https://github.com/user-attachments/assets/0370bbf4-7d83-4491-95d2-f8d6fe751d7a)
- 각 상품마다 상세보기 버튼을 만들었고 클릭하면 해당 상품의 상세 페이지로 이동합니다.
### 스토어 카테고리 페이지
![스토어 카테고리](https://github.com/user-attachments/assets/45141310-03e5-47cb-aca6-ea046795f007)
- 해당 카테고리의 상품들을 확인할 수 있습니다.
- 상품 이미지에 마우스를 갖다대면 장바구니, 선물하기, 구매하기 아이콘이 나타납니다.
- 장바구니 아이콘을 클릭하면 해당 상품을 장바구니에 담을 수 있습니다.
- 선물하기 아이콘을 클릭하면 선물하기 페이지로 이동합니다.
- 구매하기 아이콘을 클릭하면 구매 페이지로 이동합니다.
### 스토어 상세 페이지
![스토어 상세1](https://github.com/user-attachments/assets/7137d4e8-71a8-4ae5-9266-53f35b6a9e3e)
- 해당 상품의 상세정보를 확인할 수 있습니다.
- 상품명, 상품이미지, 금액, 상품구성을 확인하고 수량(+, -) 버튼을 눌러서 구매수량을 선택할 수 있습니다.
- 구매수량에 따라 총 구매금액이 실시간으로 업데이트됩니다.
- 장바구니, 선물하기, 구매하기 버튼이 있습니다.
- ![스토어 상세2](https://github.com/user-attachments/assets/1689c7d0-5eeb-46e1-abd9-2f8dd1d1b57a)
- 장바구니 버튼을 클릭하면 해당 상품을 장바구니에 담을 수 있습니다.
- 선물하기 버튼을 클릭하면 선물하기 페이지로 이동합니다.
- 구매하기 버튼을 클릭하면 구매 페이지로 이동합니다.
### 장바구니 페이지
![스토어 장바구니1](https://github.com/user-attachments/assets/524db3ac-31c2-4c1d-9f5b-e0110f65a352)
- 장바구니 페이지에서는 전체상품을 선택할 수 있는 체크박스가 있고 각 상품별로 선택할 수 있는 체크박스가 있습니다.
- 체크박스를 통해 선택한 상품들의 총 상품금액, 총 할인금액, 총 결제 예정금액이 나타나고, 수량 변경 시 실시간으로 업데이트됩니다.
- ![스토어 장바구니2](https://github.com/user-attachments/assets/e5fe6597-0a60-4e76-aea3-098729add92a)
- 각 상품마다 수량(+, -)버튼을 눌러서 구매수량을 선택할 수 있고, 구매수량에 따라 구매금액이 실시간으로 업데이트됩니다.
- ![스토어 장바구니3](https://github.com/user-attachments/assets/9b11dc2f-855d-4f37-b02b-4dfc553eb75d)
- 각 상품마다 삭제버튼이 있고 버튼 클릭 시 해당 상품을 장바구니 리스트에서 삭제합니다.
- ![스토어 장바구니4](https://github.com/user-attachments/assets/a319f8e5-9400-4c76-9042-eb8c01ffa33f)
- 선택상품 구매, 전체상품 구매 버튼을 통해 원하는 상품을 선택해서 구매페이지로 이동합니다.
### 선물하기 페이지
![스토어 선물하기](https://github.com/user-attachments/assets/da9e9cb2-dfc6-43c3-9849-31e7d6f6a859)
- 상품명, 금액, 수량을 확인할 수 있습니다.
- 선물하기 페이지에서는 선물 받는 사람에게 메시지를 전달할 수 있도록 메시지 입력 인풋창이 있습니다.
- 선물 받는 사람에 이름과 전화번호를 입력하고 확인버튼을 누르면 해당 회원이 존재하는 회원인지 체크합니다.
- 총 상품금액, 총 할인금액, 총 결제 예정금액을 확인하고 다음버튼을 누르면 구매페이지로 이동합니다.
### 구매 페이지
![스토어 구매](https://github.com/user-attachments/assets/04cb6703-e502-4ade-8da5-c3aeff4be8db)
- 상품명, 상품금액, 수량, 결제예정금액을 확인하고
- 총 상품금액, 총 할인 금액, 총 결제 예정금액을 확인할 수 있습니다.
- 주문자 정보에서 이름, 휴대전화 번호, 이메일주소를 확인할 수 있습니다.
- 결제수단으로 KG페이가 자동으로 설정되어 있습니다.
- 결제하기 버튼을 클릭하면 결제창이 뜨고, 원하는 결제수단을 선택해서 결제할 수 있습니다.
# 7. 프로젝트 후기
## 이충무:
- 타임리프 레이아웃을 활용해 타임리프의 장점을 최대한 활용하면서 효율적인 프로젝트 관리가 가능하다는 것을 배웠습니다.
실시간 채팅 기능을 구현하는 과정에서 많은 도전이 있었는데 특히 웹소켓을 이용해 실시간 데이터를 주고받는 구조를 설계했지만, 서버 측에서 예기치 않게 웹소켓 연결이 자주 끊기는 현상이 발생하면서 상당한 어려움을 겪었습니다. 
그래도 이 과정에서  STOMP와 SocketJS 같은 웹소켓 라이브러리의 존재를 알게 되었고 경험했던 것들을 바탕으로 더 나은 채팅 기능 구현을 할 수 있을 거란 자신감을 얻었습니다.
처음 팀을 이끌어보는 입장에서 부족한 점도 많았고, 특히 의사소통과 일정 관리 부분에서 아쉬움이 남습니다. 지나고 보니 더 잘할 수 있었던 부분이 많다는 생각이 듭니다. 하지만 이 경험을 통해 커뮤니케이션 방식과 리더십에 대해 많은 것을 돌아볼 수 있는 시간이었던 것 같습니다.
## 조홍빈:
- 이번에 세미와 비슷한 기능을 구현하게 되었는데 가장 어려웠던 점은 세미 프로젝트에서는 사용하지 않았던 AJAX 로 비동기 처리하는 과정을 이번에 제대로 진행해본 것과 영화 좌석 구성이나 다른 멤버와의 연결되는 데이터 값들을 처리하는 걸 자바스크립트로 해결하는 과정이 가장 험난했던 것 같습니다.
프로젝트 단 한번의 경험이 기초 설계의 속도부터 확실히 차이를 나게 하여 전체적으로 여유를 가지고 진행할수 있었고 세미와 파이널 두가지 프로젝트에서 비슷한 파트를 반복해서 하면서 세미때보다 기능적으로 성능이 좋고 전체적으로 세련되게 잘 만들어 스스로가 세미 프로젝트에 비해 발전되었다는 것을 느꼈습니다.
이번 프로젝트에서도 써보지 않았던 방식들을 사용하면서 공부가 되었고 만족스러운 결과물과 함께 의미있는 시간을 가진 것 같습니다.
## 엄태운:
#### 좋았던 점
* 당초 계획했던 기능을 모두 구현하게 되어 개인적으로 만족스러운 프로젝트였다. 세미 프로젝트를 한번 하고 나니 문서 작업에 소요되는 시간을 단축시켜 기능 구현에 많은 시간을 투자할 수 있었고, 덕분에 빠짐 없이 작업을 잘 마칠 수 있었다.
* 스스로 이메일 API와 소셜 로그인 API를 구현했다는 것에 뿌듯함을 느꼈다. 소셜 로그인의 경우 작동 원리를 이해하기까지 다소 시간이 걸렸지만 완전히 이해하고 나니 스스로 리팩토링도 하며 조금이나마 효율적으로 구성할 수 있었다.
* ajax의 이해도를 높일 수 있는 기회가 되었다. 회원 관리 대부분의 기능을 비동기 방식으로 구현하게 되어 ajax를 주로 사용하였고, 사용자가 값을 입력하는 여러 상황에 대비해 어떻게 화면에 표시해주어야 할지 스스로 많이 고민하는 시간을 가졌다. 그러다보니 세미 프로젝트 때는 끝날 때까지 낯설었던 ajax가 이제는 조금 익숙해진 것 같다.

#### 아쉬웠던 점
* 프로젝트가 종료될 시점에 뒤늦게 Spring Security에 대해 관심을 갖게 되어 구현을 시도해보지 못하고 마무리했다. 프로젝트 초반에는 담당한 기능을 잘 마칠 수 있을지 확신을 갖지 못해 욕심을 내지 않았는데 미리 계획했다면 일정을 조율해서 시도해볼만 하지 않았을까 하는 아쉬움이 남는다. 관심을 갖고 개인적으로 공부 해보고 싶다는 생각이 들었다.
* 생각보다 초반 문서 설계가 부실했던 것 같다. 물론 처음 구현해보는 기능이라 어떤 메소드와 클래스가 필요할지 문서 단계에서는 정확히 유추할 수 없었지만, 구현 중간중간에 테이블을 새로 생성하고, 클래스도 만들다보니 흐름이 좀 끊기는 듯한 느낌이 들었다. 추후에는 문서를 더 철저하게 구성해서 매끄러운 작업 과정이 될 수 있도록 해야겠다. 
## 이경학:
- 이번 프로젝트를 진행하면서 조회, 등록, 수정, 삭제 기능을 반복적으로 구현함으로써 기본적인 웹 애플리케이션 개발 능력을 확실히 다질 수 있었고, 특히 세미 프로젝트에서 어려움을 겪었던 자바스크립트와 비동기 처리에 대한 이해도가 이전보다 크게 나아져 이를 더 효율적으로 활용할 수 있게 되었으나, 여러 테이블의 데이터를 다양한 조건에 따라 조회하는 복잡한 쿼리문 작성에 있어서는 여전히 어려움을 느꼈으며, 이는 앞으로 더 효율적인 데이터베이스 처리 능력을 키우기 위해 중점적으로 학습해야 할 부분이라고 생각하게 되었습니다.
## 오준석:
- 이번 프로젝트에서는 자바스크립트의 비중이 매우 컸던 것도 큰 도전이었습니다. 
극장 관리와 관련된 데이터들(영화,예매)이 서로 연결되고, 이를 자바스크립트로 다루는 과정에서 특히 어려움을 느꼈습니다. 하지만 이러한 과정을 통해 자바스크립트의  처리에 대해 더 깊이 이해하게 되었고, 점점 더 효율적으로 문제를 해결할 수 있게 되었습니다.
자바스크립트에 익숙해질수록 기능 구현 속도가 크게 향상될 것이라는 확신도 들었습니다. 앞으로는 이 경험을 바탕으로 더 복잡한 기능도 자신 있게 구현할 수 있을 것 같습니다.

## 김창호:
- 파이널 프로젝트를 하면서 느낀점은 제가 개발하고자 하는 방향성과 구조에 대해서 깊게 고민하고 이해해야 테이블 셋팅을 원활하게 할 수 있고, 구조가 깔끔해져서 코드를 짜기가 편하다는 것을 느꼈습니다. 프로젝트 기간중 테이블과 VO구조를 여러번 수정하다보니 많은 코드들을 수정해야 했고, 효율적으로 시간을 사용하지 못했던 부분이 있었습니다. 또한 자바스크립트를 이용하는 것의 중요성에 대해서 크게 느꼈고 이 부분에서 약간의 발전이 있었다고 생각합니다. 앞으로 자바스크립트를 좀 더 공부하면 기능을 구현하는 데 있어서 이번 프로젝트보다 원활하게 진행할 수 있을 것 같습니다.
    

# 프로젝트 산출문서
- 기획보고서
- 요구사항정의서
- 유스케이스
- 플로우차트
- 와이어프레임
- DB설계
- 클래스다이어그램
- 시퀀스다이어그램
- 최종보고서
- 프로젝트 소스 DB
- 시연영상
