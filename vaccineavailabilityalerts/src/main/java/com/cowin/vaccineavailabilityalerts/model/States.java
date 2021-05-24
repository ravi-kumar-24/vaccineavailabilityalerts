package com.cowin.vaccineavailabilityalerts.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class States {

    @JsonProperty("states")
    List<State> states;

}
