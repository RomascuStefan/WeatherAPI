package com.example.demo.Controller;

import com.example.demo.DTO.CoordsDTO;
import com.example.demo.DTO.WeatherResponseDTO;
import com.example.demo.Model.User;
import com.example.demo.Service.WeatherService;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/weather")
@CrossOrigin(origins = "http://localhost:4200", exposedHeaders = "Authorization")
public class WeatherController {

    private final WeatherService weatherService;
    private final UserService userService;

    @Autowired
    public WeatherController(WeatherService weatherService, UserService userService) {
        this.weatherService = weatherService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<WeatherResponseDTO> getWeatherStatus(
            @RequestAttribute("username") String username, @RequestParam float lat, @RequestParam float lon,
            @RequestParam(required = false, defaultValue = "false") Boolean q, @RequestParam(required = false, defaultValue = "false") Boolean aqi) {

        User user = userService.getUserByUsername(username);
        String apiKey = user.getUserprofile().getWeatherApiKey();

        WeatherResponseDTO response = weatherService.getWeatherStatus(apiKey, lat, lon, q, aqi);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Map<CoordsDTO, WeatherResponseDTO>> getWeatherStatusList(
            @RequestAttribute("username") String username, @RequestParam(required = false, defaultValue = "false") boolean aqi,
            @RequestBody List<CoordsDTO> coords) {

        User user = userService.getUserByUsername(username);
        String apiKey = user.getUserprofile().getWeatherApiKey();

        Map<CoordsDTO, WeatherResponseDTO> map = weatherService.getListWeatherStatus(apiKey, coords, aqi);

        return ResponseEntity.ok(map);

    }
}
