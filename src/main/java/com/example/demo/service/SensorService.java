package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.dto.SensorMessage;
import com.example.demo.entity.Alert;
import com.example.demo.entity.SensorData;
import com.example.demo.repository.AlertRepository;
import com.example.demo.repository.SensorDataRepository;

@Service
public class SensorService {

    private final SensorDataRepository sensorRepository;
    private final AlertRepository alertRepository;
    private final TwilioCallService twilioCallService;

    private Long dangerStartTime = null;
    private Long lastCallTime = null;

    private static final long CALL_INTERVAL_MS = 60000;    // 1 phút
    private static final long DANGER_CONFIRM_MS = 30000; // 30 giây

    public SensorService(
            SensorDataRepository sensorRepository,
            AlertRepository alertRepository,
            TwilioCallService twilioCallService
    ) {
        this.sensorRepository = sensorRepository;
        this.alertRepository = alertRepository;
        this.twilioCallService = twilioCallService;
    }

    public SensorData saveFromMqtt(SensorMessage msg) {
        String dangerLevel = calculateDangerLevel(msg);

        SensorData data = new SensorData();

        data.setDeviceId(msg.getDeviceId() != null ? msg.getDeviceId() : "esp32_01");
        data.setTemperature(msg.getTemperature());
        data.setHumidity(msg.getHumidity());
        data.setGas(msg.getGas());
        data.setSmoke(msg.getSmoke());

        data.setIsSafe(msg.getIsSafe());
        data.setManualMode(defaultFalse(msg.getManualMode()));
        data.setFan(defaultFalse(msg.getFan()));
        data.setBuzzer(defaultFalse(msg.getBuzzer()));
        data.setLedRed(defaultFalse(msg.getLedRed()));
        data.setActiveAlerts(msg.getActiveAlerts() != null ? msg.getActiveAlerts() : 0);

        data.setDangerLevel(dangerLevel);

        SensorData saved = sensorRepository.save(data);

        if (!"SAFE".equals(dangerLevel)) {
            saveAlert(saved, dangerLevel);
        }

        handleEmergencyCall(dangerLevel);

        return saved;
    }

   private void handleEmergencyCall(String dangerLevel) {
    long now = System.currentTimeMillis();

    if ("DANGER".equals(dangerLevel)) {

        if (dangerStartTime == null) {
            dangerStartTime = now;
            System.out.println("Bắt đầu trạng thái nguy hiểm...");
        }

        long dangerDuration = now - dangerStartTime;

        boolean dangerConfirmed = dangerDuration >= DANGER_CONFIRM_MS;
        boolean canCallNow = lastCallTime == null || now - lastCallTime >= CALL_INTERVAL_MS;
        boolean userAnswered = twilioCallService.isUserAnswered();

        if (dangerConfirmed && canCallNow && !userAnswered) {
            try {
                System.out.println("Nguy hiểm >= 30 giây → gọi điện cảnh báo...");
                twilioCallService.callEmergency();
                lastCallTime = now;
            } catch (Exception e) {
                System.out.println("Lỗi khi gọi Twilio: " + e.getMessage());
            }
        }

    } else {
        // 🔥 SAFE → reset ngay lập tức
        dangerStartTime = null;
        lastCallTime = null;
        twilioCallService.resetAnswered();

        System.out.println("Trạng thái an toàn → reset hệ thống.");
    }
}
    private String calculateDangerLevel(SensorMessage msg) {
        // boolean unsafe = Boolean.FALSE.equals(msg.getIsSafe());
        // boolean highGas = msg.getGas() != null && msg.getGas() >= 1000;
        // boolean highSmoke = msg.getSmoke() != null && msg.getSmoke() >= 1000;
        // boolean highTemp = msg.getTemperature() != null && msg.getTemperature() >= 50;

        // if (highTemp || highGas || highSmoke || unsafe) {
        //     return "DANGER";
        // }

        // return "SAFE";
           return "DANGER";
    }

    private void saveAlert(SensorData data, String dangerLevel) {
        Alert alert = new Alert();

        alert.setDeviceId(data.getDeviceId());
        alert.setDangerLevel(dangerLevel);
        alert.setAlertType("FIRE_WARNING");
        alert.setMessage("Cảnh báo! Dữ liệu cảm biến vượt ngưỡng an toàn.");

        alertRepository.save(alert);
    }

    private Boolean defaultFalse(Boolean value) {
        return value != null && value;
    }
}