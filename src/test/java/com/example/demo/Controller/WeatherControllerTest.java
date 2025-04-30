package com.example.demo.Controller;

import com.example.demo.DTO.CoordsDTO;
import com.example.demo.DTO.WeatherResponseDTO;
import com.example.demo.Model.User;
import com.example.demo.Model.UserProfile;
import com.example.demo.Service.UserService;
import com.example.demo.Service.WeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WeatherControllerTest {

    @Mock
    private WeatherService weatherService;

    @Mock
    private UserService userService;

    @InjectMocks
    private WeatherController weatherController;

    private final User user = new User();
    private final UserProfile userProfile = new UserProfile();

    @BeforeEach
    void setUp() {
        userProfile.setWeatherApiKey("key123");
        user.setUserprofile(userProfile);
    }

    @Test
    void getWeatherStatus_shouldReturnWeatherResponseDTO() {
        when(userService.getUserByUsername("test")).thenReturn(user);

        WeatherResponseDTO weatherResponseDTO = new WeatherResponseDTO("Oras", "Tara", "2025-04-30 10:00", 20.0, 12.5, null);
        when(weatherService.getWeatherStatus("key123", 1, 2, false, false)).thenReturn(weatherResponseDTO);

        ResponseEntity<WeatherResponseDTO> response = weatherController.getWeatherStatus("test", 1, 2, false, false);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Oras", response.getBody().getName());
        assertNull(response.getBody().getAirQuality());
    }

    @Test
    void getWeatherStatusList_shouldReturnMap() {
        when(userService.getUserByUsername("test")).thenReturn(user);

        CoordsDTO c1 = new CoordsDTO(1, 2);
        CoordsDTO c2 = new CoordsDTO(3, 4);

        Map<String, Object> airQuality = new HashMap<>();
        airQuality.put("text", 20);

        WeatherResponseDTO dto1 = new WeatherResponseDTO("Oras", "Tara", "2025-04-30 10:00", 20.0, 12.5, airQuality);
        WeatherResponseDTO dto2 = new WeatherResponseDTO("Oras", "Tara", "2025-04-30 11:00", 28.0, 12.0, airQuality);

        Map<CoordsDTO, WeatherResponseDTO> mockResponse = Map.of(c1, dto1, c2, dto2);

        when(weatherService.getListWeatherStatus("key123", List.of(c1, c2), true)).thenReturn(mockResponse);

        ResponseEntity<Map<CoordsDTO, WeatherResponseDTO>> response = weatherController.getWeatherStatusList("test", true, List.of(c1, c2));

        assertEquals(200, response.getStatusCode().value());
        assertTrue(response.getBody().containsKey(c1));
        assertTrue(response.getBody().containsKey(c2));
        assertNotNull(response.getBody().get(c1).getAirQuality());
        assertNotNull(response.getBody().get(c2).getAirQuality());
    }

}

