package io.my.actuator.audit;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.listener.AuditApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collections;

@RestController
@RequiredArgsConstructor
public class GreetingRestController {
    public static final String GREETING_EVENT = "greeting_event".toUpperCase();

    private final ApplicationEventPublisher applicationEventPublisher;

    @GetMapping("/hi")
    public String greet(Principal p) {
        String message = "hello, " + p.getName() + "!";
        AuditEvent auditEvent = new AuditEvent(p.getName(), GREETING_EVENT, Collections.singletonMap("greeting", message));
        this.applicationEventPublisher.publishEvent(new AuditApplicationEvent(auditEvent));
        return message;
    }
}
