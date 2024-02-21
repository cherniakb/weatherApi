package com.weather.validator;

import com.weather.model.RegisterSensorMetricCommand;
import com.weather.model.Sensor;
import com.weather.model.SensorMetricType;
import com.weather.service.SensorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class MetricValidatorTest {
    public static final String SENSOR_ID = "SENSOR_ID";
    @Mock
    private SensorService service;
    @InjectMocks
    private MetricValidator metricValidator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void validateMetric_shouldThrowException_whenSensorIdIsNull() {
        RegisterSensorMetricCommand command = new RegisterSensorMetricCommand(null, 1.0);

        IllegalArgumentException exception =
            assertThrows(IllegalArgumentException.class, () -> metricValidator.validateMetric(command));
        assertEquals("Sensor ID cannot be empty", exception.getMessage());
    }

    @Test
    void validateMetric_shouldThrowException_whenSensorIdIsEmpty() {
        RegisterSensorMetricCommand command = new RegisterSensorMetricCommand("", 1.0);

        IllegalArgumentException exception =
            assertThrows(IllegalArgumentException.class, () -> metricValidator.validateMetric(command));
        assertEquals("Sensor ID cannot be empty", exception.getMessage());
    }

    @Test
    void validateMetric_shouldThrowException_whenSensorWithIdDontExist() {
        RegisterSensorMetricCommand command = new RegisterSensorMetricCommand(SENSOR_ID, 1.0);
        when(service.getSensor(SENSOR_ID)).thenReturn(Optional.empty());

        IllegalArgumentException exception =
            assertThrows(IllegalArgumentException.class, () -> metricValidator.validateMetric(command));
        assertEquals("Sensor not found", exception.getMessage());
    }

    @Test
    void validateMetric_shouldThrowException_whenValueLessThenZero() {
        RegisterSensorMetricCommand command = new RegisterSensorMetricCommand(SENSOR_ID, -1.0);
        when(service.getSensor("SENSOR_ID")).thenReturn(Optional.of(new Sensor(
                SENSOR_ID,
                "SENSOR_NAME",
                SensorMetricType.TEMPERATURE,
                "Test-Location",
                "2021-01-01T00:00:00")
            ));

        IllegalArgumentException exception =
            assertThrows(IllegalArgumentException.class, () -> metricValidator.validateMetric(command));
        assertEquals("Value cannot be negative", exception.getMessage());
    }
}