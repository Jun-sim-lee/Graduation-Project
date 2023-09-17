"""
실제로 실시간 RSS를 측정하고, 서버로 보내기 위한 코드입니다.
"""
import sys
import os
import time
import requests # REST API 호출용
import file_tokenize as tk # txt 파일 메시지화

def authenticate_rpi():
    url = "http://13.211.135.69:8080/rpiAuth"
    headers = {'Content-Type': 'application/json; charset=utf-8'}
    data = '{"uniqueRpiCode": "uniquerpirpi1"}' 
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

def measure_rss_three_times(mac_dict):
    measure_count = 1

    # 생성될 txt파일의 공통 이름
    out_filename = RSS_directory + '/' + 'RSS-realtime'
    # wifi 세기를 측정할 Linux 커맨드
    scan_command = "sudo iwlist wlan0 scan | grep -E 'level|Address' | sed 's/level=//' | awk '{ if ( $1 == \"Cell\" ) { print $5 } if ( $2 == \"Signal\" ) { print $3 } }'"

    while measure_count <= 3:
        curr_out_filename = out_filename + '#' + str(measure_count) + '.txt' # 측정 시 출력할 파일 이름
        curr_scan_command = scan_command + ' > ' + curr_out_filename # 스캔 명령어
        os.system(curr_scan_command)
        print("Measure ", measure_count, "th RSS")

        tk.file_to_dictionary(curr_out_filename, -70, mac_dict)
        # 해당 파일을 3회 dictionary에 집어넣는다.
        measure_count += 1
        time.sleep(0.5)

    for key, val in mac_dict.items(): # dictionary에서 연산 진행
        mac_dict[key] = (val // 3) # 우선 평균으로 계산해보자

if __name__ == "__main__":
    #authenticate_rpi() # 기기 인증 함수

    sys.path.append("../")
    RSS_directory = "RSS-1-realtime" # txt 파일을 저장할 directory
    if os.path.isdir(RSS_directory) == False: # directory가 생성이 안되었으면
        os.mkdir(RSS_directory) # 생성

    # 서버에 연결 시도
    url = "http://3.35.226.19:8080/sendTest"
    headers = {'Content-Type': 'application/json; charset=utf-8'}
    transmit_counter = 0

    try: # Message로 변환해서 서버로 보내는 과정
        while transmit_counter < 5:
            mac_dict = {}
            measure_rss_three_times(mac_dict) # 3회 측정
            data = tk.dict_to_json(mac_dict) # 서버로 보낼 json 데이터

            requests.post(url=url, data=data, headers=headers)
            time.sleep(1) # 서버에 보내는 간격이 필요하다.
            transmit_counter += 1
    except:
        print("Cannot connect to server!!")