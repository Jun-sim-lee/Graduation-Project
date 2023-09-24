import RPi.GPIO as GPIO #RPi.GPIO 라이브러리를 GPIO로 사용
from time import sleep  #time 라이브러리의 sleep함수 사용
import paho.mqtt.client as mqtt

servoPin_12 = 12 # GPIO 18 (PWM 0)
servoPin_32 = 32 # GPIO 12 (PWM 1)
servoPin_33 = 33 # GPIO 13 (PWM 0)
button_pin1 = 11
button_pin2 = 13
button_pin3 = 15
SERVO_MAX_DUTY    = 12   # 서보의 최대(180도) 위치의 주기
SERVO_MIN_DUTY    = 3    # 서보의 최소(0도) 위치의 주기

servo1 = 0 # 전역 변수로서의 사용을 위한 초기화
servo2 = 0
servo3 = 0

def setup_GPIO():
    GPIO.setmode(GPIO.BCM)        # GPIO 설정
    GPIO.setup(servoPin_12, GPIO.OUT)  # 서보핀 출력으로 설정
    GPIO.setup(servoPin_32, GPIO.OUT)  # 서보핀 출력으로 설정
    GPIO.setup(servoPin_33, GPIO.OUT)  # 서보핀 출력으로 설정
    GPIO.setup(button_pin1, GPIO.IN, pull_up_down=GPIO.PUD_DOWN) # 버튼 핀 11 입력으로, 풀다운 모드
    GPIO.setup(button_pin2, GPIO.IN, pull_up_down=GPIO.PUD_DOWN) # 버튼 핀 13 입력으로, 풀다운 모드
    GPIO.setup(button_pin3, GPIO.IN, pull_up_down=GPIO.PUD_DOWN) # 버튼 핀 15 입력으로, 풀다운 모드

    servo1 = GPIO.PWM(servoPin_12, 50)  # 서보핀을 PWM 모드 50Hz로 사용하기 (50Hz > 20ms)
    servo2 = GPIO.PWM(servoPin_32, 50)  # 서보핀을 PWM 모드 50Hz로 사용하기 (50Hz > 20ms)
    servo3 = GPIO.PWM(servoPin_33, 50)  # 서보핀을 PWM 모드 50Hz로 사용하기 (50Hz > 20ms)
    servo1.start(0)  # 서보 PWM 시작 duty = 0, duty가 0이면 서보는 동작하지 않는다.
    servo2.start(0)  # 서보 PWM 시작 duty = 0, duty가 0이면 서보는 동작하지 않는다.
    servo3.start(0)  # 서보 PWM 시작 duty = 0, duty가 0이면 서보는 동작하지 않는다.
   
def setServoPos(degree, targetServo):
  # 각도는 180도를 넘을 수 없다.
  if degree > 180:
    degree = 180

  # 각도(degree)를 duty로 변경한다.
  duty = SERVO_MIN_DUTY+(degree*(SERVO_MAX_DUTY-SERVO_MIN_DUTY)/180.0)
  # 변경된 duty값을 서보 pwm에 적용
  targetServo.ChangeDutyCycle(duty)

def receive_message(client, data, message):
    rx_message = str(message.payload.decode("utf-8"))
    print("Message received : ", rx_message)
    if rx_message == "work!":
        setup_GPIO()

        if GPIO.input(button_pin1) == GPIO.HIGH: # 1번 버튼이 눌러지면?
           setServoPos(30, servo1) # 1번 모터를 30도 연다
           sleep(1) # 1초 동안 대기
           setServoPos(0, servo1) # 0도로 복귀
        if GPIO.input(button_pin2) == GPIO.HIGH: # 1번 버튼이 눌러지면?
           setServoPos(30, servo2) # 2번 모터를 30도 연다
           sleep(1) # 1초 동안 대기
           setServoPos(0, servo2) # 0도로 복귀
        if GPIO.input(button_pin3) == GPIO.HIGH: # 1번 버튼이 눌러지면?
           setServoPos(30, servo3) # 1번 모터를 30도 연다
           sleep(1) # 1초 동안 대기
           setServoPos(0, servo3) # 0도로 복귀

    elif rx_message == "stop!":
        GPIO.cleanup() # 자판기 사용 종료 시 동작 안하도록

BROKER_ADDRESS = "3.35.229.16"
wmi_client = mqtt.Client("wmi_client")
wmi_client.connect(BROKER_ADDRESS, 1883)
wmi_client.subscribe("rpi/4") # 도어락이 4번 리소스
wmi_client.on_message = receive_message
wmi_client.loop_forever() # MQTT Message를 계속 듣고 있음