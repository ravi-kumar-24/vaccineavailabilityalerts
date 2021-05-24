package com.cowin.vaccineavailabilityalerts.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegister {

    private int pinCode;
    private String district;
    private int age;
    private String vaccineType;
    private String email;


}
