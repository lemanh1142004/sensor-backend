package com.example.demo.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "sensor_data_history")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SensorDataHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String deviceId;

    @JsonProperty("temp") // ✨ Ánh xạ từ JSON "temp" của ESP32
    private Double temperature;

    @JsonProperty("hum")  // ✨ Ánh xạ từ JSON "hum" của ESP32
    private Double humidity;

    private Double gas;
    private Double smoke;

    @JsonProperty("isSafe")
    private Boolean isSafe;

    private Boolean manualMode;
    private Boolean fan;
    private Boolean buzzer;
    private Boolean ledRed;

    private Integer activeAlerts;
    private String dangerLevel;

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
}