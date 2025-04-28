package com.example.demo.Service;

import com.example.demo.Advice.Exception.TooManyLocationsException;
import com.example.demo.DTO.CoordsDTO;
import com.example.demo.DTO.Mapper.WeatherResponseMapper;
import com.example.demo.DTO.WeatherResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Service
public class WeatherService {
    private final RestTemplate restTemplate;

    @Autowired
    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public WeatherResponseDTO getWeatherStatus(String apiKey, int lat, int lon, Boolean q, Boolean aqi) {
        String url = "http://api.weatherapi.com/v1/current.json?key=" + apiKey + "&q=" + lat + "," + lon;
        url += "&include_q=" + (q ? "yes" : "no");
        url += "&aqi=" + (aqi ? "yes" : "no");

        return WeatherResponseMapper.toDTO(restTemplate.getForObject(url, Map.class), aqi);
    }

    public Map<CoordsDTO, WeatherResponseDTO> getListWeatherStatus(String apiKey, List<CoordsDTO> coords, boolean aqi) {
        if (coords.size() > 3)
            throw new TooManyLocationsException(coords.size());

        ConcurrentMap<CoordsDTO, WeatherResponseDTO> result = new ConcurrentHashMap<>();

        try (ExecutorService exec = Executors.newVirtualThreadPerTaskExecutor()) {
            List<Callable<Void>> tasks = new ArrayList<>();

            for (CoordsDTO c : coords) {
                tasks.add(() -> {
                    WeatherResponseDTO dto =
                            getWeatherStatus(apiKey, c.getLat(), c.getLon(), false, aqi);
                    result.put(c, dto);
                    return null;
                });
            }

            exec.invokeAll(tasks);

        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("err thread", ex);
        }

        return result;
    }

}
