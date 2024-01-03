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

## tap1 연락처
info of tap1

## tap2 갤러리
## 

!https://github.com/ahnhojin1026/madcampweek1/issues/10#issue-2063715547

![tab2_imageimport.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/f6cb388f-3934-47d6-9928-26d2e10eb0fc/01eb1d95-aaa7-465b-b152-e9ecfc32fe67/tab2_imageimport.png)

![tab2_imageinfo.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/f6cb388f-3934-47d6-9928-26d2e10eb0fc/90af4340-cdcc-431f-b1d1-41700e1848f4/tab2_imageinfo.png)

- LazyVerticalGrid를 활용하여 사용자가 저장한 이미지를 제공합니다.
- 새로운 이미지를 추가할때 이미지를 휴대폰 갤러리에서 불러오고, 해당 이미지에 대한 설명을 추가하여 저장할 수 있습니다.
- 이미지는 설명과 함께 큰 화면으로 볼 수 있습니다.
- RoomDatabase를 활용하여 이미지의 URI와 이미지에 대한 설명을 저장합니다<br>

![tab2_roomDB.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/f6cb388f-3934-47d6-9928-26d2e10eb0fc/b8a3c815-0cb4-4902-a359-ed5398e84679/tab2_roomDB.png)
## tap3 메모장
## 

![tab3_screen.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/f6cb388f-3934-47d6-9928-26d2e10eb0fc/034f5df1-833b-41c8-b640-0894045c5996/tab3_screen.png)

![tab3_new_memo_input.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/f6cb388f-3934-47d6-9928-26d2e10eb0fc/36f2e062-daf2-41db-896f-5d2e077cf3a2/tab3_new_memo_input.png)

![tab3_memo_fullscreen.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/f6cb388f-3934-47d6-9928-26d2e10eb0fc/3c763a37-35a5-4532-9b9b-32eb52647184/tab3_memo_fullscreen.png)

- LazyVerticalColumn을 활용해 사용자가 저장하였던 메모들에 접근할 수 있습니다.
- 새로운 메모를 추가하기 위해서는 + 아이콘을 눌러 작성창에 메모를 입력하고 저장이 가능합니다.
- 메모를 터치하면 메모 전체내용을 볼 수 있고 메모를 삭제할 수 있습니다.
- Room database를 활용하여 사용자가 작성한 메모를 저장합니다.(제목, 내용, 작성일자)
 
![tab3_roomDB.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/f6cb388f-3934-47d6-9928-26d2e10eb0fc/d7fa56c3-584c-40cb-ad08-c6cd284afed6/tab3_roomDB.png)
