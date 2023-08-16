package io.github.vjuranek;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class SparkKafkaBasic {
    public static void main(String[] args) throws Exception {
        SparkConf conf = new SparkConf().setMaster("local[2]").setAppName("test");
        JavaStreamingContext jssc = new JavaStreamingContext(conf, Durations.seconds(10));


        Set<String> topicsSet = new HashSet<>(Arrays.asList("test"));
        Map<String, Object> kafkaParams = new HashMap<>();
        kafkaParams.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        kafkaParams.put(ConsumerConfig.GROUP_ID_CONFIG, "test");
        kafkaParams.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        kafkaParams.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        JavaInputDStream<ConsumerRecord<String, String>> messages = KafkaUtils.createDirectStream(
                jssc,
                LocationStrategies.PreferConsistent(),
                ConsumerStrategies.Subscribe(topicsSet, kafkaParams));

        JavaDStream<String> keys = messages.map(ConsumerRecord::key);
        JavaDStream<String> values = messages.map(ConsumerRecord::value);
        keys.print();
        values.print();

        jssc.start();
        jssc.awaitTermination();
    }
}
