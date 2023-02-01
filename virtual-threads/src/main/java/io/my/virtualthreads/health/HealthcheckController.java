package io.my.virtualthreads.health;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class HealthcheckController {

    @GetMapping("/health")
    public String healthcheck() {
        for (int index=0; index<1000; index++) {
            log.info("index: {}", index);
        }
        return "service is health";
    }
}
