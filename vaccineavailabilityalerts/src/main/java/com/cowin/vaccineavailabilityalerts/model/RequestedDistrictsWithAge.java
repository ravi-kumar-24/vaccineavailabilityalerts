package com.cowin.vaccineavailabilityalerts.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class RequestedDistrictsWithAge {
    Integer districtId;
    Integer age;
    String vaccineType;
    String email;
}
