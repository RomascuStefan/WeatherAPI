package com.example.demo.Logs;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class LoggingInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(LoggingInterceptor.class);
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) {

        MDC.put("method", req.getMethod());
        MDC.put("endpoint", req.getRequestURI());
        MDC.put("time", ZonedDateTime.now().format(FMT));

        Object userAttr = req.getAttribute("username");
        MDC.put("username", userAttr != null ? userAttr.toString() : "-");

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest req, HttpServletResponse res, Object handler, Exception ex) {
        MDC.put("status", String.valueOf(res.getStatus()));

        String msg;
        if (ex != null) {
            msg = ex.getMessage();
        } else {
            Object attr = req.getAttribute("errMsg");
            msg = attr != null ? attr.toString() : "-";
        }
        MDC.put("err", msg);

        Object old = req.getAttribute("oldEmail");
        MDC.put("oldEmail", old != null ? old.toString() : "-");

        log.info("LOG");

        MDC.clear();
    }
}