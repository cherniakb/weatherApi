package com.weather.service;

import com.weather.error.MetricValidationError;
import com.weather.model.Metric;
import com.weather.model.RegisterSensorMetricCommand;
import com.weather.model.SensorMetricType;
import com.weather.repository.MetricRepository;
import com.weather.validator.MetricValidator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MetricService {
    private final MetricRepository metricRepository;
    private final MetricValidator metricValidator;

    public MetricService(MetricRepository metricRepository, MetricValidator metricValidator) {
        this.metricRepository = metricRepository;
        this.metricValidator = metricValidator;
    }

    public void registerMetric(RegisterSensorMetricCommand registerSensorMetricCommand) {
        try {
            metricValidator.validateMetric(registerSensorMetricCommand);
        } catch (IllegalArgumentException e) {
            throw new MetricValidationError(e.getMessage());
        }

        metricRepository.registerSensorMetric(registerSensorMetricCommand);
    }

    public List<Metric> getSensorMetricInTimeRange(String sensorId, LocalDateTime start, LocalDateTime end) {
        return metricRepository.getSensorMetricInTimeRange(sensorId, start, end);
    }

    public List<Metric> getSensorMetricByTypeInTimeRange(SensorMetricType sensorType, LocalDateTime start, LocalDateTime end) {
        return metricRepository.getSensorMetricByTypeInTimeRange(sensorType, start, end);
    }

}
