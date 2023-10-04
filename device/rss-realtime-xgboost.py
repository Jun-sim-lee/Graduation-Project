import pandas as pd
import load_utility as ut
import file_tokenize as tk
import seaborn as sns
import matplotlib.pyplot as plt
import sys
import requests

sys.path.append("../")
RSS_directory = "RSS-1-realtime"  # txt 파일을 저장할 directory

out_filename = RSS_directory + "/" + "RSS-realtime"
scan_command = "sudo iwlist wlan0 scan | grep -E 'level|Address' | sed 's/level=//' | awk '{ if ( $1 == \"Cell\" ) { print $5 } if ( $2 == \"Signal\" ) { print $3 } }'"
predict_counter = 1

ap_list = ut.load_ap_list()
data_for_request = ""
while predict_counter < 2:
    client_rss_store = [0 for _ in range(134)]

    curr_out_filename = "hello" + str(predict_counter) + ".txt"
    #curr_scan_command = scan_command + " > " + curr_out_filename
    #os.system(curr_scan_command)

    data_for_parse = tk.parse_for_calculate(curr_out_filename)
    splitted_data = data_for_parse.split(",")

    idx = 0
    try:
        while idx < len(splitted_data):
            client_rss_store[ap_list[splitted_data[idx]]] = int(splitted_data[idx + 1])
            idx += 2
    except:
        pass

    for num in client_rss_store:
        data_for_request = data_for_request + str(num) + ','
    
    headers = {'Content-Type': 'application/json; charset=utf-8'}
    data_for_request = '{"client_rss_store": "' + data_for_request[:-1] + '"}'
    response = requests.post("http://3.35.229.16:8000/predict/", headers=headers, data=data_for_request)
    print(response.text)

    predict_counter += 1