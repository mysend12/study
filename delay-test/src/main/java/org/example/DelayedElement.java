package org.example;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public final class DelayedElement implements Delayed {
    private final String message;
    private final long endTime;

    public DelayedElement(String message, long endTime, TimeUnit unit) {
        this.message = message;
        this.endTime = System.currentTimeMillis() + unit.toMillis(endTime);
    }

    @Override
    public long getDelay(TimeUnit unit) {
        long remainingTime = endTime - System.currentTimeMillis();
        return unit.convert(remainingTime, TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        long diff = this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS);
        return Long.compare(diff, 0);
    }

    public String getMessage() {
        return message;
    }
}
