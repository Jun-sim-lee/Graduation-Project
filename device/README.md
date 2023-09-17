Device layer의 코드 저장소입니다.
### 개발 환경
```
- Raspberry pi 3B+ (변경)
- Python 3.9.2
```
### 파일 구조
```
RSS-1-realtime
ㄴ 해당 폴더에 3회 측정한 RSS 값이 쌓입니다.

load_map_test.py
ㄴ ```load_radio_map()```
  ㄴ 측정한 radio map을 위치 계산에 사용하기 위해 불러옵니다.
ㄴ ```load_ap_list()```
  ㄴ 측정된 AP 목록을 dictionary 형태로 불러옵니다.
ㄴ ```create_client_info(curr_rss, ap_list, client_rss_store)```
  ㄴ 사용자가 실시간으로 측정한 RSS를 기반으로 (81, 3) 크기의 큐를 선언합니다.
  ㄴ 실시간으로 측정되는 RSS 값을 해당 큐에 쌓아, 정확도를 개선합니다.
ㄴ ```calculate_client_position(radio_map, client_rss_store_median)
  ㄴ 사용자의 큐에 저장된 값들의 중간값을 이용해 실제 위치를 계산합니다.

clientQueue.py
ㄴ ```clientQueue```
  ㄴ 사용자의 RSS 값을 AP 별로 쌓기 위한 개인 큐입니다.
  ㄴ 3의 크기를 고정적으로 가지며, 위치 계산을 위해 중간값을 사용합니다.

file_tokenize.py
ㄴ ```txt_to_msg()```
  ㄴ 실시간으로 측정되는 RSS 값을 서버로 전송할 수 있도록 JSON 형태로 반환합니다.
  ㄴ 해당 함수는 서버에서 위치 계산을 하는 경우 사용되는 함수입니다.
ㄴ ```parse_for_calculate(filename, threshold)```
  ㄴ 실시간으로 측정되는 RSS 값을 계산을 위해 파싱합니다.
  ㄴ MAC,RSS,MAC,RSS,... 의 형태로 파싱하며, 역치 이하의 값은 버립니다.
  ㄴ 해당 함수는 라즈베리파이에서 위치 계산을 하는 경우 사용됩니다.
ㄴ ```coordinate_to_json(x, y)```
  ㄴ 계산 결과를 서버로 전송하기 위해 JSON 형태로 반환합니다.
ㄴ ```add_unique_id(unique_id, msg)```
  ㄴ 고유 rpi 코드를 전송해 해당 유저의 위치임을 알 수 있도록 합니다.

file_tokenize-queuever.py
ㄴ 정확도를 높이기 위해 라즈베리파이에서 큐를 이용해 계산했던 함수가 선언된 파일입니다.
  ㄴ 현재 버전에서는 사용하지 않으며, 버전 관리를 위해 남겨두었습니다.

rss-realtime.py
ㄴ 실시간으로 서버에 RSS 값을 전송하기 위한 코드입니다.

rss-setup.py
ㄴ Radio map 구성 시 RSS 반복 측정을 위한 코드입니다.

hello1~3.txt
ㄴ 서버 전송 테스트를 위한 파일입니다.

rss_value.txt
ㄴ radio map을 구성하기 위한 txt 파일입니다.

rss_value_zero.txt
ㄴ 측정되지 않는 RSS 값을 0으로 설정한 txt 파일입니다.
  ㄴ -70과 0의 정확도 차이를 확인하기 위해 사용됩니다.

wmi_mqtt.py
ㄴ Subscriber 역할을 할 MQTT 코드의 테스트 코드입니다.
```
