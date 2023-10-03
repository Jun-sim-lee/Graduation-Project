"""
실제로 실시간 RSS를 측정하고, 서버로 보내기 위한 코드입니다.
"""
import sys
import os
import time
import requests # REST API 호출용
import file_tokenize as tokenize # txt 파일 메시지화
import load_utility as ut
from clientQueue import clientQueue
def authenticate_rpi(unique_id):
    url = "http://43.202.141.142:8080/checkRpi"
    headers = {'Content-Type': 'application/json; charset=utf-8'}
    data = '{"code":' + '"' + unique_id + '"}'
    # 이런식으로 아이디와 고유 코드를 전송해서 서버에서 유효성을 검증한다.

    try:
        requests.post(url=url, data=data, headers=headers)
        if(requests.Response == "OK"):
            print("성공적으로 인증 되었습니다.")
            time.sleep(10)
        else:
            assert "유효한 고유 코드가 아닙니다."
    except:
        print("Cannot connect to server!!")

if __name__ == "__main__":
    fp = open("uniqueCode.txt", 'r')
    unique_id = fp.readline()
    # authenticate_rpi(unique_id) # 여기서 기기인증 시도

    sys.path.append("../")
    RSS_directory = "RSS-1-realtime" # txt 파일을 저장할 directory
    
    if os.path.isdir(RSS_directory) == False: # directory가 생성이 안되었으면
        os.mkdir(RSS_directory) # 생성

    # 1. 생성될 txt파일의 공통 이름
    # 2. wifi 세기를 측정할 Linux 커맨드
    # 3. 초반에 생성해 놓을 라디오 맵 
    # 4. 초반에 생성해 놓을 AP(공유기) 리스트
    # 5. 사용자의 위치를 계산하기 위한 사용자용 radio_map
    out_filename = RSS_directory + '/' + 'RSS-realtime'
    scan_command = "sudo iwlist wlan0 scan | grep -E 'level|Address' | sed 's/level=//' | awk '{ if ( $1 == \"Cell\" ) { print $5 } if ( $2 == \"Signal\" ) { print $3 } }'"
    radio_map = ut.load_radio_map()
    ap_list = ut.load_ap_list(); ap_num = 134
    
    client_rss_store = []
    for _ in range(ap_num): # 사용자의 큐를 만들어 유지한다.
        client_rss_store.append(clientQueue(3))

    # 연결할 서버 정보
    url = "http://43.202.141.142:8080/ap"
    headers = {'Content-Type': 'application/json; charset=utf-8'}
    transmit_counter = 1
    while transmit_counter <= 3:
            curr_out_filename = out_filename + '#' + str(transmit_counter) + '.txt'
            #curr_out_filename = 'hello' + str(transmit_counter) + '.txt'
            #curr_scan_command = scan_command + ' > ' + curr_out_filename
            #os.system(curr_scan_command)

            # 생성된 txt 파일을 token화 해서 계산을 진행해야 한다!
            data_for_calculate = tokenize.parse_for_calculate(curr_out_filename)
            client_rss_store = ut.create_client_info(data_for_calculate, ap_list, client_rss_store)
            
            client_rss_store_median = []
            for idx in range(ap_num):
                client_rss_store_median.append(client_rss_store[idx].get_median())
            # 사용자의 개인 큐에서 중간 값만 뽑아내어 계산에 사용하도록 한다.
            
            # 아래의 함수로 계산 로직 실행
            x, y = ut.calculate_client_position(radio_map, client_rss_store_median)
            print("(" + x + ", " + y + ")")
            # 먼저 계산되어 나온 좌표를 POST 방식으로 전송하기 위해 JSON 형식으로 만들자!
            data_for_request = tokenize.coordinate_to_json(x, y)
            # 계산되어 나온 좌표에 unique_id를 덧붙여서 서버에 전송하도록 한다.
            data_for_request = tokenize.add_unique_id(unique_id, data_for_request)
            
            #requests.post(url=url, data=data_for_request, headers=headers)
            print('Measure count : ' + transmit_counter)
            #time.sleep(1) # 서버에 보내는 간격이 필요하다.
            transmit_counter += 1