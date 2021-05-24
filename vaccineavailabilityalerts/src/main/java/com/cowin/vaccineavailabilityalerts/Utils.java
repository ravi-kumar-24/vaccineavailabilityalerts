package com.cowin.vaccineavailabilityalerts;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {

    public static String getCurrentDateAsString() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDateTime now = LocalDateTime.now();
        return dateTimeFormatter.format(now);
    }

    public static int getValidAge(int age) {
        if(age >= 45){
            age = 45;
        }else{
            age = 18;
        }
        return age;
    }

}
