package com.example.demo.dto;
import lombok.Data;

@Data
public class SensorMessage {
    private String deviceId;
    private Double temperature;
    private Double humidity;
    private Double gas;
    private Double smoke;
    private Boolean isSafe;
    private Boolean manualMode;
    private Boolean fan;
    private Boolean buzzer;
    private Boolean ledRed;
    private Integer activeAlerts;
}