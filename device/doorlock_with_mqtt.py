import RPi.GPIO as GPIO #RPi.GPIO 라이브러리를 GPIO로 사용
from time import sleep  #time 라이브러리의 sleep함수 사용
import paho.mqtt.client as mqtt

servoPin          = 12   # 서보 핀
SERVO_MAX_DUTY    = 12   # 서보의 최대(180도) 위치의 주기
SERVO_MIN_DUTY    = 3    # 서보의 최소(0도) 위치의 주기

GPIO.setmode(GPIO.BCM)        # GPIO 설정
GPIO.setup(servoPin, GPIO.OUT)  # 서보핀 출력으로 설정

servo = GPIO.PWM(servoPin, 50)  # 서보핀을 PWM 모드 50Hz로 사용하기 (50Hz > 20ms)
servo.start(0)  # 서보 PWM 시작 duty = 0, duty가 0이면 서보는 동작하지 않는다.

def setServoPos(degree):
  # 각도는 180도를 넘을 수 없다.
  if degree > 180:
    degree = 180

  # 각도(degree)를 duty로 변경한다.
  duty = SERVO_MIN_DUTY+(degree*(SERVO_MAX_DUTY-SERVO_MIN_DUTY)/180.0)
  # 변경된 duty값을 서보 pwm에 적용
  servo.ChangeDutyCycle(duty)

def receive_message(client, data, message):
    rx_message = str(message.payload.decode("utf-8"))
    print("Message received : ", rx_message)
    if rx_message == "work!":
        setServoPos(90) # 서보모터 90도로 위치 (열림)
    elif rx_message == "stop!":
        setServoPos(0) # 다시 제자리로 (잠김)

BROKER_ADDRESS = "3.35.229.16"
wmi_client = mqtt.Client("wmi_client")
wmi_client.connect(BROKER_ADDRESS, 1883)
wmi_client.subscribe("rpi/4") # 도어락이 4번 리소스
wmi_client.on_message = receive_message
wmi_client.loop_forever() # MQTT Message를 계속 듣고 있음