package org.example.delayed;

import org.example.DelayedElement;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

public final class DelayedQueueDelayed implements TestDelayed {
    @Override
    public void print(int milliseconds, String message) {
        BlockingQueue<DelayedElement> queue = new DelayQueue<>();

        try {
            queue.put(new DelayedElement(message, milliseconds, TimeUnit.MILLISECONDS));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        DelayedElement element = null;
        try {
            element = queue.take();
            System.out.println(element.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
