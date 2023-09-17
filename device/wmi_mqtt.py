import paho.mqtt.client as mqtt

def receive_message(client, data, message):
    rx_message = str(message.payload.decode("utf-8"))
    print("Message received : ", str(message.payload.decode("utf-8")))
    print("Message topic : ", message.topic)

BROKER_ADDRESS = "3.35.229.16"
wmi_client = mqtt.Client("wmi_client")
wmi_client.connect(BROKER_ADDRESS, 1883)
wmi_client.subscribe("rpi/1")
wmi_client.on_message = receive_message
wmi_client.loop_forever()