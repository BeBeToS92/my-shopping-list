package com.bebetos.shoppinglist.services;

import java.security.SecureRandom;

import org.springframework.stereotype.Service;

@Service
public class RandomGenerator {

    private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
    private static final String NUMBER = "0123456789";

    private static final String ALPHANUMERIC_STRING = CHAR_LOWER + CHAR_UPPER + NUMBER;
    private static final String NUMERIC_ONLY_STRING = NUMBER;

    private static SecureRandom random = new SecureRandom();


    public static String generateRandomString(int length) {
        if (length < 1) throw new IllegalArgumentException();

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {

            int rndCharAt = random.nextInt(ALPHANUMERIC_STRING.length());
            char rndChar = ALPHANUMERIC_STRING.charAt(rndCharAt);

            sb.append(rndChar);
        }

        return sb.toString();
    }


    public String getRecoverCode(int length){

        if (length < 1) throw new IllegalArgumentException();

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {

            int rndCharAt = random.nextInt(NUMERIC_ONLY_STRING.length());
            char rndChar = NUMERIC_ONLY_STRING.charAt(rndCharAt);

            sb.append(rndChar);
        }

        return sb.toString();
    }
    
}