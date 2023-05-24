package io.github.vjuranek;

import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigDef.Type;
import org.apache.kafka.common.config.ConfigDef.Importance;

import java.util.Map;

public class DelayPollConnectorConfig extends AbstractConfig {

    public static final String VERSION = "0.0.1";

    public static final String TOPIC = "topic";
    private static final String TOPIC_DOC = "Kafka topic";
    public static final String DELAY_POLL_DURATION = "delay.poll.duration";
    private static final String DELAY_POLL_DOC = "Poll delay in seconds";

    public DelayPollConnectorConfig(ConfigDef config, Map<String, String> parsedConfig) {
        super(config, parsedConfig);
    }

    public DelayPollConnectorConfig(Map<String, String> parsedConfig) {
        this(conf(), parsedConfig);
    }

    public static ConfigDef conf() {
        return new ConfigDef()
                .define(TOPIC, Type.STRING, Importance.HIGH, TOPIC_DOC)
                .define(DELAY_POLL_DURATION, Type.STRING, Importance.HIGH, DELAY_POLL_DOC);
    }

    public String getDelayPollDuration() {
        return this.getString(DELAY_POLL_DURATION);
    }
}
