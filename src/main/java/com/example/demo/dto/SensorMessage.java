package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SensorMessage {

    private String deviceId;

    @JsonProperty("temp")
    private Double temperature;

    @JsonProperty("hum")
    private Double humidity;

    private Double gas;
    private Double smoke;

    @JsonProperty("isSafe")
    private Boolean isSafe;

    @JsonProperty("manualMode")
    private Boolean manualMode;

    private Boolean fan;
    private Boolean buzzer;

    @JsonProperty("ledRed")
    private Boolean ledRed;

    private Integer activeAlerts;
}