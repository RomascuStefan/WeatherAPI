package com.example.demo.Aspect;


import com.example.demo.DTO.WeatherResponseDTO;
import com.example.demo.Model.RequestHistory;
import com.example.demo.Model.User;
import com.example.demo.Service.RequestHistoryService;
import com.example.demo.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@RequiredArgsConstructor
public class WeatherHistoryAspect {
    private final RequestHistoryService requestHistoryService;
    private final UserService userService;

    @AfterReturning(pointcut = "execution(* com.example.demo.Service.WeatherService.getWeatherStatus(..))", returning = "result")
    public void saveHistory(JoinPoint jp, Object result) {
        Object[] args = jp.getArgs();

        int lat = (Integer) args[1];
        int lon = (Integer) args[2];
        Boolean q = (Boolean) args[3];
        Boolean aqi = (Boolean) args[4];

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attr.getRequest();
        String username = (String) request.getAttribute("username");

        WeatherResponseDTO weatherResponse = (WeatherResponseDTO) result;

        User user = userService.getUserByUsername(username);
        requestHistoryService.saveRequestHistory(user, lat, lon, q, aqi, weatherResponse);

    }


}
