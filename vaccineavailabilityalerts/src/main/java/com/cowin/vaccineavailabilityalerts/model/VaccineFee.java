
package com.cowin.vaccineavailabilityalerts.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VaccineFee {

    @JsonProperty("vaccine")
    public String vaccine;
    @JsonProperty("fee")
    public String fee;

}
