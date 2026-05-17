package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.SensorDataHistory;

public interface SensorDataHistoryRepository extends JpaRepository<SensorDataHistory, Long> {

    List<SensorDataHistory> findTop50ByDeviceIdOrderByCreatedAtDesc(String deviceId);
}