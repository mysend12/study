package org.example.delayed;

import java.util.concurrent.locks.LockSupport;

@Deprecated
public final class LockSupportDelayed implements TestDelayed {
    @Override
    public void print(int milliseconds, String message) {
        Thread thread = new Thread(() -> {
            LockSupport.parkNanos(100000L * milliseconds);
            LockSupport.unpark(Thread.currentThread());
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
