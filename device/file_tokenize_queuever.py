import sys
"""
REST Api 요청을 위해서는, 역질렬화가 필요하다.
따라서 String을 객체로 변환하는 작업을 해 주어야 한다.
"""
def file_to_dictionary(filename, mac_dict):
    # 파일을 넘겨받아 dictionary로 옮기는 함수
    msg_cnt = 0
    msg = []

    fp = open(filename, 'r')
    lines = fp.readlines() # 파일 안의 전체 내용 읽기
    fp.close()

    for line in lines: # 읽은 내용 한 줄씩 뗌
        l = line.rstrip() # 문장 끝 공백(개행) 제거
        msg.append(l)
        msg_cnt += 1
        
        if(msg_cnt > 1):
            msg_cnt = 0
            if(msg[0] not in mac_dict):
                mac_dict[msg[0]] = [int(msg[1]), 1]
            else:
                mac_dict[msg[0]][0] += int(msg[1])
                mac_dict[msg[0]][1] += 1

            msg.clear()

def dict_to_json(mac_dict):
    # 만들어진 dict를 JSON으로 변환하는 함수
    result_json = "{"

    for key, val in mac_dict.items():
        result_json += ('"' + key + '"' + ':' + str(val) + ', ')

    result_json = result_json[:-2]
    result_json += "}"

    print(result_json)
    return result_json