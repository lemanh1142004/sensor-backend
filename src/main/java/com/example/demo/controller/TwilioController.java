package com.example.demo.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.TwilioCallService;

@RestController
@CrossOrigin("*")
public class TwilioController {

    private final TwilioCallService twilioCallService;

    public TwilioController(TwilioCallService twilioCallService) {
        this.twilioCallService = twilioCallService;
    }

    @PostMapping("/api/twilio/call-status")
    public String callStatus(
            @RequestParam(value = "CallStatus", required = false) String callStatus,
            @RequestParam(value = "CallSid", required = false) String callSid
    ) {
        System.out.println("Twilio callback: CallSid=" + callSid + ", Status=" + callStatus);

        if ("in-progress".equals(callStatus) || "completed".equals(callStatus)) {
            twilioCallService.markAnswered();
        }

        return "OK";
    }
}