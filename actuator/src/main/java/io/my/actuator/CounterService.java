package io.my.actuator;

import io.micrometer.core.instrument.Metrics;
import org.springframework.stereotype.Service;

@Service
public class CounterService {
    public void increment(String metricName) {
        Metrics.counter(metricName).increment();
//        Metrics.gauge(metricName, 0);
//        Metrics.summary(metricName, metricName);
    }
}
