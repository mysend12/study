package io.my.actuator.micrometer;

import com.netflix.servo.publish.BasicMetricFilter;
import com.netflix.servo.publish.MonitorRegistryMetricPoller;
import com.netflix.servo.publish.PollRunnable;
import com.netflix.servo.publish.atlas.AtlasMetricObserver;
import com.netflix.servo.publish.atlas.BasicAtlasConfig;
import com.netflix.servo.tag.BasicTagList;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.aop.ObservedAspect;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
public class ObservationConfig {

    @Bean
    public AtlasMetricObserver atlasMetricObserver() {
        System.setProperty("servo.pollers", "1000");
        System.setProperty("servo.atlas.batchSize", "1");
        System.setProperty("servo.atlas.uri", "http://localhost:7101/api/v1/publish");
        AtlasMetricObserver observer = new AtlasMetricObserver(
                new BasicAtlasConfig(), BasicTagList.of("servo", "counter"));

        PollRunnable task = new PollRunnable(
                new MonitorRegistryMetricPoller(), new BasicMetricFilter(true), observer);

        return observer;
    }

    @Bean
    public ObservationRegistry observationRegistry() {
        return ObservationRegistry.create();
    }

    @Bean
    public ObservedAspect observedAspect() {
        return new ObservedAspect(observationRegistry());
    }

    @Bean
    public RestTemplate restTemplate() {
        ObservationRegistry observationRegistry = observationRegistry();

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setObservationRegistry(observationRegistry());
        return restTemplate;
    }


//    @Bean
//    @ConditionalOnBean(ObservationRegistry.class)
//    @ConditionalOnMissingBean(ServerHttpObservationFilter.class)
//    public ServerHttpObservationFilter observationFilter() {
//        return new ServerHttpObservationFilter(observationRegistry());
//    }

}
