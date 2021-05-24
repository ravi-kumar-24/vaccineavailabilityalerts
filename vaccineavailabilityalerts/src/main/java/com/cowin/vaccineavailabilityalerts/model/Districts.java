
package com.cowin.vaccineavailabilityalerts.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class Districts {

    @JsonProperty("districts")
    public List<District> districts;
    @JsonProperty("ttl")
    public Integer ttl;

}
