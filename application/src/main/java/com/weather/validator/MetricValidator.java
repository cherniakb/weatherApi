package com.weather.validator;

import com.weather.model.RegisterSensorMetricCommand;
import com.weather.service.SensorService;
import org.springframework.stereotype.Component;

@Component
public class MetricValidator {

    private final SensorService sensorService;

    public MetricValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    public void validateMetric(RegisterSensorMetricCommand command) {
        String sensorId = command.sensorId();
        if (sensorId == null || sensorId.isEmpty()) {
            throw new IllegalArgumentException("Sensor ID cannot be empty");
        }

        if (sensorService.getSensor(sensorId).isEmpty()) {
            throw new IllegalArgumentException("Sensor not found");
        }

        double value = command.value();
        if (value < 0) {
            throw new IllegalArgumentException("Value cannot be negative");
        }
    }

}
