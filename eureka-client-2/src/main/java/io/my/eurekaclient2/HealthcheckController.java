package io.my.eurekaclient2;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class HealthcheckController {
    private final RestTemplate restTemplate;
    private final DiscoveryClient discoveryClient;

    @GetMapping("/health")
    public String health() {
        String serviceId = "eureka-client-1";
        this.discoveryClient.getInstances(serviceId)
                .forEach(serviceInstance -> {
                    log.info("host: {}", serviceInstance.getHost());
                    log.info("port: {}", serviceInstance.getPort());
                    log.info("instanceId: {}", serviceInstance.getInstanceId());
                });

        return restTemplate.getForObject("//eureka-client-1/health", String.class);
    }
}
