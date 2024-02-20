package com.weather.controller;

import com.weather.model.SensorMetricType;
import com.weather.service.StatisticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController("/statistics")
public class StatisticsController {
    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/average-by-id")
    public double getAverageMetricBySensorId(String sensorId, LocalDateTime start, LocalDateTime end) {
        return statisticsService.getAverageMetricBySensorIdInTimeRange(sensorId, start, end);
    }

    @GetMapping("/average-by-type")
    public double getAverageMetricBySensorType(SensorMetricType sensorType, LocalDateTime start, LocalDateTime end) {
        return statisticsService.getAverageMetricBySensorTypeInTimeRange(sensorType, start, end);
    }
}
