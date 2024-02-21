package com.weather.validator;

import com.weather.model.RegisterSensorCommand;
import com.weather.model.SensorMetricType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SensorValidatorTest {
    private static final String TEST_LOCATION = "Test-Location";
    private SensorValidator sensorValidator;

    @BeforeEach
    void setUp() {
        sensorValidator = new SensorValidator();
    }

    @Test
    void validateSensor_shouldThrowException_whenNameIsEmpty() {
        RegisterSensorCommand commandWithEmptyName =
            new RegisterSensorCommand("", SensorMetricType.HUMIDITY, TEST_LOCATION);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> sensorValidator.validateSensor(commandWithEmptyName)
        );

        assertEquals("Sensor name cannot be empty", exception.getMessage());
    }

    @Test
    void validateSensor_shouldThrowException_whenNameIsNull() {
        RegisterSensorCommand commandWithNullName =
            new RegisterSensorCommand(null, SensorMetricType.HUMIDITY, TEST_LOCATION);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> sensorValidator.validateSensor(commandWithNullName)
        );

        assertEquals("Sensor name cannot be empty", exception.getMessage());
    }

    @Test
    void validateSensor_shouldThrowException_whenTypeIsNull() {
        RegisterSensorCommand commandWithNullType =
            new RegisterSensorCommand("Test-Name", null, TEST_LOCATION);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> sensorValidator.validateSensor(commandWithNullType)
        );

        assertEquals("Sensor type cannot be empty", exception.getMessage());
    }
}