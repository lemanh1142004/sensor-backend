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
@Table(name = "sensor_data")
@JsonIgnoreProperties(ignoreUnknown = true) // ✨ QUAN TRỌNG: Bỏ qua trường "smoke" và "flame_detected" bị thiếu từ ESP32
public class SensorData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String deviceId;

    @JsonProperty("temp") 
    private Double temperature;

    @JsonProperty("hum")  
    private Double humidity;

    private Double gas;
    
    private Double smoke; // Tự động nhận null/0 nếu ESP32 không gửi

    private Boolean flameDetected; // Tự động nhận null/0 nếu ESP32 không gửi

    private Boolean fan;
    private Boolean buzzer;
    
    @JsonProperty("ledRed") // ✨ Ép kiểu chữ hoa "ledRed" từ JSON vào led_red của DB
    private Boolean ledRed;

    @JsonProperty("manualMode") // ✨ Ép kiểu chữ hoa "manualMode" từ JSON vào manual_mode của DB
    private Boolean manualMode;

    @JsonProperty("isSafe") 
    private Boolean isSafe;

    private Integer activeAlerts;
    private String dangerLevel;

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
}