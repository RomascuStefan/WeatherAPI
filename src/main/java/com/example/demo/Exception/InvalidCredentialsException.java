package com.example.demo.Exception;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super("Username sau parola invalide");
    }
}