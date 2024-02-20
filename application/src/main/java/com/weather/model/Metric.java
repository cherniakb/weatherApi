package com.weather.model;

import java.time.LocalDateTime;

public record Metric(String metricId, String sensorId, double value, LocalDateTime createdAt) {
}
