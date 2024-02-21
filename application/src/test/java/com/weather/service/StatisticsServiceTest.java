package com.weather.service;

import com.weather.model.Metric;
import com.weather.model.SensorMetricType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class StatisticsServiceTest {
    private static final String SENSOR_ID = "SENSOR_ID";
    private static final LocalDateTime START = LocalDateTime.now().minusDays(1);
    private static final LocalDateTime END = LocalDateTime.now();
    private static final String METRIC_ID = "METRIC_ID";
    @Mock
    private MetricService metricService;

    @InjectMocks
    private StatisticsService statisticsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAverageMetricBySensorIdInTimeRange_successCase() {
        when(metricService.getSensorMetricInTimeRange(SENSOR_ID, START, END)).thenReturn(getTestMetrics());

        double result = statisticsService.getAverageMetricBySensorIdInTimeRange(SENSOR_ID, START, END);

        assertEquals(20.0, result);
    }

    @Test
    void getAverageMetricBySensorIdInTimeRange_shouldReturnZeroAvr_whenNoMetricRegistered() {
        when(metricService.getSensorMetricInTimeRange(SENSOR_ID, START, END)).thenReturn(List.of());

        double result = statisticsService.getAverageMetricBySensorIdInTimeRange(SENSOR_ID, START, END);

        assertEquals(0.0, result);
    }

    @Test
    void getAverageMetricBySensorTypeInTimeRange_successCase() {
        when(metricService.getSensorMetricByTypeInTimeRange(SensorMetricType.TEMPERATURE, START, END))
            .thenReturn(getTestMetrics());

        double result = statisticsService.getAverageMetricBySensorTypeInTimeRange(SensorMetricType.TEMPERATURE, START, END);

        assertEquals(20.0, result);
    }

    @Test
    void getAverageMetricBySensorTypeInTimeRange_shouldReturnZeroAvg_whenNoMetricsRegistered() {
        when(metricService.getSensorMetricByTypeInTimeRange(null, START, END)).thenReturn(List.of());

        double result = statisticsService.getAverageMetricBySensorTypeInTimeRange(SensorMetricType.TEMPERATURE, START, END);

        assertEquals(0.0, result);
    }

    private List<Metric> getTestMetrics() {
        return List.of(
            new Metric(METRIC_ID, SENSOR_ID, 10.0, LocalDateTime.now()),
            new Metric(METRIC_ID, SENSOR_ID, 20.0, LocalDateTime.now()),
            new Metric(METRIC_ID, SENSOR_ID, 30.0, LocalDateTime.now())
        );
    }
}