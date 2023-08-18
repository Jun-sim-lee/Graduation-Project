package com.junsim.whereami.controller;

import com.junsim.whereami.config.MqttGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MqttController {
    @Autowired
    MqttGateway mqttGateway;

    @PostMapping("/sendMessage")
    public ResponseEntity<?> publish(@RequestBody String mqttMessage){
        mqttGateway.sendToMqtt(mqttMessage, "rpi/1");
        return ResponseEntity.ok("Success");
    }
}
