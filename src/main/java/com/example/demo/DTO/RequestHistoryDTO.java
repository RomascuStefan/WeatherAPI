package com.example.demo.DTO;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class RequestHistoryDTO {

    private String lat;
    private String lon;
    private String response;
    private String username;
}
