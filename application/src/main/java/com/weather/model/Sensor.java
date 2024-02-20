package com.weather.model;

public record Sensor(String id, String name, SensorMetricType type, String location, String createdAt) {
}
