package io.github.vjuranek;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.source.SourceRecord;
import org.apache.kafka.connect.source.SourceTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DelayPollTask extends SourceTask {
    static final Logger LOGGER = LoggerFactory.getLogger(DelayPollTask.class);

    private String topic;
    private int pollDelay;
    private int count = 0;

    @Override
    public String version() {
        return DelayPollConnectorConfig.VERSION;
    }

    @Override
    public void start(Map<String, String> map) {
        if (map.containsKey(DelayPollConnectorConfig.TOPIC)) {
            topic = map.get(DelayPollConnectorConfig.TOPIC);
        }
        else {
            throw new IllegalStateException("No topic configured");
        }
        if (map.containsKey(DelayPollConnectorConfig.DELAY_POLL_DURATION)) {
            pollDelay = Integer.valueOf(map.get(DelayPollConnectorConfig.DELAY_POLL_DURATION));
        }
        else {
            throw new IllegalStateException("No delay configured");
        }
        count = 0;
    }

    @Override
    public List<SourceRecord> poll() throws InterruptedException {
        count++;
        LOGGER.info("Increased count to {}", count);

        try {
            Thread.sleep(pollDelay * 1_000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        List<SourceRecord> records = new ArrayList<>(1);
        Map sourcePartition = Collections.singletonMap("partition", "test_partition");
        Map sourceOffset = Collections.singletonMap("count", count);
        records.add(new SourceRecord(sourcePartition, sourceOffset, topic, Schema.INT64_SCHEMA, System.currentTimeMillis(), Schema.INT32_SCHEMA, count));
        LOGGER.info("Sending record for count {}", count);

        return records;
    }

    @Override
    public void stop() {
    }
}