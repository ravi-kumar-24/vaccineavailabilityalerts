
package com.cowin.vaccineavailabilityalerts.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class District {

    @JsonProperty("district_id")
    public Integer districtId;
    @JsonProperty("district_name")
    public String districtName;

}
