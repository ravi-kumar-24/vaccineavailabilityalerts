
package com.cowin.vaccineavailabilityalerts.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class StateDistrict {

    @JsonProperty("districts")
    public List<District> districts;
    @JsonProperty("ttl")
    public Integer ttl;

}
