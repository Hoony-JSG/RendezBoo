# Rendez-Boo 

![타이틀 이미지](https://user-images.githubusercontent.com/109324637/219056160-a5b1a9cf-bcf5-4835-bc4e-e295666ce5e1.png)

## 0. 프로젝트 개요

✔ 프로젝트명 : Rendez-Boo(랑데부)

✔ 한줄 소개 : Web RTC를 기반으로 한 1대1 / 3대3 블라인드 데이팅 웹 서비스

✔ 개발 기간 : 23.01.09 ~ 23.02.17 (6주)

✔ 팀원 : 이홍주 김명준 안예나 유선준 이재원 정훈

✔ 사용 기술스택 : SpringBoot, React, Docker, AWS + (4. 시스템 아키텍처 참고)

## 1. 서비스 및 기능 소개

✔ 1대1 미팅 
- 임의의 상대방과 1대1 미팅이 진행됩니다.
- 처음에 블라인드로 시작했다가 시간이 지날수록 (매 1분마다) 얼굴이 조금씩 공개됩니다.
- 미팅 중에 상대방이 느낀 감정을 알 수 있습니다.
- 정해진 시간이 끝나면 미팅이 종료되고 상대방과 친구를 맺을 수 있습니다.

✔ 3대3 미팅
- 방을 생성하거나 기존에 생성된 방에 입장해서 3대3 미팅을 진행할 수 있습니다.
- 미팅은 블라인드 상태로 진행되고 게임을 통해서 상대방의 얼굴이 공개됩니다.
- 게임을 일정 횟수 이상 하게 되면 사랑의 작대기를 통해 마음에 드는 상대를 지목할 수 있습니다.

✔ 채팅
- 1대1 혹은 3대3 미팅을 통해서 친구가 된 상대와 채팅을 나눌 수 있습니다.

✔ 프로필
- 1대1 미팅에서 상대방이 느낀 감정 데이터를 종합해 다이어그램 형태로 볼 수 있습니다.
- 활동 내역에 따른 뱃지가 지급됩니다.

## 2. 서비스 화면


### 로그인 / 가입

![초기화면](https://user-images.githubusercontent.com/109324637/219236954-4029e47e-75cd-4266-8d85-032a7e3d0eb3.PNG)

![소셜카카오](https://user-images.githubusercontent.com/109324637/219395488-0db5af40-d62b-43d5-bfbe-21a8e2a7877a.PNG)

![회원가입1](https://user-images.githubusercontent.com/109324637/219395766-e28705de-4753-44fc-89f6-671c697afcc1.PNG)

![회원가입2](https://user-images.githubusercontent.com/109324637/219395828-1ac35db5-cc76-44fb-935c-5616c6f2a502.PNG)


### 메인화면

![메인페이지](https://user-images.githubusercontent.com/109324637/219395880-35db11f5-d8b6-409f-99a3-74f96250feec.PNG)


### 1대1 미팅

![1대1입장](https://user-images.githubusercontent.com/109324637/219237368-0a58e223-0304-40c1-98fd-4221e854441a.PNG)

![1대1대기](https://user-images.githubusercontent.com/109324637/219237555-3634fc07-36a9-4c6a-a7cc-14e7c91b67f5.PNG)

![1대1페이즈1](https://user-images.githubusercontent.com/109324637/219237641-ecf1ee7f-e904-4200-b079-bad52460c6d1.PNG)

![1대1페이즈2](https://user-images.githubusercontent.com/109324637/219237860-a2623495-1dbb-45ea-a64c-d0c1c4218a9c.PNG)

![1대1페이즈3](https://user-images.githubusercontent.com/109324637/219237951-49bbeb46-a08b-427e-a1c2-d268283f0548.PNG)

![1대1친구신청](https://user-images.githubusercontent.com/109324637/219238332-e6449ee5-745a-441f-b950-0627280b015a.PNG)

![1대1미팅종료](https://user-images.githubusercontent.com/109324637/219238422-ae8a0530-c423-49f1-87f0-a0a0e15c6007.PNG)

### 3대3 미팅

![3대3대기방](https://user-images.githubusercontent.com/109324637/219396059-3f85128b-d3dd-40e0-8afb-8e0ba0f52b8f.PNG)

![3대3방목록](https://user-images.githubusercontent.com/109324637/219396162-68171c62-4f91-45eb-b5ff-5dacf47969b8.PNG)


### 채팅

![채팅](https://user-images.githubusercontent.com/109324637/219238626-39e37856-b134-4280-93c0-9d56f264bccd.PNG)

![친구프로필](https://user-images.githubusercontent.com/109324637/219396320-84da4d4c-8542-4870-b5be-afe46356bbeb.PNG)


### 프로필 화면

![프로필](https://user-images.githubusercontent.com/109324637/219238731-bcf5fa1d-2c7a-400d-811c-0be97b0cbf74.PNG)

## 3. 시스템 아키텍처

![servicearchitecture](https://user-images.githubusercontent.com/109324637/219059964-0ed37545-a7f3-4edc-838d-425cb43b8acb.png)


## 4. 개발 환경

![개발 환경](https://user-images.githubusercontent.com/109324637/219061092-ef630192-eb6b-41e6-8255-e003dfaf3fe5.png)


## 5. 컴포넌트 구성도 및 Figma

![rdb-component](https://user-images.githubusercontent.com/109324637/219228129-9ab69ee2-6207-49f0-9458-cdb7bbf947c8.png)

![PagesFlow](https://user-images.githubusercontent.com/109324637/219229051-bf9900e5-edf6-421b-ae59-b5a53e0cb1a5.png)


## 6. ERD

![mysql erd](https://user-images.githubusercontent.com/109324637/219227717-ea6d8fe4-6d93-46dd-abbb-79106a1ea0c6.png)

## 7. 멤버 및 회고

<table>
  <tr>
    <td align="center"><a href="https://github.com/developerhongjulee"><img src="https://avatars.githubusercontent.com/u/70627908?v=4?s=100" width="100px;" alt=""/><br /><sub><b>이홍주</b></sub></a><br /></td>
    <td align="center"><a href="https://github.com/grolarkim"><img src="https://avatars.githubusercontent.com/u/91328539?v=4?s=100" width="100px;" alt=""/><br /><sub><b>김명준</b></sub></a><br /></td>      
    <td align="center"><a href="https://github.com/Bluuubery"><img src="https://avatars.githubusercontent.com/u/109324637?v=4?s=100" width="100px;" alt=""/><br /><sub><b>유선준</b></sub></a><br /></td>
    <td align="center"><a href="https://github.com/yena0426"><img src="https://avatars.githubusercontent.com/u/17820939?v=4?s=100" width="100px;" alt=""/><br /><sub><b>안예나</b></sub></a><br /></td>      
    <td align="center"><a href="https://github.com/ljaewon97"><img src="https://avatars.githubusercontent.com/u/106070950?v=4?s=100" width="100px;" alt=""/><br /><sub><b>이재원</b></sub></a><br /></td>     
    <td align="center"><a href="https://github.com/Hoony-JSG"><img src="https://avatars.githubusercontent.com/u/107928377?v=4?s=100" width="100px;" alt=""/><br /><sub><b>정훈</b></sub></a><br /></td>   
  </tr>
</table>


✔ 이홍주 
- 팀장
- 일정관리(Jira)
- Backend Entity 및 API 구현
- Backend 에러 및 예외처리 handler 설정 및 적용
- OpenVidu API 설정 및 적용
- Frontend 3대3 미팅방 구현
     
> 회고가 들어갈 자리

✔ 김명준 
- Backend Entity 및 API 구현
- Docker 및 Jenkins를 활용한 CI/CD 구축
- Docker 및 AWS를 활용한 서버 배포
- Nginx를 활용한 프론트 웹서버 구축 및 리버스 프록시 설정 및 적용
- Facemash와 Three.js를 활용한 얼굴 인식 및 블라인드 설정 및 적용
- FaceAPI를 활용한 감정인식 설정 및 적용
- Frontend 1대1 미팅방 구현
     
> 회고가 들어갈 자리

✔ 안예나 
- Figma를 활용한 Prototype 제작
- Frontend 
- Frontend 
- Frontend  
- 프로젝트 기록 정리
- UCC 제작
     
> 회고가 들어갈 자리

✔ 유선준 
- Backend Entity 및 API 구현
- Backend NoSql(MongoDB) 적용
- WebRTC Stomp 서버 구축 및 클라이언트 생성&연결
- Frontend Redux 설정 및 적용
- Frontend JWT 보관 및 발급 구성 및 적용
- Frontend 로그인 및 로그아웃 구성 및 적용 
- 산출물 문서화
- 프로젝트 발표
     
> 회고가 들어갈 자리

✔ 이재원 
- Backend Entity 및 API 구현
- Docker 및 Jenkins를 활용한 CI/CD 구축
- Docker 및 AWS를 활용한 서버 배포
- Backend Redis 적용 
- Backend JWT 발급 및 인증 로직 구성 및 적용
- 소셜 로그인 설정 및 적용
     
> 회고가 들어갈 자리

✔ 정훈 
- Figma를 활용한 Prototype 제작
- Frontend 
- Frontend 
- Frontend  

     
> 회고가 들어갈 자리
