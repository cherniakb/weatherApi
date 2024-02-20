package com.weather.repository;

import com.weather.model.RegisterSensorCommand;
import com.weather.model.Sensor;
import com.weather.model.SensorMetricType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class SensorRepository {

    private final JdbcTemplate jdbcTemplate;
    private final Logger log = LoggerFactory.getLogger(SensorRepository.class);

    public SensorRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void registerSensor(RegisterSensorCommand registerSensorCommand) {
        try {
            String sqlUpdate = "INSERT INTO sensors (name, type, location, created_at) VALUES (?, ?, ?, ?)";
            jdbcTemplate.update(
                sqlUpdate,
                registerSensorCommand.name(),
                registerSensorCommand.type().name(),
                registerSensorCommand.location(),
                LocalDateTime.now()
            );
        } catch (DuplicateKeyException e) {
            throw new IllegalArgumentException("Sensor with name " + registerSensorCommand.name() + " already exists");
        } catch (Exception e) {
            log.error("Error while inserting sensor", e);
        }
    }

    public Optional<Sensor> getSensor(String id) {
        try {
            String sqlQuery = "SELECT * FROM sensors WHERE id = ?";

            Sensor sensor = jdbcTemplate.queryForObject(sqlQuery, (rs, rowNum) -> new Sensor(
                rs.getString("id"),
                rs.getString("name"),
                SensorMetricType.valueOf(rs.getString("type")),
                rs.getString("location"),
                rs.getString("created_at")
            ), id);
            return Optional.ofNullable(sensor);
        } catch (Exception e) {
            log.error("Error while getting sensor", e);
            return Optional.empty();
        }
    }
}
