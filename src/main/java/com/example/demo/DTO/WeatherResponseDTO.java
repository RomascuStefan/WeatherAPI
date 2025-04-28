package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class WeatherResponseDTO {
    private String name;
    private String country;
    private String localtime;
    private double tempC;
    private double windKph;
}
