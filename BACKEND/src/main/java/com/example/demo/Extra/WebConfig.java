package com.example.demo.Extra;

import com.example.demo.Logs.LoggingInterceptor;
import com.example.demo.Security.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final JwtInterceptor jwtInterceptor;
    private final LoggingInterceptor loggingInterceptor;

    @Autowired
    public WebConfig(JwtInterceptor jwtInterceptor, LoggingInterceptor loggingInterceptor) {
        this.jwtInterceptor = jwtInterceptor;
        this.loggingInterceptor = loggingInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/api/**");

        registry.addInterceptor(loggingInterceptor)
                .addPathPatterns("/api/**");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*");
    }
}
