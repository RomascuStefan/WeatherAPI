package com.example.demo.Advice.Exception;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super("username or password is incorrect");
    }
}
