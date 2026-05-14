package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.SensorDataHistory;

public interface SensorDataHistoryRepository extends JpaRepository<SensorDataHistory, Long> {
}