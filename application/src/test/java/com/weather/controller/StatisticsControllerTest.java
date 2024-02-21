package com.weather.controller;

import com.weather.service.StatisticsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StatisticsController.class)
class StatisticsControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StatisticsService statisticsService;

    @Test
    void getAverageMetricBySensorId_successCase() throws Exception {
        when(statisticsService.getAverageMetricBySensorIdInTimeRange(any(), any(), any())).thenReturn(1.0);

        this.mockMvc
            .perform(get("/average-by-id").contentType(MediaType.APPLICATION_JSON)
                .param("sensorId", "SENSOR_ID").param("from", "2021-01-01T00:00:00Z").param("to", "2021-01-01T00:00:00Z"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string("1.0"));
    }

    @Test
    void getAverageMetricBySensorType_successCase() throws Exception {
        when(statisticsService.getAverageMetricBySensorTypeInTimeRange(any(), any(), any())).thenReturn(1.0);

        this.mockMvc
            .perform(get("/average-by-type").contentType(MediaType.APPLICATION_JSON)
                .param("sensorType", "TEMPERATURE").param("from", "2021-01-01T00:00:00Z").param("to", "2021-01-01T00:00:00Z"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string("1.0"));
    }
}