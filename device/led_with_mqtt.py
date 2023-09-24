import RPi.GPIO as GPIO
import time
import paho.mqtt.client as mqtt

def receive_message(client, data, message):
    rx_message = str(message.payload.decode("utf-8"))
    print("Message received : ", rx_message)
    if rx_message == "work!":
        GPIO.output(17,False) # LED 점등
    elif rx_message == "stop!":
        GPIO.output(17,True) # LED 끔

GPIO.setmode(GPIO.BCM)
GPIO.setup(17, GPIO.OUT)

BROKER_ADDRESS = "3.35.229.16"
wmi_client = mqtt.Client("wmi_client")
wmi_client.connect(BROKER_ADDRESS, 1883)
wmi_client.subscribe("rpi/3") # 전등이 3번 리소스
wmi_client.on_message = receive_message
wmi_client.loop_forever() # MQTT Message를 계속 듣고 있음