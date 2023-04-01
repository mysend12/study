package org.example.delayed;

public final class ThreadSleepDelayed implements TestDelayed {
    public void print(int milliseconds, String message) {
        try {
            Thread.sleep(milliseconds);
            System.out.println(message);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
