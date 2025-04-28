package com.example.demo.Controller;

import com.example.demo.Model.User;
import com.example.demo.Service.RequestHistoryService;
import com.example.demo.Service.WeatherService;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private final WeatherService weatherService;
    private final RequestHistoryService requestHistoryService;
    private final UserService userService;

    @Autowired
    public WeatherController(WeatherService weatherService, RequestHistoryService requestHistoryService, UserService userService) {
        this.weatherService = weatherService;
        this.requestHistoryService = requestHistoryService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Map<Object, Object>> getWeatherStatus(
            @RequestAttribute("username") String username, @RequestParam int lat, @RequestParam int lon,
            @RequestParam(required = false) Boolean q, @RequestParam(required = false) Boolean aqi) {

        User user = userService.getUserByUsername(username);

        String apiKey = user.getUserprofile().getWeatherApiKey();
        Map<Object, Object> response = weatherService.getWeatherStatus(apiKey, lat, lon, q, aqi);

        requestHistoryService.saveRequestHistory(user, lat, lon, q, aqi, response);
        return ResponseEntity.ok(response);
    }
}
