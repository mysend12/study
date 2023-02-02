package io.my.bucket4jtest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class HealthcheckController {

    @GetMapping("/healthcheck")
    public Mono<String> healthcheck() {
        return Mono.just("service is success");
    }
}
