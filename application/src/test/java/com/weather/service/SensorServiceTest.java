package com.weather.service;

import com.weather.error.SensorValidationError;
import com.weather.model.RegisterSensorCommand;
import com.weather.model.Sensor;
import com.weather.model.SensorMetricType;
import com.weather.repository.SensorRepository;
import com.weather.validator.SensorValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SensorServiceTest {
    private static final String SENSOR_ID = "SENSOR_ID";
    private static final String TEST_LOCATION = "Test-Location";
    private static final String TEST_NAME = "NAME";
    @Mock
    private SensorRepository sensorRepository;
    @Mock
    private SensorValidator sensorValidator;

    @InjectMocks
    private SensorService sensorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerSensor_successCase() {
        RegisterSensorCommand testCommand = new RegisterSensorCommand(TEST_NAME, SensorMetricType.HUMIDITY, TEST_LOCATION);
        doNothing().when(sensorValidator).validateSensor(testCommand);
        doNothing().when(sensorRepository).registerSensor(testCommand);

        sensorService.registerSensor(testCommand);

        verify(sensorValidator, times(1)).validateSensor(testCommand);
        verify(sensorRepository, times(1)).registerSensor(testCommand);
    }

    @Test
    void registerSensor_shouldNotRegisterSensor_whenValidationFailed() {
        RegisterSensorCommand testCommand = new RegisterSensorCommand(TEST_NAME, SensorMetricType.HUMIDITY, TEST_LOCATION);
        doThrow(IllegalArgumentException.class).when(sensorValidator).validateSensor(testCommand);

        assertThrows(SensorValidationError.class, () -> sensorService.registerSensor(testCommand));
        verify(sensorRepository, times(0)).registerSensor(testCommand);
    }

    @Test
    void getSensor_shouldReturnSensor_whenSensorExists() {
        String createdAt = "2021-01-01T00:00:00";
        when(sensorRepository.getSensor(SENSOR_ID))
            .thenReturn(Optional.of(new Sensor(SENSOR_ID, TEST_NAME, SensorMetricType.HUMIDITY, TEST_LOCATION, createdAt)));

        var result = sensorService.getSensor(SENSOR_ID);

        assertTrue(result.isPresent());
        assertEquals(SENSOR_ID, result.get().id());
        assertEquals(TEST_NAME, result.get().name());
        assertEquals(SensorMetricType.HUMIDITY, result.get().type());
        assertEquals(TEST_LOCATION, result.get().location());
        assertEquals(createdAt, result.get().createdAt());
    }
}