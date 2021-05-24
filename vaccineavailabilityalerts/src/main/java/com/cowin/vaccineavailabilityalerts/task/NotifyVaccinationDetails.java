package com.cowin.vaccineavailabilityalerts.task;

import com.cowin.vaccineavailabilityalerts.callbacks.VaccineNotifierCallback;
import com.cowin.vaccineavailabilityalerts.email.BaseEmail;
import com.cowin.vaccineavailabilityalerts.model.Center;
import com.cowin.vaccineavailabilityalerts.model.SearchType;
import com.cowin.vaccineavailabilityalerts.model.Session;
import com.cowin.vaccineavailabilityalerts.model.VaccinationInfo;
import com.cowin.vaccineavailabilityalerts.service.SpeechService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
@Slf4j
public abstract class NotifyVaccinationDetails {

    private final RestTemplate restTemplate;

    public NotifyVaccinationDetails(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    protected abstract String getBaseURLWithParams();

    protected abstract String getType();

    public List<Center> getVaccinationCentres(Integer data) {
        List<Center> centers = null;
        log.info("Looking for eligible vaccination centres at {} = {}", getType(), data);
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("user-agent", "Mozilla/5.0");
            HttpEntity entity = new HttpEntity(httpHeaders);
            ResponseEntity<VaccinationInfo> vaccinationInfoResponse = restTemplate.exchange(getBaseURLWithParams(), HttpMethod.GET, entity, VaccinationInfo.class);
            if (vaccinationInfoResponse.getBody() != null) {
                centers = vaccinationInfoResponse.getBody().getCenters();
                log.info("Found {} vaccination centres at {} = {}", centers.size(), getType(), data);
            }
        }catch (Exception e){
            log.error("error "+e.getCause());
            log.error("error message "+e.getMessage());
        }
        return centers;
    }

    protected String Notify(SearchType searchType, Integer data , int minAge, String vaccineType, String email, VaccineNotifierCallback vaccineNotifierCallback) {
        List<Center> centres = getVaccinationCentres(data);
        StringBuilder sb = new StringBuilder();
        if(centres != null) {
            int count = 0;
            for (Center center : centres) {
                List<Session> sessions = center.getSessions();
                for (Session session : sessions) {
                    if(session.getMinAgeLimit() == minAge && session.getAvailableCapacity() > 0) {
                        if(vaccineType == null || session.getVaccine().equals(vaccineType)) {
                            count++;
                           // vaccineNotifierCallback.listen("Vaccine " + session.getVaccine() + " for " + session.getMinAgeLimit() + " available at center " + center.getName() + " pin code " + center.getPincode() + " on date " + session.getDate() + ", available quantity  " + session.getAvailableCapacity());
                            sb.append("Vaccine " + session.getVaccine() + " for " + session.getMinAgeLimit() + " available at center " + center.getName() + " pin code " + center.getPincode() + " on date " + session.getDate() + ", available quantity  " + session.getAvailableCapacity()+"\n\n");
                        }
                    }
                }
            }
            if (count > 0) {
                //vaccineNotifierCallback.listen("found total " + count + " active vaccine centres for age " + minAge + " for "+ getType() + " = " + data);
                log.info("found total {} active vaccine centres for age {} for {} = {}", count, minAge, getType(), data);
                sb.append("found total "+ count +" active vaccine centres for age "+ minAge +" for " +getType()+" = " +data);
                if(SearchType.DISTRICT.equals(searchType)) {
                    vaccineNotifierCallback.listenDistrict(sb.toString(), email);
                }else{
                    vaccineNotifierCallback.listenPinCode(sb.toString(), email);
                }
                //BaseEmail.sendmail(sb.toString());
            }else {
                log.info("Sorry no vaccine available for  age {}", minAge);
                sb.append(String.format("Sorry no vaccine available for  age {}", minAge));
            }
            log.info("Search complete for {} = {}\n\n", getType(), data);
        }else {
            log.info("No Centre found for given {}", getType());
        }

        return sb.toString();
    }

}