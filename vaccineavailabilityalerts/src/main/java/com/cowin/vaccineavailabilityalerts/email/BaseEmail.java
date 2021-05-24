package com.cowin.vaccineavailabilityalerts.email;


import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class BaseEmail {

    static  Set<String>  districtSet = new HashSet<>();
    static  Set<String> pinCodeSet = new HashSet<>();

    public static void sendmail (String data, String email) {

        System.out.println("SimpleEmail Start");

        String smtpHostServer = "smtp.gmail.com";
        final String emailID = "ravi.hpe1@gmail.com";
        final String password = "Ravi@123";
        String receipent = email;
        /*if(!data.equals("Jaagte raho")) {
            receipent = "hiravi2410@gmail.com,";
        } else {
            receipent = "hiravi2410@gmail.com";
        }*/

        Properties props = System.getProperties();

        props.put("mail.smtp.host", smtpHostServer);
        props.put("mail.smtp.starttls.enable",true);
        props.put("mail.smtp.port","587");
        props.put("mail.smtp.auth",true);
        props.put("mail.smtp.ssl.protocols","TLSv1.2");


        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailID,password);
            }
        });

        EmailUtil.sendEmail(session, receipent, "SimpleEmail Testing Subject", data);
    }

    public static void sendmailPinCode(String message, String email) {
        if(!pinCodeSet.contains(email)){
            pinCodeSet.add(email);
            sendmail(message, email);
        }
    }

    public static void sendmailDistrict(String message, String email) {
        if (!districtSet.contains(email)){
            districtSet.add(email);
            sendmail(message,email);
        }
    }

    public static void clearData(){
        districtSet.clear();
        pinCodeSet.clear();
    }
}
