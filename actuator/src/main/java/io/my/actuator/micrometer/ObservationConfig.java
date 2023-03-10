package io.my.actuator.micrometer;

import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.aop.ObservedAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ObservationConfig {

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
