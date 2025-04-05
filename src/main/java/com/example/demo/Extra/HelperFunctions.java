package com.example.demo.Extra;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class HelperFunctions {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static String getHashString(String input) {
        return encoder.encode(input);
    }
}
