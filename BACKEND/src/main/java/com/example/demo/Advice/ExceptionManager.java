package com.example.demo.Advice;

import com.example.demo.Advice.Exception.*;
import io.jsonwebtoken.JwtException;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;

@RestControllerAdvice
public class ExceptionManager {

    private Map<String, String> body(String msg) {
        return Collections.singletonMap("error", msg);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    @ApiResponse(responseCode = "401", description = "Invalid credentials")
    public ResponseEntity<Map<String, String>> invalidCred(InvalidCredentialsException ex, HttpServletRequest request) {

        request.setAttribute("errMsg", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body(ex.getMessage()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ApiResponse(responseCode = "404", description = "Resource not found")
    public ResponseEntity<Map<String, String>> notFound(ResourceNotFoundException ex, HttpServletRequest request) {

        request.setAttribute("errMsg", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body(ex.getMessage()));
    }

    @ExceptionHandler(DuplicateResourceException.class)
    @ApiResponse(responseCode = "409", description = "Duplicate resource")
    public ResponseEntity<Map<String, String>> duplicate(DuplicateResourceException ex, HttpServletRequest request) {

        request.setAttribute("errMsg", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body(ex.getMessage()));
    }

    @ExceptionHandler(JwtException.class)
    @ApiResponse(responseCode = "401", description = "JWT invalid sau expirat")
    public ResponseEntity<Map<String, String>> badJwt(JwtException ex, HttpServletRequest request) {

        request.setAttribute("errMsg", "JWT invalid sau expirat");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body("JWT invalid sau expirat"));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ApiResponse(responseCode = "400", description = "Invalid request payload")
    public ResponseEntity<Map<String, Object>> validation(MethodArgumentNotValidException ex, HttpServletRequest request) {

        Map<String, String> details = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(e -> details.put(e.getField(), e.getDefaultMessage()));

        request.setAttribute("errMsg", "Invalid request payload");

        Map<String, Object> resp = new HashMap<>();
        resp.put("error", "Invalid request payload");
        resp.put("details", details);

        return ResponseEntity.badRequest().body(resp);
    }

    @ExceptionHandler(Exception.class)
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<Map<String, String>> generic(Exception ex, HttpServletRequest request) {

        request.setAttribute("errMsg", "Internal server error");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body("Internal server error"));
    }

    @ExceptionHandler(TooManyLocationsException.class)
    @ApiResponse(responseCode = "400", description = "Prea multe locatii")
    public ResponseEntity<Map<String, String>> tooMany(TooManyLocationsException ex, HttpServletRequest request) {

        request.setAttribute("errMsg", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body(ex.getMessage()));
    }
}
