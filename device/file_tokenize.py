import sys
import requests

"""
REST Api 요청을 위해서는, 역질렬화가 필요하다.
따라서 String을 객체로 변환하는 작업을 해 주어야 한다.
"""
def concat_quotes(str):
    return "\"" + str + "\""

def txt_to_msg(filename, threshold):
    sys.path.append('../')

    msg = "{" # JSON으로 역직렬화를 위해 중괄호로 시작
    fp = open(filename, 'r')
    lines = fp.readlines() # 파일 안의 전체 내용 읽기
    
    for line in lines: # 읽은 내용 한 줄씩 뗌
        l = line.rstrip() # 문장 끝 공백(개행) 제거
        if(len(msg) > 0): # msg에 이미 내용이 있으면 뒤에 concat
            if l.startswith("-"): # token이 -로 시작한다면? RSS라는 뜻!
                if int(l) <= threshold:
                    msg = msg[:-22] # 역치 값 이하인 경우, 해당 AP는 Token화 하지 않음
                else:
                    msg += (l + ", ") # "-37", 과 같은 형태로 붙여나감
            else: # 아니면, MAC 주소라는 의미!
                msg += (concat_quotes(l) + " : ") # "Address" : 와 같은 형태로 붙임
        else:
            msg = l

    msg = msg[:-2] # 마지막에 하나 더 붙는 , 제거를 위한 slicing
    fp.close()
    
    if msg == "":
        msg = "{}" # 전송할 AP가 없는 경우, }만 남는 문제가 생김
                   # 우선 if문으로 해결
    else:
        msg += "}" # 마지막에 중괄호를 반드시 닫아줌

    return msg
