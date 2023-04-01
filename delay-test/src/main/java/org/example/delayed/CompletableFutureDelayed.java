package org.example.delayed;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Deprecated
public final class CompletableFutureDelayed implements TestDelayed {
    @Override
    public void print(int milliseconds, String message) {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(900);
            } catch (InterruptedException e) { /* do nothing */ }
            return message;
        });

        try {
            String result = future.get(milliseconds, TimeUnit.MILLISECONDS);
            System.out.println(result);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }

    }
}
