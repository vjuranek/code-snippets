package io.github.vjuranek.monitoring;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

public class SimpleMeter {
    private static Counter counter = Metrics.counter("test_counter");

    void main(String[] args) {
        Metrics.addRegistry(new SimpleMeterRegistry());
        IO.println("Starting micrometer test");
        for (int i = 0; i < 100; i++) {
            incrementCounter();
        }
        IO.println("Counter: %s".formatted(counter.count()));
        IO.println("Done");
    }

    public static void incrementCounter() {
        IO.println("Incrementing counter");
        counter.increment();
    }
}
