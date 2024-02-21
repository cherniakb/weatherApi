package com.weather.controller;

import com.weather.error.SensorValidationError;
import com.weather.model.Sensor;
import com.weather.model.SensorMetricType;
import com.weather.service.SensorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SensorController.class)
class SensorControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SensorService sensorService;

    @Test
    void registerSensor_successFlow() throws Exception {
        doNothing().when(sensorService).registerSensor(any());
        this.mockMvc
            .perform(post("/register-sensor").contentType(MediaType.APPLICATION_JSON).content("{\"name\":\"SENSOR_NAME\",\"metricType\":\"HUMIDITY\",\"location\":\"LOCATION\"}"))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    void registerSensor_shouldReturnBadRequest_whenInvalidSensor() throws Exception {
        doThrow(SensorValidationError.class).when(sensorService).registerSensor(any());
        this.mockMvc
            .perform(post("/register-sensor").contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"\",\"metricType\":\"HUMIDITY\",\"location\":\"LOCATION\"}"))
            .andDo(print())
            .andExpect(status().isBadRequest());
    }

    @Test
    void getSensor_successCase() throws Exception {
        when(sensorService.getSensor("SENSOR_ID"))
            .thenReturn(Optional.of(new Sensor("SENSOR_ID", "SENSOR_NAME", SensorMetricType.TEMPERATURE, "LOCATION", "CREATED_AT")));

        String expectedResponse = "\"id\":\"SENSOR_ID\",\"name\":\"SENSOR_NAME\",\"type\":\"TEMPERATURE\",\"location\":\"LOCATION\",\"createdAt\":\"CREATED_AT\"";
        this.mockMvc
            .perform(get("/sensor").contentType(MediaType.APPLICATION_JSON).param("id", "SENSOR_ID"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString(expectedResponse)));
    }

    @Test
    void getSensor_shouldReturnNotFound_whenNoSensor() throws Exception {
        when(sensorService.getSensor("SENSOR_ID")).thenReturn(Optional.empty());

        this.mockMvc
            .perform(get("/sensor").contentType(MediaType.APPLICATION_JSON).param("id", "SENSOR_ID"))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(content().string(containsString("{\"error\": \"Sensor not found\"}")));
    }
}