package com.example.demo.DTO.Mapper;

import com.example.demo.DTO.WeatherResponseDTO;

import java.util.Map;

public class WeatherResponseMapper {

    public static WeatherResponseDTO toDTO(Map<String, Object> response, boolean includeAqi) {

        Map<String, Object> location = (Map<String, Object>) response.get("location");
        Map<String, Object> current = (Map<String, Object>) response.get("current");

        String name = (String) location.get("name");
        String country = (String) location.get("country");
        String localtime = (String) location.get("localtime");
        double tempC = ((Number) current.get("temp_c")).doubleValue();
        double windKph = ((Number) current.get("wind_kph")).doubleValue();

        Map<String, Object> airQuality = null;
        if (includeAqi) {
            airQuality = (Map<String,Object>) current.get("air_quality");
        }

        return new WeatherResponseDTO(name, country, localtime, tempC, windKph, airQuality);
    }
}
