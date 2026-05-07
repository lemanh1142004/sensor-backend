package com.example.demo.entity;


import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "sensor_data")
public class SensorData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    private String dangerLevel;

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
}