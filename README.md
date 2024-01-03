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
## 

[갤럭시 기본 연락처 앱을 참고하여 디자인 시스템을 간략하게 정립하였습니다(피그마 링크)](https://www.figma.com/embed?embed_host=notion&url=https%3A%2F%2Fwww.figma.com%2Ffile%2F6bwhNUFprLSkoIXLa0zx1A%2FMadCamp%3Ftype%3Ddesign%26node-id%3D0%3A1%26mode%3Ddesign%26t%3DaznvtmSQG61tsxHm-1)

![Screenshot_20240103-202927_MadcampCom1](https://github.com/ahnhojin1026/madcampweek1/assets/71690205/b40d38a1-f6e5-4d2e-8c11-ec92ca2f7d01) 연락처 목록을 보여주는 화면 | ![Screenshot_20240103-202951_MadcampCom1](https://github.com/ahnhojin1026/madcampweek1/assets/71690205/3af80ee2-4c36-48ac-8c20-2ede19065778) 연락처를 클릭하면 전화번호 정보와 info 버튼과 삭제 버튼이 뜸 | ![Screenshot_20240103-203018_MadcampCom1](https://github.com/ahnhojin1026/madcampweek1/assets/71690205/4d3f0838-87e7-4d3a-895f-00d7c651dd82) 상단 앱 바의 더보기에서 휴대폰의 연락처로부터 연락처를 불러오거나, 연락처 목록을 전체 삭제할 수 있음
---|---|---
![Screenshot_20240103-202958_MadcampCom1](https://github.com/ahnhojin1026/madcampweek1/assets/71690205/e47bbaf2-c966-486a-ac7f-87b875411bd8) 연락처 상세 화면, 이름과 번호 목록, 기본 번호가 나타나 있음 | ![Screenshot_20240103-203011_MadcampCom1](https://github.com/ahnhojin1026/madcampweek1/assets/71690205/dd2a4847-667f-4bfc-8806-d85baeb76dd1) 상단 앱 바의 삭제 버튼을 누르면 팝업이 뜨고, 연락처를 삭제할 수 있음 | ![Screenshot_20240103-203006_MadcampCom1](https://github.com/ahnhojin1026/madcampweek1/assets/71690205/796c6d61-e0c4-474e-9d60-b092e602139c) 번호를 길게 클릭하면 팝업이 뜨고, 기본 번호로 설정할 수 있음
||
![Screenshot_20240103-203700_MadcampCom1](https://github.com/ahnhojin1026/madcampweek1/assets/71690205/66e958db-00ee-4964-9c82-46673665415e) 연락처 추가 / 수정 화면, 연락처 목록 화면 상단 앱 바의 추가 버튼을 통해 접근 가능||

- Room을 사용하여 로컬 데이터 베이스에 연락처 데이터를 추가, 수정, 삭제할 수 있습니다
- contentResolver를 이용해 휴대폰 연락처 정보를 가져올 수 있습니다
- (Hilt)ViewModel을 이용해 각 화면에서 이용되는 데이터와 메소드에 접근합니다
- Navigation을 이용해 연락처 화면에서 연락처 상세 화면으로 이동할 수 있습니다
- 폴더 구조를 component, data, di, screen, theme, viewModel로 분류하였고 component를 재사용 가능하며, Preview 할 수 있도록 개발하였습니다

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
