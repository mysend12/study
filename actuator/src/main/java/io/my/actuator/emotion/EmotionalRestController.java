package io.my.actuator.emotion;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmotionalRestController {
    private final EmotionalHealthIndicator indicator;

    @PutMapping("/event/happy")
    void eventHappy() {
        indicator.onHealthEvent(new HappyEvent());
    }
    @PutMapping("/event/sad")
    void sadHappy() {
        indicator.onHealthEvent(new SadEvent());
    }
}
