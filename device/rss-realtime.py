"""
실제로 실시간 RSS를 측정하고, 서버로 보내기 위한 코드입니다.
"""
import sys
import os
import time
import socket

def file_to_msg():
    msg = ""
    return msg.rstrip()

if __name__ == "__main__":
    sys.path.append("../")

    RSS_directory = "RSS-1-realtime" # txt 파일을 저장할 directory
    if os.path.isdir(RSS_directory) == False: # directory가 생성이 안되었으면
        os.mkdir(RSS_directory) # 생성

    # 생성될 txt파일의 공통 이름
    # wifi 세기를 측정할 Linux 커맨드
    out_filename = RSS_directory + '/' + 'RSS-realtime'
    scan_command = "sudo iwlist wlan0 scan | grep -E 'level|Address' | sed 's/level=//' | awk '{ if ( $1 == \"Cell\" ) { print $5 } if ( $2 == \"Signal\" ) { print $3 } }'"

    # 여기부터는 Socket을 이용한 서버통신 코드입니다.
    client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    print("Connect to : ") # Server Ip와 Port(8080)가 필요하다.

    # 서버에 연결 시도
    try:
        #client_socket.connect((ip, port))
        print("Connect to server..")
    except:
        assert False, "Fail to open socket connection!"

    transmit_counter = 0
    try:
        while True:
            curr_out_filename = out_filename + ' #' + str(transmit_counter) + '.txt'
            curr_scan_command = scan_command + ' > ' + curr_out_filename
            os.system(curr_scan_command)
            time.sleep(1)
            print(transmit_counter)

            # Message로 변환해서 서버로 보내는 과정 필요
            """
                message 코드 자리
                File 내용을 String으로 바꾸는 코드 필요.
                해당 String을 return 받아
                client_socket.sendall(bytes(msg, "utf-8)) 코드 적용.
            """

            time.sleep(2) # 서버에 보내는 간격이 필요하다.
            transmit_counter += 1
    except:
        client_socket.close()
        print("Disconnect to server...")