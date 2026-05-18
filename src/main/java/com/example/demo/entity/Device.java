package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "devices")
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String deviceId;
    private String deviceName;
    private String location;
    private Double maxTemp;
    private Double maxGas;
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

    public Device() {}
    public Device(String deviceId, String deviceName, String location, Double maxTemp, Double maxGas) {
        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.location = location;
        this.maxTemp = maxTemp;
        this.maxGas = maxGas;
    }
    public Long getId() {
        return id;
    }
    public String getDeviceId() {
        return deviceId;
    }
    public String getDeviceName() {
        return deviceName;
    }
    public String getLocation() {
        return location;
    }
    public Double getMaxTemp() {
        return maxTemp;
    }
    public Double getMaxGas() {
        return maxGas;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public void setMaxTemp(Double maxTemp) {
        this.maxTemp = maxTemp;
    }
    public void setMaxGas(Double maxGas) {
        this.maxGas = maxGas;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
}