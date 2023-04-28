"""
기본 rss map을 만들기 위한 RSS 측정 코드입니다.
"""
import sys
import os
import time
import shutil

"""
Python은 기본적으로, No indent(들여쓰기가 없는) 줄을 main으로 실행합니다.
__name__은 인터프리터 실행 이전에 만들어져 있는 내장 변수로, 
현재 실행중인 모듈(파일)의 이름을 받아옵니다.
따라서, if __name__=="__main__"의 의미는 import한 모듈이 아닌,
내가 직접 실행한 파일이라는 의미가 됩니다.
"""
if __name__ == "__main__":
    sys.path.append("../")

    if len(sys.argv) != 4:
        assert False, 'Please pass argument : [file name] x y n'
    
    x = sys.argv[1] # x좌표
    y = sys.argv[2] # y좌표
    n = int(sys.argv[3]) # 반복 횟수
    RSS_directory = "RSS-1" # txt 파일을 저장할 directory

    if os.path.isdir(RSS_directory) == False: # directory가 생성이 안되었으면
        os.mkdir(RSS_directory) # 생성

    # 생성될 txt파일의 공통 이름
    # wifi 세기를 측정할 Linux 커맨드
    out_filename = RSS_directory + '/' + 'RSS-1[' + x + ',' + y + ']'
    scan_command = "sudo iwlist wlan0 scan | grep -E 'level|Address' | sed 's/level=//' | awk '{ if ( $1 == \"Cell\" ) { print $5 } if ( $2 == \"Signal\" ) { print $3 } }'"

    # 인자로 받은 반복 횟수만큼 반복 측정한다.
    for i in range(n):
        curr_out_filename = out_filename + '#' + str(i+1) + '.txt'
        curr_scan_command = scan_command + ' > ' + curr_out_filename
        print("Create on file : ", curr_out_filename)
        os.system(curr_scan_command)
        time.sleep(1) # ms 단위가 아닌 sec 단위