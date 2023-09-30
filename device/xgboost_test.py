import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score, roc_auc_score
from sklearn.preprocessing import LabelEncoder
from xgboost import XGBClassifier

df = pd.read_csv('rss_csv.csv')
X = df.iloc[:, :134] # RSS 값
Y = df['result'] # 레이블 값

x_train, x_test, y_train, y_test = train_test_split(X, Y, test_size=0.2, random_state=2023)
encoder = LabelEncoder()
y_train = encoder.fit_transform(y_train) # 그대로 레이블 값을 사용할 수 없다. 정수 값으로 Encoding 필요!!

model = XGBClassifier(n_estimators = 400, learning_rate = 0.1, max_depth = 3, objective='multi:softmax')
model.fit(x_train, y_train)
w_pred = model.predict(x_test)
w_pred_prob = model.predict_proba(x_test)[:, 1]
"""
XGBoost 모델 선언
1. n_estimators : 학습 모델의 수 높을 수록 성능 향상의 가능성 높아지나, 속도 저하 위험
2. learning_rate : alpha 계수, 너무 크면 gradient 발산 가능성, 너무 작으면 학습 속도 느림
3. max_depth : 최대 탐색 깊이, 너무 깊으면 과적합 가능성, 너무 얕으면 일반화 불가
4. min_samples_split : 분할 종료 최소 샘플 수(Early stopping), 크면 과적합 방지, 학습 저하 가능성
5. min_samples_leaf : 단말의 최소 샘플 수, 위와 유사
6. random_state : 시드 값
"""
print(accuracy_score(w_pred, y_test))