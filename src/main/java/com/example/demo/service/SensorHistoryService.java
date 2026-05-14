package com.example.demo.service;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.demo.entity.SensorData;
import com.example.demo.entity.SensorDataHistory;
import com.example.demo.repository.SensorDataHistoryRepository;
import com.example.demo.repository.SensorDataRepository;

@Service
public class SensorHistoryService {

    private final SensorDataRepository sensorDataRepository;
    private final SensorDataHistoryRepository historyRepository;

    public SensorHistoryService(
            SensorDataRepository sensorDataRepository,
            SensorDataHistoryRepository historyRepository) {
        this.sensorDataRepository = sensorDataRepository;
        this.historyRepository = historyRepository;
    }

    @Scheduled(fixedRate = 60000)
    public void saveHistoryEveryMinute() {

        List<SensorData> currentData = sensorDataRepository.findAll();

        for (SensorData data : currentData) {

            SensorDataHistory history = new SensorDataHistory();

            history.setDeviceId(data.getDeviceId());
            history.setTemperature(data.getTemperature());
            history.setHumidity(data.getHumidity());
            history.setGas(data.getGas());
            history.setSmoke(data.getSmoke());

            history.setIsSafe(data.getIsSafe());
            history.setManualMode(data.getManualMode());
            history.setFan(data.getFan());
            history.setBuzzer(data.getBuzzer());
            history.setLedRed(data.getLedRed());

            history.setActiveAlerts(data.getActiveAlerts());
            history.setDangerLevel(data.getDangerLevel());

            historyRepository.save(history);
        }

        System.out.println("Saved sensor history");
    }
}