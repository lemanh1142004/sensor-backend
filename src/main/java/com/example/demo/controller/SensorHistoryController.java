package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.SensorDataHistory;
import com.example.demo.service.SensorHistoryService;

@RestController
@RequestMapping("/api/sensors")
@CrossOrigin("*")
public class SensorHistoryController {

    private final SensorHistoryService service;

    public SensorHistoryController(SensorHistoryService service) {
        this.service = service;
    }

    @GetMapping("/history/{deviceId}")
    public List<SensorDataHistory> getHistory(@PathVariable String deviceId) {
        return service.getHistory(deviceId);
    }
}