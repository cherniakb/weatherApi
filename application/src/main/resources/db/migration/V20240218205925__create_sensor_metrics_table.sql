CREATE TABLE sensor_metrics (
    metric_id INT AUTO_INCREMENT PRIMARY KEY,
    sensor_id INT NOT NULL,
    sensor_value DECIMAL(6, 2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (sensor_id) REFERENCES sensors(id)
);