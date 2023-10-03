"""
실제로 실시간 RSS를 측정하고, 서버로 보내기 위한 코드입니다.
"""
import sys
import os
import time
import file_tokenize as tk # txt 파일 메시지화
import load_utility as ut

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
    url = "http://localhost:8080/sendTest"
    headers = {'Content-Type': 'application/json; charset=utf-8'}
    transmit_counter = 1

    radio_map = ut.load_radio_map()
    ap_list = ut.load_ap_list()
    while transmit_counter <= 3:
            client_rss_store = [0 for _ in range(134)]
            client_rss_store_with_threshold = [0 for _ in range(134)]

            curr_out_filename = 'hello' + str(transmit_counter) + '.txt'
            #curr_out_filename = out_filename + '#' + str(transmit_counter) + '.txt'
            #curr_scan_command = scan_command + ' > ' + curr_out_filename
            #os.system(curr_scan_command)

            # 생성된 txt 파일을 token화 해서 REST api 호출을 통해 서버로 전송
            # 서버로 전송된 String은 서버의 Controller에서 역직렬화되어 Map으로 받아진다.
            data_for_calculate = tk.parse_for_calculate(curr_out_filename)
            data_for_calculate_with_threshold = tk.parse_for_calculate_with_threshold(curr_out_filename, -70)

            splitted_data = data_for_calculate.split(',')
            splitted_data_with_threshold = data_for_calculate_with_threshold.split(',')

            idx = 0
            while idx < len(splitted_data):
                try:
                    client_rss_store[ap_list[splitted_data[idx]]] = int(splitted_data[idx+1])
                    idx += 2
                except:
                    pass
        
            print(ut.calculate_client_position(radio_map, client_rss_store))

            print('Measure count : ' + str(transmit_counter))
            time.sleep(0.1) # 서버에 보내는 간격이 필요하다.
            transmit_counter += 1