package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.SensorData;

public interface SensorDataRepository extends JpaRepository<SensorData, Long> {
    SensorData findTopByDeviceIdOrderByCreatedAtDesc(String deviceId);

    List<SensorData> findTop50ByDeviceIdOrderByCreatedAtDesc(String deviceId);
}