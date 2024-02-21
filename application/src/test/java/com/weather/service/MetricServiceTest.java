package com.weather.service;

import com.weather.error.MetricValidationError;
import com.weather.model.Metric;
import com.weather.model.RegisterSensorMetricCommand;
import com.weather.model.Sensor;
import com.weather.model.SensorMetricType;
import com.weather.repository.MetricRepository;
import com.weather.validator.MetricValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MetricServiceTest {

    public static final String SENSOR_ID = "SENSOR_ID";
    @Mock
    private MetricRepository metricRepository;
    @Mock
    private MetricValidator metricValidator;
    @InjectMocks
    private MetricService metricService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerMetric_successFlow() {
        RegisterSensorMetricCommand command = new RegisterSensorMetricCommand(SENSOR_ID, 1.0);
        doNothing().when(metricValidator).validateMetric(command);
        doNothing().when(metricRepository).registerSensorMetric(command);

        metricService.registerMetric(command);

        verify(metricValidator, times(1)).validateMetric(command);
        verify(metricRepository, times(1)).registerSensorMetric(command);
    }

    @Test
    void registerMetric_shouldNotRegisterMetric_whenValidationFailed() {
        RegisterSensorMetricCommand command = new RegisterSensorMetricCommand(null, -1.0);
        doThrow(IllegalArgumentException.class).when(metricValidator).validateMetric(command);


        assertThrows(MetricValidationError.class, () -> metricService.registerMetric(command));
        verify(metricRepository, times(0)).registerSensorMetric(command);
    }

    @Test
    void getSensorMetricInTimeRange_successFlow() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = LocalDateTime.now().plusHours(1);
        when(metricRepository.getSensorMetricInTimeRange(SENSOR_ID, start, end)).thenReturn(Collections.emptyList());

        List<Metric> result = metricService.getSensorMetricInTimeRange(SENSOR_ID, start, end);

        verify(metricRepository, times(1)).getSensorMetricInTimeRange(SENSOR_ID, start, end);
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    void getSensorMetricByTypeInTimeRange_successFlow() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = LocalDateTime.now().plusHours(1);
        SensorMetricType sensorType = SensorMetricType.TEMPERATURE;
        when(metricRepository.getSensorMetricByTypeInTimeRange(sensorType, start, end)).thenReturn(Collections.emptyList());

        List<Metric> result = metricService.getSensorMetricByTypeInTimeRange(sensorType, start, end);

        verify(metricRepository, times(1)).getSensorMetricByTypeInTimeRange(sensorType, start, end);
        assertEquals(Collections.emptyList(), result);
    }
}