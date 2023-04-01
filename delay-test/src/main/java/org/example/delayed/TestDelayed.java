package org.example.delayed;

public sealed interface TestDelayed permits CompletableFutureDelayed, ConditionTestDelayed, CountDownLatchDelayed, DelayedQueueDelayed, LockSupportDelayed, ScheduledExecutorServiceDelayed, ThreadSleepDelayed, TimerDelayed {

    void print(int milliseconds, String message);
}
