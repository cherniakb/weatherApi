package com.weather.controller;

import com.weather.error.MetricValidationError;
import com.weather.service.MetricService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MetricController.class)
class MetricControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MetricService metricService;

    @Test
    void registerMetric_successCase() throws Exception {
        doNothing().when(metricService).registerMetric(any());
        this.mockMvc
            .perform(post("/register-metric").contentType(MediaType.APPLICATION_JSON).content("{\"sensorId\":\"SENSOR_ID\",\"metric\":1.0}"))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    void registerMetric_shouldReturnBadRequest_whenInvalidMetric() throws Exception {
        doThrow(MetricValidationError.class).when(metricService).registerMetric(any());

        this.mockMvc
            .perform(post("/register-metric").contentType(MediaType.APPLICATION_JSON).content("{\"sensorId\":\"SENSOR_ID\",\"metric\":-1.0}"))
            .andDo(print())
            .andExpect(status().isBadRequest());
    }
}