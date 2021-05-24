package com.cowin.vaccineavailabilityalerts.service;

import com.cowin.vaccineavailabilityalerts.Bootstrapper;
import com.cowin.vaccineavailabilityalerts.Utils;
import com.cowin.vaccineavailabilityalerts.callbacks.VaccineNotifierCallback;
import com.cowin.vaccineavailabilityalerts.email.BaseEmail;
import com.cowin.vaccineavailabilityalerts.model.RequestedDistrictsWithAge;
import com.cowin.vaccineavailabilityalerts.model.RequestedPinCodesWithAge;
import com.cowin.vaccineavailabilityalerts.task.NotifyVaccinationDetailsByDistrict;
import com.cowin.vaccineavailabilityalerts.task.NotifyVaccinationDetailsByPin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
@Service
public class CoronaVaccinationService {

    private final ApplicationContext applicationContext;
    private final Set<RequestedPinCodesWithAge> requestedPinCodesWithAges;
    private final Set<RequestedDistrictsWithAge> requestedDistrictsWithAges;


    private final ExecutorService executorService;
    private final Bootstrapper bootstrapper;

    public CoronaVaccinationService(ApplicationContext applicationContext, Bootstrapper bootstrapper) {
        this.applicationContext = applicationContext;
        this.requestedPinCodesWithAges = new HashSet<>();
        this.requestedDistrictsWithAges = new HashSet<>();
        this.bootstrapper = bootstrapper;
        this.executorService = Executors.newFixedThreadPool(10);
    }

    public String registerPinCode(Integer pinCode, int age, String vaccineType, String email) {
        log.info("registered a new pin code = {}, age = {}", pinCode, age);
        age = Utils.getValidAge(age);
        requestedPinCodesWithAges.add(new RequestedPinCodesWithAge(pinCode, age, sanitizeVaccineType(vaccineType),email));
        return "Successfully registered a pin code = "+ pinCode + " for age = " + age;
    }

    public String registerDistrict(String districtName, int age, String vaccineType, String email) {
        log.info("registered a new district  = {}, age = {}", districtName, age);
        age = Utils.getValidAge(age);
        if (districtName != null) {
            Integer districtId = bootstrapper.getDistrictIdByName(districtName.toLowerCase().replace(" ", ""));
            if (districtId != null) {
                requestedDistrictsWithAges.add(new RequestedDistrictsWithAge(districtId, age, sanitizeVaccineType(vaccineType),email));
                return "Successfully registered a district = " + districtName + " for age = " + age;
            }else {
                throw new IllegalArgumentException("No district found by name = " + districtName);
            }
        }else {
            throw new IllegalArgumentException("Missing district name in the request");
        }
    }

    @Scheduled(fixedRate = 100000)
    public void getVaccinationInfo() {
        log.info("Checking vaccine availability for requested pin codes and districts");
        List<Future> futures = new ArrayList<>();
        requestedPinCodesWithAges.forEach(requestedPinCodesWithAge -> futures
                .add(executorService.submit(() -> applicationContext.getBean(NotifyVaccinationDetailsByPin.class)
                        .execute(requestedPinCodesWithAge, new VaccineNotifierCallback()))));

        requestedDistrictsWithAges.forEach(requestedDistrictsWithAge -> futures.
                add(executorService.submit(() -> applicationContext.getBean(NotifyVaccinationDetailsByDistrict.class)
                        .execute(requestedDistrictsWithAge, new VaccineNotifierCallback()))));

        futures.forEach(future -> {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                log.error("Exception = {}", e.getMessage());
            }
        });
        log.info("Successfully Checked vaccine available for pin codes and districts");
    }

    private String sanitizeVaccineType(String vaccineType) {
        return vaccineType.equals("") ? null : vaccineType;
    }

    @Scheduled(cron ="0 0 1 * * *" )
    public void clearEmailDataEveryDay() {
        log.info("Staring Cleaning pin codes and districts email id");
        BaseEmail.clearData();
        log.info("Ending Cleaning pin codes and districts email id");
    }
}