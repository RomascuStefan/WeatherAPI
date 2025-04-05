package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class WeatherService {

    private final RestTemplate restTemplate;


    @Autowired
    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Map<Object, Object> getWeatherStatus(String apiKey, int lat, int lon, Boolean q, Boolean aqi) {
        String url = "http://api.weatherapi.com/v1/current.json?key=" + apiKey + "&q=" + lat + "," + lon;
        if (q != null) {
            url += "&include_q=" + (q ? "yes" : "no");
        }
        if (aqi != null) {
            url += "&aqi=" + (aqi ? "yes" : "no");
        }
        return restTemplate.getForObject(url, Map.class);
    }





}
