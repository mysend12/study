package org.example.delayed;

import java.util.Timer;
import java.util.TimerTask;

public final class TimerDelayed implements TestDelayed {
    @Override
    public void print(int milliseconds, String message) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println(message);
            }
        }, milliseconds);
    }
}
