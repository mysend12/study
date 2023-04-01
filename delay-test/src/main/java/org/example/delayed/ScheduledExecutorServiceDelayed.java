package org.example.delayed;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public final class ScheduledExecutorServiceDelayed implements TestDelayed {
    @Override
    public void print(int milliseconds, String message) {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.schedule(() -> System.out.println(message), milliseconds, TimeUnit.MILLISECONDS);
    }
}
