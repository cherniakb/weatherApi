package com.weather.error;

public class SensorValidationError extends RuntimeException {
    public SensorValidationError(String message) {
        super(message);
    }
}
