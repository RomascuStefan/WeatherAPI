package com.example.demo.Advice.Exception;

public class TooManyLocationsException extends RuntimeException {
    public TooManyLocationsException(int size) {
        super("Maxim 3 locatii. Am primit: " + size);
    }
}
