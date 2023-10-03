import pandas as pd
import load_utility as ut
import file_tokenize as tk
import seaborn as sns
import matplotlib.pyplot as plt
import sys
import os

from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score, confusion_matrix, classification_report, multilabel_confusion_matrix
from sklearn.preprocessing import LabelEncoder
from xgboost import XGBClassifier

def print_result(result):
    result = str(result[0])

    if(len(result) == 1):
        print('(0, ' + result[0] + ')')
    if(len(result) == 2):
        print('(' + result[0] + ',' + result[1] + ')')
    if(len(result) == 3):
        if(result[0] == 1):
            print('(' + result[0] + result[1] + ',' + result[2] + ')')
        else:
            print('(' + result[0] + ',' + result[1] + result[2] + ')')
    if(len(result) == 4):
        print('(' + result[0] + result[1] + ',' + result[2] + result[3] + ')')

def create_sample(curr_rss):
    sample = [0 for _ in range (134)]
    idx = 0
    
    while idx < len(curr_rss):
        try:
            sample[ap_list[curr_rss[idx]]] = int(curr_rss[idx + 1])
        except:
            pass

        idx += 2
    return sample

def predict_sample(sample):
    sample = pd.DataFrame(sample).T
    test_predict = model.predict(sample)

    print(encoder.classes_[test_predict])

df = pd.read_csv('rss_csv.csv')
X = df.iloc[:, :134] # RSS 값
Y = df['result'] # 레이블 값
ap_list = ut.load_ap_list()

x_train, x_test, y_train, y_test = train_test_split(X, Y, test_size=0.2, random_state=39)
encoder = LabelEncoder()
y_train = encoder.fit_transform(y_train) # 그대로 레이블 값을 사용할 수 없다. 정수 값으로 Encoding 필요
y_test = encoder.fit_transform(y_test)

"""
XGBoost 모델 선언
1. n_estimators : 학습 모델의 수 높을 수록 성능 향상의 가능성 높아지나, 속도 저하 위험
2. learning_rate : alpha 계수, 너무 크면 gradient 발산 가능성, 너무 작으면 학습 속도 느림
3. max_depth : 최대 탐색 깊이, 너무 깊으면 과적합 가능성, 너무 얕으면 일반화 불가
4. min_samples_split : 분할 종료 최소 샘플 수(Early stopping), 크면 과적합 방지, 학습 저하 가능성
5. min_samples_leaf : 단말의 최소 샘플 수, 위와 유사
6. random_state : 시드 값
"""

model = XGBClassifier(n_estimators = 400, learning_rate = 0.1, max_depth = 3, objective='multi:softmax')
model.fit(x_train, y_train)
y_pred = model.predict(x_test)
print("Learning finished!!   Accuracy : ", accuracy_score(y_pred, y_test))
#conmat = confusion_matrix(y_pred, y_test)
#plt.figure(figsize=(10, 8))
#sns.heatmap(conmat, annot=True, fmt='d', cmap='Reds')
#plt.show()
#sample = create_sample(tk.parse_for_calculate('hello1.txt').split(','))
#predict_sample(sample)

sys.path.append("../")
RSS_directory = "RSS-1-realtime" # txt 파일을 저장할 directory

out_filename = RSS_directory + '/' + 'RSS-realtime'
scan_command = "sudo iwlist wlan0 scan | grep -E 'level|Address' | sed 's/level=//' | awk '{ if ( $1 == \"Cell\" ) { print $5 } if ( $2 == \"Signal\" ) { print $3 } }'"
predict_counter = 1

ap_list = ut.load_ap_list()
while predict_counter < 2:
    client_rss_store = [0 for _ in range(134)]

    curr_out_filename = 'hello' + str(predict_counter) + '.txt'
    curr_scan_command = scan_command + ' > ' + curr_out_filename
    os.system(curr_scan_command)

    data_for_parse = tk.parse_for_calculate(curr_out_filename)
    splitted_data = data_for_parse.split(',')

    idx = 0
    try:
        while idx < len(splitted_data):
            client_rss_store[ap_list[splitted_data[idx]]] = int(splitted_data[idx+1])
            idx += 2
    except:
        pass

    data_for_predict = pd.DataFrame(client_rss_store).T
    predicted_coord = model.predict(data_for_predict)

    print_result(encoder.classes_[predicted_coord])
    predict_counter += 1