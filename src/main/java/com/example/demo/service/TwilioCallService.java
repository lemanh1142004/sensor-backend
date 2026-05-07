package com.example.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.type.PhoneNumber;

import jakarta.annotation.PostConstruct;

@Service
public class TwilioCallService {

    @Value("${twilio.accountSid}")
    private String accountSid;

    @Value("${twilio.authToken}")
    private String authToken;

    @Value("${twilio.fromNumber}")
    private String fromNumber;

    @Value("${twilio.toNumber}")
    private String toNumber;

    @Value("${twilio.statusCallbackUrl:}")
    private String statusCallbackUrl;

    private boolean userAnswered = false;

    @PostConstruct
    public void init() {
        Twilio.init(accountSid, authToken);
        System.out.println("Twilio initialized.");
    }

public void callEmergency() {

    if (userAnswered) {
        System.out.println("Người dùng đã nhấc máy trước đó, không gọi nữa.");
        return;
    }

    String twiml = "<Response>" +
                   "<Say loop=\"2\">" +
                   "Fire warning. Dangerous condition detected. Please check immediately." +
                   "</Say>" +
                   "</Response>";

    Call call = Call.creator(
            new PhoneNumber(toNumber),
            new PhoneNumber(fromNumber),
            new com.twilio.type.Twiml(twiml)
    )
    .setStatusCallback(java.net.URI.create(statusCallbackUrl))
    .setStatusCallbackMethod(com.twilio.http.HttpMethod.POST)
    .setStatusCallbackEvent(java.util.Arrays.asList(
            "initiated",
            "ringing",
            "answered",
            "completed"
    ))
    .create();

    System.out.println("Đã gọi Twilio. Call SID: " + call.getSid());
}
    public void markAnswered() {
        this.userAnswered = true;
        System.out.println("Đã ghi nhận người dùng nhấc máy. Dừng gọi lại.");
    }

    public boolean isUserAnswered() {
        return userAnswered;
    }

    public void resetAnswered() {
        this.userAnswered = false;
        System.out.println("Reset trạng thái nhấc máy.");
    }
}