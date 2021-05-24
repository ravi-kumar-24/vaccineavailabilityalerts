package com.cowin.vaccineavailabilityalerts.callbacks;

import com.cowin.vaccineavailabilityalerts.email.BaseEmail;
import com.cowin.vaccineavailabilityalerts.service.SpeechService;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;

@Slf4j
public class VaccineNotifierCallback {

    private final SpeechService speechService;

    public VaccineNotifierCallback() {
        this.speechService = new SpeechService();
    }

    public void listenDistrict(String message, String email) {
        System.out.println("======== this is VaccineNotifierCallback districtSet =========================== "+message);
            BaseEmail.sendmailDistrict(message,email);

        //
       // speechService.speak(message);
    }

    public void listenPinCode(String message, String email) {
        System.out.println("======== this is VaccineNotifierCallback pinCodeSet =========================== "+message);
            BaseEmail.sendmailPinCode(message,email);
        //BaseEmail.sendmail(message);
        // speechService.speak(message);
    }

}
