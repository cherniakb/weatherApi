package com.weather.repository;

import com.weather.model.Metric;
import com.weather.model.RegisterSensorMetricCommand;
import com.weather.model.SensorMetricType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class MetricRepository {

    private final JdbcTemplate jdbcTemplate;
    private final Logger log = LoggerFactory.getLogger(MetricRepository.class);

    public MetricRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void registerSensorMetric(RegisterSensorMetricCommand registerSensorMetricCommand) {
        String updateSql = "INSERT INTO sensor_metrics (sensor_id, sensor_value, created_at) VALUES (?, ?, ?)";
        try {
            jdbcTemplate.update(
                updateSql,
                registerSensorMetricCommand.sensorId(),
                registerSensorMetricCommand.value(),
                LocalDateTime.now()
            );
        } catch (DataAccessException e) {
            log.error("Error while inserting sensor metric", e);
        }
    }

    public List<Metric> getSensorMetricInTimeRange(String sensorId, LocalDateTime start, LocalDateTime end) {
        String query = "SELECT * FROM sensor_metrics WHERE sensor_id = ? AND created_at >= ? AND created_at <= ?";
        try {
            return jdbcTemplate.query(
                query,
                getMetricsObjectMapper(),
                sensorId,
                start,
                end
            );
        } catch (DataAccessException e) {
            log.error("Error while getting sensor metric", e);
            return List.of();
        }
    }

    public List<Metric> getSensorMetricByTypeInTimeRange(SensorMetricType type, LocalDateTime start, LocalDateTime end) {
        String query = "SELECT * FROM sensor_metrics LEFT JOIN sensors ON sensor_metrics.sensor_id = sensors.id " +
            "WHERE sensors.type = ? AND sensor_metrics.created_at >= ? AND sensor_metrics.created_at <= ?";
        try {
            return jdbcTemplate.query(
                query,
                getMetricsObjectMapper(),
                type.name(),
                start,
                end
            );
        } catch (DataAccessException e) {
            log.error("Error while getting sensor metric", e);
            return List.of();
        }
    }

    private RowMapper<Metric> getMetricsObjectMapper() {
        return (rs, rowNum) -> new Metric(
            rs.getString("metric_id"),
            rs.getString("sensor_id"),
            rs.getDouble("sensor_value"),
            rs.getTimestamp("created_at").toLocalDateTime()
        );
    }
}
