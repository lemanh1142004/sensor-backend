package com.example.demo.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Alert;

public interface AlertRepository extends JpaRepository<Alert, Long> {
    List<Alert> findTop50ByDeviceIdOrderByCreatedAtDesc(String deviceId);
}