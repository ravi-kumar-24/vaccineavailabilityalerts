package com.cowin.vaccineavailabilityalerts.task;

import com.cowin.vaccineavailabilityalerts.Utils;
import com.cowin.vaccineavailabilityalerts.callbacks.VaccineNotifierCallback;
import com.cowin.vaccineavailabilityalerts.model.RequestedPinCodesWithAge;
import com.cowin.vaccineavailabilityalerts.model.SearchType;
import com.cowin.vaccineavailabilityalerts.service.SpeechService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Scope("prototype")
public class
NotifyVaccinationDetailsByPin extends NotifyVaccinationDetails {

    private RequestedPinCodesWithAge requestedPinCodesWithAge;

    @Value("${vaccination.details.by.pincode.url}")
    private String baseUrl;

    public NotifyVaccinationDetailsByPin(RestTemplate restTemplate) {
        super(restTemplate);
    }

    @Override
    protected String getBaseURLWithParams() {
        return String.format(baseUrl, requestedPinCodesWithAge.getPinCode(), Utils.getCurrentDateAsString());
    }

    @Override
    protected String getType() {
        return "PinCode";
    }

    public void execute(RequestedPinCodesWithAge requestedPinCodesWithAge, VaccineNotifierCallback vaccineNotifierCallback) {
        this.requestedPinCodesWithAge = requestedPinCodesWithAge;
        Notify(SearchType.PINCODE,requestedPinCodesWithAge.getPinCode(), requestedPinCodesWithAge.getAge(), requestedPinCodesWithAge.getVaccineType(), requestedPinCodesWithAge.getEmail(),vaccineNotifierCallback);
    }

}
