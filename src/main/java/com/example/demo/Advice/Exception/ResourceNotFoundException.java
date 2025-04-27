package com.example.demo.Advice.Exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resource, String input) {
        super(resource + " not found: `" + input + "`");
    }
}
