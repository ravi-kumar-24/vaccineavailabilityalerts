package com.cowin.vaccineavailabilityalerts.model;

public enum SearchType {

    PINCODE,
    DISTRICT;

    String type;

    public String getType() {
        return type;
    }
}
