package com.weather.error;

public class MetricValidationError extends RuntimeException {
    public MetricValidationError(String message) {
        super(message);
    }
}
