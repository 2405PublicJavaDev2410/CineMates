- - -
깃 허브 리드미 작성 시에 올려놓은 사진에 맞게 양식 맞춰서 작성할 예정입니다. "팀 깃허브 리드미 기본 틀 확인하시고" "각자 velog 나 개인 깃 허브 repository 에 형식에 맞게 작성"해서 리드미에 한번에 올릴 예정입니다. 
내용 작성시 (프로젝트 기능 소개 제외) 제목은 # 3개로 제목 틀 구성하시고 내용은 그냥 작성하시면 되겠습니다. 프로젝트 기능 소개의 경우 #2개로 기능 이름 작성 하시면 될 것 같습니다.
- - -


- - -
**FilmFellows README**

프로젝트 명 : CineMates
영화 예매와 사람들 간의 연결을 중심으로 한 프로젝트를 상징합니다. 
사용자들이 영화를 통해 새로운 친구를 만나고, 함께 영화를 즐길 수 
있는 커뮤니티를 구축하는 데 중점을 둔다는 의미를 전달합니다.
- - -

# 1. 개발환경<br>
![GitHub](https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Slack](https://img.shields.io/badge/Slack-4A154B?style=for-the-badge&logo=slack&logoColor=white)<br>
![JavaScript](https://img.shields.io/badge/javascript-%23323330.svg?style=for-the-badge&logo=javascript&logoColor=%23F7DF1E)<br>
![HTML5](https://img.shields.io/badge/html5-%23E34F26.svg?style=for-the-badge&logo=html5&logoColor=white)
![CSS3](https://img.shields.io/badge/css3-%231572B6.svg?style=for-the-badge&logo=css3&logoColor=white)
![Kakao](https://img.shields.io/badge/Kakao-FFCD00?style=for-the-badge&logo=Kakao&logoColor=black)
![Naver Maps](https://img.shields.io/badge/Naver%20Maps-03C75A?style=for-the-badge&logo=Naver&logoColor=white)

# 2. 브랜치 전략
## Git Flow 방식
- 팀원 각자의 브랜치를 만들어 개발 진행
- 기능 구현이 완료되면 Main 브랜치에 병합
  ![git_flow](https://github.com/2405PublicJavaDev/sulgilddara/blob/main/img/github_flow.png?raw=true)
# 3. 프로젝트 구조

# 4. 프로젝트 역할 분담
  ## 이충무:
    
  ## 조홍빈:
  - ㅁㄴㅇ
  ## 엄태운:
    - 
  ## 이경학:
    - 
  ## 오준석:
- 영화관 극장 페이지,극장 정보관리(추가,수정,삭제) 극장 상영관 관리(추가,수정,삭제) 극장 상영정보 관리(추가,수정,삭제) 신고 페이지, 신고처리 기능
  ## 김창호:
    - 

# 5. 개발 기간 및 작업 관리
![image](https://github.com/user-attachments/assets/c26fcb5b-3348-4ecd-81b8-d820c8c84c13)

# 6. 페이지별 기능
  ## 이충무:
    - 
  ## 조홍빈:
    - 
  ## 엄태운:
    - 
  ## 이경학:
    - 
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
    - 
# 7. 프로젝트 후기
  ## 이충무:
    - 
  ## 조홍빈:
    - 
  ## 엄태운:
    - 
  ## 이경학:
    - 
  ## 오준석:
- 이번 프로젝트에서는 자바스크립트의 비중이 매우 컸던 것도 큰 도전이었습니다. 
극장 관리와 관련된 데이터들(영화,예매)이 서로 연결되고, 이를 자바스크립트로 다루는 과정에서 특히 어려움을 느꼈습니다. 하지만 이러한 과정을 통해 자바스크립트의  처리에 대해 더 깊이 이해하게 되었고, 점점 더 효율적으로 문제를 해결할 수 있게 되었습니다.
자바스크립트에 익숙해질수록 기능 구현 속도가 크게 향상될 것이라는 확신도 들었습니다. 앞으로는 이 경험을 바탕으로 더 복잡한 기능도 자신 있게 구현할 수 있을 것 같습니다.

  ## 김창호:
    - 
    
