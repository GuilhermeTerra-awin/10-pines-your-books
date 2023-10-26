package demo;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ObjectInvalidator {
    private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    private final Clock clock;
    private boolean invalidated = false;

    public ObjectInvalidator(Clock clock) {
        this.clock = clock;

        // Schedule invalidation after 30 minutes
        Instant invalidationTime = Instant.now(clock).plus(Duration.ofMinutes(30));
        executor.schedule(() -> invalidateObject(invalidationTime), Duration.between(Instant.now(clock), invalidationTime).getSeconds(), TimeUnit.SECONDS);
    }

    public boolean isInvalidated() {
        return invalidated;
    }

    private void invalidateObject(Instant invalidationTime) {
        invalidated = Instant.now(clock).isAfter(invalidationTime);
        executor.shutdown();
    }
}