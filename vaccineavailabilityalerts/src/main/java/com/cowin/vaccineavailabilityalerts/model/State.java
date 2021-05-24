package com.cowin.vaccineavailabilityalerts.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class State {

    @JsonProperty("state_id")
    public Integer stateId;
    @JsonProperty("state_name")
    public String stateName;

}
