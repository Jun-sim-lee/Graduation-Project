"""
실제로 실시간 RSS를 측정하고, 서버로 보내기 위한 코드입니다.
"""
import sys
import os
import time
import requests # REST API 호출용
import file_tokenize # txt 파일 메시지화

if __name__ == "__main__":
    sys.path.append("../")
    RSS_directory = "RSS-1-realtime" # txt 파일을 저장할 directory
    
    if os.path.isdir(RSS_directory) == False: # directory가 생성이 안되었으면
        os.mkdir(RSS_directory) # 생성

    # 생성될 txt파일의 공통 이름
    # wifi 세기를 측정할 Linux 커맨드
    out_filename = RSS_directory + '/' + 'RSS-realtime'
    scan_command = "sudo iwlist wlan0 scan | grep -E 'level|Address' | sed 's/level=//' | awk '{ if ( $1 == \"Cell\" ) { print $5 } if ( $2 == \"Signal\" ) { print $3 } }'"

    # 서버에 연결 시도
    url = "http://127.0.0.1:8080/test"
    headers = {'Content-Type': 'application/json; charset=utf-8'}
    transmit_counter = 0
    try:
        while True:
            curr_out_filename = out_filename + '#' + str(transmit_counter) + '.txt'
            curr_scan_command = scan_command + ' > ' + curr_out_filename
            os.system(curr_scan_command)
            time.sleep(1)
            print(transmit_counter)

            # 생성된 txt 파일을 token화 해서 REST api 호출을 통해 서버로 전송
            # 서버로 전송된 String은 서버의 Controller에서 역직렬화되어 Map으로 받아진다.
            requests.post(url=url, data=file_tokenize.txt_to_msg(curr_out_filename), headers=headers)
            time.sleep(2) # 서버에 보내는 간격이 필요하다.
            transmit_counter += 1
    except:
        print("Cannot connect to server!!")
