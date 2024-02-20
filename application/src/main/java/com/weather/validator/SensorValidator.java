package com.weather.validator;

import com.weather.model.RegisterSensorCommand;
import org.springframework.stereotype.Component;

@Component
public class SensorValidator {

    public void validateSensor(RegisterSensorCommand registerSensorCommand) {
        String name = registerSensorCommand.name();
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Sensor name cannot be empty");
        }

        if (registerSensorCommand.type() == null) {
            throw new IllegalArgumentException("Sensor type cannot be empty");
        }
    }
}
