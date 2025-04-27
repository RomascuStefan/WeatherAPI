package com.example.demo.Advice.Exception;

public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(String resource, String input) {
        super(resource + " already exists with: `" + input + "`");
    }
}
