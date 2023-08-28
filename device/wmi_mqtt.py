import paho.mqtt.client as mqtt

def receive_message(client, data, message):
    print("Message received : ", str(message.payload.decode("utf-8")))
    print("Message topic : ", message.topic)
    print("Message qos : ", message.qos)

BROKER_ADDRESS = "13.211.135.69"
wmi_client = mqtt.Client("wmi_client")
wmi_client.connect(BROKER_ADDRESS, 1883)
wmi_client.subscribe("rpi/1")
wmi_client.on_message = receive_message
wmi_client.loop_forever()