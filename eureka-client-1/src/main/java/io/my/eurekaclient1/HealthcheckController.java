package io.my.eurekaclient1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthcheckController {

    @Autowired
    private Environment environment;

    @GetMapping("/health")
    public String health() {
        return "port " + environment.getProperty("local.server.port") + "is health";
    }
}
