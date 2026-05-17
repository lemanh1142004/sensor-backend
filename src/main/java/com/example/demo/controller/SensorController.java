package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.SensorData;
import com.example.demo.entity.SensorDataHistory;
import com.example.demo.repository.SensorDataHistoryRepository;
import com.example.demo.repository.SensorDataRepository;

@RestController
@RequestMapping("/api/sensors")
@CrossOrigin("*")
public class SensorController {

    private final SensorDataRepository sensorRepository;
    private final SensorDataHistoryRepository historyRepository;

    public SensorController(
            SensorDataRepository sensorRepository,
            SensorDataHistoryRepository historyRepository) {
        this.sensorRepository = sensorRepository;
        this.historyRepository = historyRepository;
    }

@GetMapping(value = "/latest/{deviceId}", produces = "application/json")
    public SensorData latest(@PathVariable String deviceId) {
        return sensorRepository.findTopByDeviceIdOrderByCreatedAtDesc(deviceId);
    }

@GetMapping(value = "/history/{deviceId}", produces = "application/json")
    public List<SensorDataHistory> history(@PathVariable String deviceId) {
        return historyRepository.findTop50ByDeviceIdOrderByCreatedAtDesc(deviceId);
    }
}