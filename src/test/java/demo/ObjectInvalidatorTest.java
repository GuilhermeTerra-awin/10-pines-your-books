package demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ObjectInvalidatorTest {

    private ObjectInvalidator objectInvalidator;
    private Clock fixedClock;

    @BeforeEach
    public void setup() {
        fixedClock = mock(Clock.class);
        objectInvalidator = new ObjectInvalidator(fixedClock);
    }

    @Test
    public void testObjectInvalidation() {
        Instant now = Instant.parse("2023-10-26T00:00:00.00Z"); // Set the initial time

        when(fixedClock.instant()).thenReturn(now);

        assertFalse(objectInvalidator.isInvalidated()); // Object should not be invalidated yet

        // Advance the clock by 30 minutes
        now = now.plusSeconds(30 * 60);
        when(fixedClock.instant()).thenReturn(now);

        assertTrue(objectInvalidator.isInvalidated()); // Object should be invalidated
    }
}
