package io.github.vjuranek.monitoring;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.jmx.JmxConfig;
import io.micrometer.jmx.JmxMeterRegistry;

public class JmxMeter {

    private static Counter counter = Metrics.counter("test_counter");

    void main(String[] args) {
        Metrics.addRegistry(new JmxMeterRegistry(JmxConfig.DEFAULT, Clock.SYSTEM));
        IO.println("Starting micrometer test");
        for (int i = 0; i < 100; i++) {
            incrementCounter();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        IO.println("Counter: %s".formatted(counter.count()));
        IO.println("Done");
    }

    public static void incrementCounter() {
        IO.println("Incrementing counter");
        counter.increment();
    }
}
