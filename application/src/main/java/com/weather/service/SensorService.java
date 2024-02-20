package com.weather.service;

import com.weather.model.RegisterSensorCommand;
import com.weather.model.Sensor;
import com.weather.repository.SensorRepository;
import com.weather.validator.SensorValidator;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SensorService {
    private final SensorRepository sensorRepository;
    private final SensorValidator sensorValidator;

    public SensorService(SensorRepository sensorRepository, SensorValidator sensorValidator) {
        this.sensorRepository = sensorRepository;
        this.sensorValidator = sensorValidator;
    }

    public void registerSensor(RegisterSensorCommand sensorCommand) {
        sensorValidator.validateSensor(sensorCommand);
        sensorRepository.registerSensor(sensorCommand);
    }

    public Optional<Sensor> getSensor(String id) {
        return sensorRepository.getSensor(id);
    }
}
