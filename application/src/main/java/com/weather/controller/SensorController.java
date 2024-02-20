package com.weather.controller;

import com.weather.model.RegisterSensorCommand;
import com.weather.model.Sensor;
import com.weather.service.SensorService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
public class SensorController {

    private final SensorService sensorService;

    public SensorController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @PostMapping("/register-sensor")
    public ResponseEntity<?> registerSensor(@RequestBody RegisterSensorCommand sensor) {
        sensorService.registerSensor(sensor);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/sensor")
    public ResponseEntity<?> getSensor(String id) {
        Optional<Sensor> sensor = sensorService.getSensor(id);
        if (sensor.isPresent()) {
            return ResponseEntity.ok(sensor.get());
        } else {
            return ResponseEntity
                .status(NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body("{\"error\": \"Sensor not found\"}");
        }
    }
}
