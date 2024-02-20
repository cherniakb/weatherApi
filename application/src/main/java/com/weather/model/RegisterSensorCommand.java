package com.weather.model;

public record RegisterSensorCommand(String name, SensorMetricType type, String location) {

}
