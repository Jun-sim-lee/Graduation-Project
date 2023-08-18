Device layer의 코드 저장소입니다.
### 개발 환경
```
- Raspberry pi 4 - 4GB model B
- Python 3.9.2
```
### 파일 구조
```
RSS-1-realtime
ㄴ 해당 폴더에 3회 측정한 RSS 값이 쌓입니다.
file_tokenize.py
ㄴ file_to_dictionary 함수
  ㄴ 실시간으로 측정되는 RSS 값을 {MAC 주소 : RSS} 형태로 dictionary에 누적시킵니다.
  ㄴ 해당 값을 이후에 평균내어 서버로 전송합니다.
ㄴ dict_to_json 함수
  ㄴ 평균 낸 dictionary 구조를 json 형태로 파싱해 서버로 전송합니다.
rss-realtime.py
ㄴ 실시간으로 서버에 RSS 값을 전송하기 위한 코드입니다.
  ㄴ RSS-1-realtime 폴더에 모인 3회 치 RSS 값을 평균내어 전송합니다.
rss-setup.py
ㄴ Radio map 구성 시 RSS 반복 측정을 위한 코드입니다.
wmi_mqtt.py
ㄴ Subscriber 역할을 할 mqtt 코드입니다.
hello1~3.txt
ㄴ 서버 전송 테스트를 위한 파일입니다.
```
