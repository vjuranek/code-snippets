package io.github.vjuranek;

import java.util.Iterator;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.connector.base.DeliveryGuarantee;
import org.apache.flink.connector.kafka.sink.KafkaRecordSerializationSchema;
import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.connector.kafka.source.reader.deserializer.KafkaRecordDeserializationSchema;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.JsonNode;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.util.serialization.JSONKeyValueDeserializationSchema;

public class FlinkKafkaWithSchema {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        KafkaSource<ObjectNode> source = KafkaSource.<ObjectNode>builder()
                .setBootstrapServers("localhost:9092")
                .setTopics("flink.public.boston_train")
                .setGroupId("my-group")
                .setStartingOffsets(OffsetsInitializer.earliest())
                .setDeserializer(KafkaRecordDeserializationSchema.of(new JSONKeyValueDeserializationSchema(false)))
                .build();
        DataStreamSource<ObjectNode> recordStream = env.fromSource(source, WatermarkStrategy.noWatermarks(), "Kafka Source");

        KafkaSink<String> kafkaSink = KafkaSink.<String>builder()
                .setBootstrapServers("localhost:9092")
                .setRecordSerializer(KafkaRecordSerializationSchema.builder()
                        .setTopic("test-out")
                        .setValueSerializationSchema(new SimpleStringSchema())
                        .build()
                )
                .setDeliveryGuarantee(DeliveryGuarantee.AT_LEAST_ONCE)
                .build();

        recordStream.map(new RecordMapper()).sinkTo(kafkaSink);

        env.execute("Flink Kafka");
    }

    private static class RecordMapper implements MapFunction<ObjectNode, String> {
        @Override public String map(ObjectNode node) throws Exception {
            JsonNode values = node.get("value").get("payload");
            StringBuffer sb = new StringBuffer();
            Iterator<String> iter = values.fieldNames();
            while (iter.hasNext()) {
                sb.append(iter.next());
                sb.append(" ");
            }
            return "node fileds: " + sb.toString();
        }
    }
}
