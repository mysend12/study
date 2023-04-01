package org.example.delayed;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Deprecated
public final class CountDownLatchDelayed implements TestDelayed {
    @Override
    public void print(int milliseconds, String message) {
        CountDownLatch latch = new CountDownLatch(1);
        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(1000);
                System.out.println(message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
        });

        t1.start();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
