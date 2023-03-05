package io.my.actuator;

import org.springframework.http.ResponseEntity;

import java.util.Optional;

public record Customer(Long id) {
    public void map(String metricName) {
    }
}
