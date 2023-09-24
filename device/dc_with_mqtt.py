#import RPi.GPIO as GPIO
from time import sleep
import paho.mqtt.client as mqtt

# 모터 상태
STOP  = 0
FORWARD  = 1
BACKWARD = 2

# 모터 채널
CH1 = 0

# PIN 입출력 설정
OUTPUT = 1
INPUT = 0

# PIN 설정
HIGH = 1
LOW = 0

# 실제 핀 정의
#PWM PIN
ENA = 26  #37 pin

#GPIO PIN
IN1 = 19  #35 pin
IN2 = 13  #33 pin

def setPinConfig(EN, INA, INB):        
    GPIO.setup(EN, GPIO.OUT)
    GPIO.setup(INA, GPIO.OUT)
    GPIO.setup(INB, GPIO.OUT)
    # 100khz 로 PWM 동작 시킴 
    pwm = GPIO.PWM(EN, 100) 
    # 우선 PWM 멈춤.   
    pwm.start(0) 
    return pwm

# 모터 제어 함수
def setMotorControl(pwm, INA, INB, speed, stat):

    #모터 속도 제어 PWM
    pwm.ChangeDutyCycle(speed)  
    
    if stat == FORWARD:
        GPIO.output(INA, HIGH)
        GPIO.output(INB, LOW)
        
    #뒤로
    elif stat == BACKWARD:
        GPIO.output(INA, LOW)
        GPIO.output(INB, HIGH)
        
    #정지
    elif stat == STOP:
        GPIO.output(INA, LOW)
        GPIO.output(INB, LOW)

def setMotor(ch, speed, stat):
    if ch == CH1:
        #pwmA는 핀 설정 후 pwm 핸들을 리턴 받은 값이다.
        setMotorControl(pwmA, IN1, IN2, speed, stat)

def receive_message(client, data, message):
    rx_message = str(message.payload.decode("utf-8"))
    print("Message received : ", rx_message)
    if rx_message == "work!":
        print(rx_message)
        #setMotor(CH1, 80, FORWARD) # 앞으로 80% 속도로 회전
    elif rx_message == "stop!":
        print(rx_message)
        #setMotor(CH1, 80, STOP) # 선풍기 정지 

# GPIO 모드 설정 
#GPIO.setmode(GPIO.BCM)
#모터 핀 설정
#핀 설정후 PWM 핸들 얻어옴 
#pwmA = setPinConfig(ENA, IN1, IN2)

BROKER_ADDRESS = "3.35.229.16"
wmi_client = mqtt.Client("wmi_client")
wmi_client.connect(BROKER_ADDRESS, 1883)
wmi_client.subscribe("rpi/5") # 선풍기가 2번 리소스
wmi_client.on_message = receive_message
wmi_client.loop_forever() # MQTT Message를 계속 듣고 있음