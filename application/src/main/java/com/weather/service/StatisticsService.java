package com.weather.service;

import com.weather.model.Metric;
import com.weather.model.SensorMetricType;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDateTime;

@Service
public class StatisticsService {
    private final MetricService metricService;

    public StatisticsService(MetricService metricService) {
        this.metricService = metricService;
    }


    public double getAverageMetricBySensorIdInTimeRange(String sensorId, LocalDateTime start, LocalDateTime end) {
        return metricService.getSensorMetricInTimeRange(sensorId, start, end)
                .stream()
                .mapToDouble(Metric::value)
                .average()
                .orElse(0.0);
    }

    public double getAverageMetricBySensorTypeInTimeRange(SensorMetricType sensorType, LocalDateTime start, LocalDateTime end) {
        return metricService.getSensorMetricByTypeInTimeRange(sensorType, start, end)
                .stream()
                .mapToDouble(Metric::value)
                .average()
                .orElse(0.0);
    }
}
