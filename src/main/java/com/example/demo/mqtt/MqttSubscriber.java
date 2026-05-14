package com.example.demo.mqtt;


import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.demo.dto.SensorMessage;
import com.example.demo.service.SensorService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;

@Component
public class MqttSubscriber {

    @Value("${mqtt.broker}")
    private String broker;

    @Value("${mqtt.username}")
    private String username;

    @Value("${mqtt.password}")
    private String password;

    @Value("${mqtt.clientId}")
    private String clientId;

    @Value("${mqtt.telemetryTopic}")
    private String telemetryTopic;

    private final SensorService sensorService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public MqttSubscriber(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @PostConstruct
    
    public void connect() {
        try {
MqttClient client = new MqttClient(
        broker,
        clientId + "-" + System.currentTimeMillis()
);
            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(username);
            options.setPassword(password.toCharArray());
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            options.setConnectionTimeout(30);
            options.setKeepAliveInterval(60);
            options.setAutomaticReconnect(true);

            client.connect(options);

            client.subscribe(telemetryTopic, (topic, message) -> {
                String payload = new String(message.getPayload());

                try {
                    SensorMessage sensorMessage =
                            objectMapper.readValue(payload, SensorMessage.class);

                    sensorService.saveFromMqtt(sensorMessage);

                    System.out.println("Saved MQTT data: " + payload);
                } catch (Exception e) {
                    System.out.println("Invalid MQTT payload: " + payload);
                    e.printStackTrace();
                }
            });

            System.out.println("MQTT connected.");
            System.out.println("Listening topic: " + telemetryTopic);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}