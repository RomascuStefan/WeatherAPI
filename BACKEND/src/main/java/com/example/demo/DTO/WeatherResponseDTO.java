package com.example.demo.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WeatherResponseDTO {
    private String name;
    private String country;
    private String localtime;
    private double tempC;
    private double windKph;
    private Map<String,Object> airQuality;
}
