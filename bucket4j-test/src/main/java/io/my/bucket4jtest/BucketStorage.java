package io.my.bucket4jtest;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class BucketStorage {
    private Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    public boolean checkBucket(String clientIp) {
        Bucket bucket = buckets.getOrDefault(clientIp, createBucket());
        if (! buckets.containsKey(clientIp)) {
            buckets.put(clientIp, bucket);
        }

        log.info("avilable token: {}", bucket.getAvailableTokens());

        return bucket.tryConsume(1);
    }

    private Bucket createBucket() {
        long capacity = 5;
        Refill refill = Refill.greedy(1, Duration.ofSeconds(1));
        Bandwidth limit = Bandwidth.classic(capacity, refill);
        return Bucket.builder().addLimit(limit).build();
    }
}
