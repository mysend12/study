package io.my.virtualthreadsforshorts;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthcheckController {

    @GetMapping("/health")
    public String healthcheck() {
        System.out.println(Thread.currentThread());
        System.out.println(Thread.currentThread().getThreadGroup().toString());
        System.out.println(Thread.currentThread().isVirtual());
        return "service is health";
    }
}
