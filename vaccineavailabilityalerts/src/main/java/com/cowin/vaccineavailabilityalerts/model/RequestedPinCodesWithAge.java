package com.cowin.vaccineavailabilityalerts.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class RequestedPinCodesWithAge {
    Integer pinCode;
    Integer age;
    String vaccineType;
    String email;
}
