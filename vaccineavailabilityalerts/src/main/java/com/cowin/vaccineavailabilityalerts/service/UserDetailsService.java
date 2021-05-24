package com.cowin.vaccineavailabilityalerts.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

@Slf4j
@Service
public class UserDetailsService {

    @Value("${user.filename}")
    private  String fileName ="user.txt";
    FileOutputStream outputStream =null;

    public UserDetailsService() {
        try {
            outputStream = new FileOutputStream(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addUserInFile(String districtName, int age, String vaccineType, String email){

        String fileContent ="District Name "+ districtName + " AGE "+ age +" Vaccine Type "+ vaccineType+" Email "+ email +"\n";
        byte[] strToBytes = fileContent.getBytes();
        try {
            outputStream.write(strToBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
