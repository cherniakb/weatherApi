package com.weather.controller;

import com.weather.model.RegisterSensorMetricCommand;
import com.weather.service.MetricService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MetricController {
    private final MetricService metricService;

    public MetricController(MetricService metricService) {
        this.metricService = metricService;
    }

    @PostMapping("/register-metric")
    public ResponseEntity<?> registerMetric(@RequestBody RegisterSensorMetricCommand registerSensorMetricCommand) {
        metricService.registerMetric(registerSensorMetricCommand);
        return ResponseEntity.ok().build();
    }
}