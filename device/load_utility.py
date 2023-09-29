import numpy as np

def load_radio_map():
    ap_num = 134; col = 16; row = 18; x = 0; y = 0 
    radio_map = [[[0 for _ in range(ap_num)] for _ in range(col)] for _ in range(row)]
    # [18][16][ap 개수]의 radio map 미리 생성
    fp = open('rss_value.txt', 'r')
    
    for line in fp.readlines():
        coord_line = line[0:535].rstrip().split(',')
        coord_line = list(map(int, coord_line))
        # 하나하나의 숫자는 81개의 ap 별 rss 신호를 의미
        # 가장 뒤에 오는 개행, 역슬래쉬를 잘라내어 좌표로 분리
        x, y = line[536:541].rstrip().split(',')

        radio_map[int(x)][int(y)] = coord_line

    fp.close()
    # 위의 for문을 통해 radio_map 완성 및 반환
    return radio_map

def load_ap_list():
    ap_list = {}
    
    fp = open('ap_list.txt', 'r')

    for line in fp.readlines():
        mac, rss = line.rstrip().split(',')
        ap_list[mac] = int(rss)

    fp.close()
    return ap_list

def create_client_info(curr_rss, ap_list, client_rss_store):
    ap_num = 134
    ap_visit = [0 for _ in range(ap_num)] # 방문하지 않은 AP는 0으로 채우기 위함
    splitted_rss = curr_rss.split(',') # 여기서 rss 값과 mac 주소로 분리

    idx = 0 # 받아온 RSS 정보를 훑을 인덱스
    while idx < len(splitted_rss):
        mac_addr = splitted_rss[idx] # MAC 주소
        try:
            ap_index = ap_list[mac_addr] # 몇 번 MAC인지?
            rss = int(splitted_rss[idx+1]) # 해당 MAC의 RSS 값
            client_rss_store[ap_index].push_value(rss)
            # client_rss_store[ap_index] = rss
            ap_visit[ap_index] = 1 # 방문했다.
        except:
            pass

        idx += 2 # 두 칸씩 증가해야 다음 MAC 탐색 가능

    for idx in range(ap_num):
        if ap_visit[idx] == 0: # 방문하지 않은 AP는 0으로 채우기
            client_rss_store[idx].push_value(0)
            # client_rss_store[idx] = 0
            ap_visit[idx] = 1

    for i in client_rss_store:
        print(i.queue)

    return client_rss_store

def calculate_client_position(radio_map, client_rss_store_median):
    ap_num = 134; row = 18; col = 16
    
    min_dist = 9999999999 # 매우 큰 값으로 초기화
    result_x = 0; result_y = 0
    for x in range(row):
        for y in range(col):
            distance = round(np.linalg.norm(np.array(client_rss_store_median) - np.array(radio_map[x][y])))
            # Euclidean distance를 통해 거리를 계산한다.

            if(min_dist > distance): # 최단거리인 좌표를 저장한다.
                min_dist = distance
                result_x = x
                result_y = y
    
    return result_x, result_y