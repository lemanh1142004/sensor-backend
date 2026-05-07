package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.SensorData;
import com.example.demo.repository.SensorDataRepository;

@RestController
@RequestMapping("/api/sensors")
@CrossOrigin("*")
public class SensorController {

    private final SensorDataRepository sensorRepository;

    public SensorController(SensorDataRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    @GetMapping("/latest/{deviceId}")
    public SensorData latest(@PathVariable String deviceId) {
        return sensorRepository.findTopByDeviceIdOrderByCreatedAtDesc(deviceId);
    }

    @GetMapping("/history/{deviceId}")
    public List<SensorData> history(@PathVariable String deviceId) {
        return sensorRepository.findTop50ByDeviceIdOrderByCreatedAtDesc(deviceId);
    }
}