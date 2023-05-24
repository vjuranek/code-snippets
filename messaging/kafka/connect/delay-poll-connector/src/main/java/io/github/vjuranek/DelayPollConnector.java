package io.github.vjuranek;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.source.SourceConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DelayPollConnector extends SourceConnector {
    private static Logger log = LoggerFactory.getLogger(DelayPollConnector.class);
    private DelayPollConnectorConfig config;

    @Override
    public String version() {
        return DelayPollConnectorConfig.VERSION;
    }

    @Override
    public void start(Map<String, String> map) {
        config = new DelayPollConnectorConfig(map);
    }

    @Override
    public Class<? extends Task> taskClass() {
        return DelayPollTask.class;
    }

    @Override
    public List<Map<String, String>> taskConfigs(int maxTasks) {
        List<Map<String, String>> taskConfigs = new ArrayList<>();
        for (int i = 0; i < maxTasks; i++) {
            taskConfigs.add(i, config.originalsStrings());
        }
        return taskConfigs;
    }

    @Override
    public void stop() {
    }

    @Override
    public ConfigDef config() {
        return DelayPollConnectorConfig.conf();
    }
}
