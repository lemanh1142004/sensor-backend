package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TwilioVoiceController {

@GetMapping(value = "/api/twilio/voice", produces = "application/xml")
public String voice() {
    return "<Response>" +
           "<Say loop=\"2\">" +
           "Fire warning. Dangerous condition detected. Please check immediately." +
           "</Say>" +
           "</Response>";
}
}