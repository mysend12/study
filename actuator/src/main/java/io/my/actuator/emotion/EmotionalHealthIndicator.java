package io.my.actuator.emotion;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Component
public class EmotionalHealthIndicator extends AbstractHealthIndicator {
    private EmotionalEvent event;
    private Date when;

    public void onHealthEvent(EmotionalEvent event) {
        this.event = event;
        this.when = new Date();
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        Optional.ofNullable(this.event)
                .ifPresent(event -> {
                    Class<? extends EmotionalEvent> eventClass = this.event.getClass();
                    Health.Builder healthBuilder = eventClass.isAssignableFrom(SadEvent.class) ?
                            builder.down() : builder.up();
                    String eventTimeAsString = this.when.toInstant().toString();

                    healthBuilder.withDetail("class", eventClass).withDetails(
                            Map.of("when", eventTimeAsString)
                    );
                });
    }
}
