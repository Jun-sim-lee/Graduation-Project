"""
실제로 실시간 RSS를 측정하고, 서버로 보내기 위한 코드입니다.
"""
import sys
import os
import time
import requests # REST API 호출용
import file_tokenize # txt 파일 메시지화

if __name__ == "__main__":
    fp = open("uniqueCode.txt", 'r')
    unique_id = fp.readline()
    # authenticate_rpi(unique_id) # 여기서 기기인증 시도

    sys.path.append("../")
    RSS_directory = "RSS-1-realtime" # txt 파일을 저장할 directory
    
    if os.path.isdir(RSS_directory) == False: # directory가 생성이 안되었으면
        os.mkdir(RSS_directory) # 생성

    # 생성될 txt파일의 공통 이름
    # wifi 세기를 측정할 Linux 커맨드
    out_filename = RSS_directory + '/' + 'RSS-realtime'
    scan_command = "sudo iwlist wlan0 scan | grep -E 'level|Address' | sed 's/level=//' | awk '{ if ( $1 == \"Cell\" ) { print $5 } if ( $2 == \"Signal\" ) { print $3 } }'"

    # 서버에 연결 시도
    url = "http://43.202.141.142:8080/sendTest"
    headers = {'Content-Type': 'application/json; charset=utf-8'}
    transmit_counter = 0
    try:
        while transmit_counter <= 5:
            curr_out_filename = out_filename + '#' + str(transmit_counter) + '.txt'
            curr_scan_command = scan_command + ' > ' + curr_out_filename
            os.system(curr_scan_command)

            # 생성된 txt 파일을 token화 해서 REST api 호출을 통해 서버로 전송
            # 서버로 전송된 String은 서버의 Controller에서 역직렬화되어 Map으로 받아진다.
            request_data = file_tokenize.txt_to_msg(curr_out_filename, -70)
            # 이 부분에서 전송이 아닌 계산이 들어가야 한다.
            request_data = file_tokenize.add_unique_id(unique_id, request_data)
            # 또한, add_unique_id 함수를 통해서 JSON 형태로 아이디를 붙여 전송한다.
            
            requests.post(url=url, data=request_data, headers=headers)
            print(transmit_counter)
            time.sleep(0.1) # 서버에 보내는 간격이 필요하다.
            transmit_counter += 1
    except:
        print("Cannot connect to server!!")