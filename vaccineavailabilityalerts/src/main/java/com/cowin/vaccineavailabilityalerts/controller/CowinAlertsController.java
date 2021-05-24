package com.cowin.vaccineavailabilityalerts.controller;

import com.cowin.vaccineavailabilityalerts.model.UserRegister;
import com.cowin.vaccineavailabilityalerts.service.CoronaVaccinationService;
import com.cowin.vaccineavailabilityalerts.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
class CowinAlertsController {

    @Autowired
    private CoronaVaccinationService coronaVaccinationService;

    @Autowired
    private UserDetailsService userDetailsService;

    @GetMapping("/")
    String index() {
        return "index";
    }

    @RequestMapping(value="/registerPin", method= RequestMethod.POST)
    ModelAndView registerPin(@ModelAttribute UserRegister userRegister) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user-register-pin");
        modelAndView.addObject("userRegister", userRegister);
        userDetailsService.addUserInFile(String.valueOf(userRegister.getPinCode()), userRegister.getAge(), userRegister.getVaccineType(), userRegister.getEmail());
        coronaVaccinationService.registerPinCode(userRegister.getPinCode(), userRegister.getAge(), userRegister.getVaccineType(), userRegister.getEmail());
        return modelAndView;
    }

    @RequestMapping(value="/registerDistrict", method= RequestMethod.POST)
    ModelAndView registerDistrict(@ModelAttribute UserRegister userRegister) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user-register-district");
        modelAndView.addObject("userRegister", userRegister);
        userDetailsService.addUserInFile(userRegister.getDistrict(), userRegister.getAge(), userRegister.getVaccineType(), userRegister.getEmail());
        coronaVaccinationService.registerDistrict(userRegister.getDistrict(), userRegister.getAge(), userRegister.getVaccineType(), userRegister.getEmail());
        return modelAndView;
    }

}