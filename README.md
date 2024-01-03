# Hello Android!

## 소개

코딩의 첫걸음은 Hello World! 안드로이드의 첫걸음은 Hello Android!

사용자의 입장에서 꼭 필요한 기능들인 연락처, 갤러리, 메모장 기능을 유용하고 사용하기 편리하게 제작한 앱입니다.
## 구성
1. [tap1 : 연락처(My Contact)☎️](#tap1-연락처)<br>
2. [tap2 : 갤러리(My Image)📷](#tap2-갤러리)<br>
3. [tap3 : 메모장(My Notes)📝](#tap3-메모장)<br>

## 개발자
[정성엽](https://github.com/SungyeopJeong) <br>
[안호진](https://github.com/ahnhojin1026)
## 개발환경
* Android Studio<br>
* kotlin
* jetpack Compose

## tap1 연락처
info of tap1

## tap2 갤러리
## 
![tab2_imagescreen](https://github.com/ahnhojin1026/madcampweek1/assets/43876782/40a96bb6-41e1-4d89-8d8d-dadc5ec1f60e) |![tab2_imageimport](https://github.com/ahnhojin1026/madcampweek1/assets/43876782/29798ec5-57be-4c1a-bfde-57f36482fd5a) |![tab2_imageinfo](https://github.com/ahnhojin1026/madcampweek1/assets/43876782/48b07693-19f1-41e5-b4c6-b0cfa60f0880)
---|---|---

- LazyVerticalGrid를 활용하여 사용자가 저장한 이미지를 제공합니다.
- 새로운 이미지를 추가할때 이미지를 휴대폰 갤러리에서 불러오고, 해당 이미지에 대한 설명을 추가하여 저장할 수 있습니다.
- 이미지는 설명과 함께 큰 화면으로 볼 수 있습니다.
- RoomDatabase를 활용하여 이미지의 URI와 이미지에 대한 설명을 저장하고 Repository와 ViewModel을 구축하여서 데이터를 사용자에게 제공합니다<br>

![tab2_roomDB](https://github.com/ahnhojin1026/madcampweek1/assets/43876782/5df869f8-c7d7-4d26-b3ac-8b0d09abf4df)
## tap3 메모장
## 

![tab3_memoscreen](https://github.com/ahnhojin1026/madcampweek1/assets/43876782/97544e83-f46b-47a1-b993-16bb725a31bc)|![tab3_new_memo_input](https://github.com/ahnhojin1026/madcampweek1/assets/43876782/60121d05-d6ac-46c7-ace6-855a44e91f16)|![tab3_memo_fullscreen](https://github.com/ahnhojin1026/madcampweek1/assets/43876782/886f3971-cdaa-4495-a413-4e461722a630)
---|---|---
- LazyVerticalColumn을 활용해 사용자가 저장하였던 메모들에 접근할 수 있습니다.
- 새로운 메모를 추가하기 위해서는 + 아이콘을 눌러 작성창에 메모를 입력하고 저장이 가능합니다.
- 메모를 터치하면 메모 전체내용을 볼 수 있고 메모를 삭제할 수 있습니다.
- Room database를 활용하여 사용자가 작성한 메모를 저장하고 Repository와 ViewModel을 구축하여서 데이터를 사용자에게 제공합니다.(제목, 내용, 작성일자)
  
 ![tab3_roomDB](https://github.com/ahnhojin1026/madcampweek1/assets/43876782/3df7441c-83dc-4dc8-8ca0-7426162f8fe4)
