# ALMO(ALarm meMO)



## 소개
> 잠자는 당신의 기억, 저희가 깨워드립니다!

쉽게 잊을 수 있는 일들을 등록해놓으면 랜덤한 시각에 알려드립니다.


## 기능


### 작성 / 삭제
<img src="https://github.com/bsshmk/MemoAlarmApp/blob/master/GIF/inputAndRemove.gif" alt="alt text" width="250px" height="500px">



> 마감일과 알림 빈도 설정 / 우측으로 스와이프하여 삭제



메모 데이터 작성과 삭제 기능을 Room Database를 적용해 관리하였다.


### 정렬
<img src="https://github.com/bsshmk/MemoAlarmApp/blob/master/GIF/sort.gif" alt="alt text" width="250px" height="500px">



> 마감일과 등록일 기준으로 정렬

Observe가 Database의 변경을 인지할 때마다 반환받은 List를 Sort하여 Adapter에게 넘겨주었다.
정렬 기능은 Sort의 멤버함수인 Compare 함수를 Override하여 구현하였다.



### 푸시 알림
<img src="https://github.com/bsshmk/MemoAlarmApp/blob/master/GIF/notification.gif" alt="alt text" width="250px" height="500px">



> 프로세스가 종료되어도 서비스가 유지됨 (기기 재시작 시에도 서비스 자동 실행)

> GroupSummary 통한 노티피케이션 정리

안드로이드 8.0 이상부터는 백그라운드를 제한하기 때문에 강제종료시 서비스 기능이 종료된다.
이를 막기위해 라이프사이클상 ondestroy 실행시 다시 실행해주는 것으로 서비스를 유지한다.



### 자동 삭제 
<img src="https://github.com/bsshmk/MemoAlarmApp/blob/master/GIF/autoRemove.gif" alt="alt text" width="250px" height="500px">



> 마감일 도달시 자동 삭제

MVVM 패턴을 채택하였다.
View Modeld, Livedata과 Observe를 활용해 메모의 마감일에 도달하였을 경우 자동으로 삭제하게끔 구현하였다.



### 개발환경
> Android Studio 3.3.2

### 지원 플랫폼
> Android JellyBean 이상의 모든 버전

### 참여자
> 김보성 bsgreentea / 조명기 ChoMK / 조성훈 JoChoSunghoon
