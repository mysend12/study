package org.example;

import org.example.delayed.*;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {
        TestDelayed delayed = new CompletableFutureDelayed();
        delayed.print(1000, "hello CompletableFuture");

        delayed = new TimerDelayed();
        delayed.print(1000, "hello Timer");

        delayed = new CountDownLatchDelayed();
        delayed.print(1000, "hello CountDownLatch");

        delayed = new LockSupportDelayed();
        delayed.print(1000, "hello LockSupport");

        delayed = new DelayedQueueDelayed();
        delayed.print(1000, "hello DelayedQueueDelayed");

        delayed = new ThreadSleepDelayed();
        delayed.print(1000, "hello ThreadSleep");

        delayed = new ConditionTestDelayed();
        delayed.print(1000, "hello ConditionTest");

        // 비동기라서 다음 작업이 바로 실행됨
        delayed = new ScheduledExecutorServiceDelayed();
        delayed.print(1000, "hello ScheduledExecutorService");
    }
}
