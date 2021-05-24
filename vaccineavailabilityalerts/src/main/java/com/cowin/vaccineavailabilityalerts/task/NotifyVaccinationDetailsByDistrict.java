package com.cowin.vaccineavailabilityalerts.task;

import com.cowin.vaccineavailabilityalerts.Utils;
import com.cowin.vaccineavailabilityalerts.callbacks.VaccineNotifierCallback;
import com.cowin.vaccineavailabilityalerts.model.RequestedDistrictsWithAge;
import com.cowin.vaccineavailabilityalerts.model.SearchType;
import com.cowin.vaccineavailabilityalerts.service.SpeechService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Scope("prototype")
public class NotifyVaccinationDetailsByDistrict extends NotifyVaccinationDetails {

    private RequestedDistrictsWithAge requestedDistrictsWithAge;

    @Value("${vaccination.details.by.district.url}")
    String baseUrl;

    public NotifyVaccinationDetailsByDistrict(RestTemplate restTemplate) {
        super(restTemplate);
    }

    @Override
    protected String getBaseURLWithParams() {
        return String.format(baseUrl, requestedDistrictsWithAge.getDistrictId(), Utils.getCurrentDateAsString());
    }

    @Override
    protected String getType() {
        return "DistrictId";
    }

    public void execute(RequestedDistrictsWithAge requestedDistrictsWithAge, VaccineNotifierCallback vaccineNotifierCallback) {
        this.requestedDistrictsWithAge = requestedDistrictsWithAge;
        Notify(SearchType.DISTRICT, requestedDistrictsWithAge.getDistrictId(),
                requestedDistrictsWithAge.getAge(), requestedDistrictsWithAge.getVaccineType(),
                requestedDistrictsWithAge.getEmail(), vaccineNotifierCallback);
    }
}
