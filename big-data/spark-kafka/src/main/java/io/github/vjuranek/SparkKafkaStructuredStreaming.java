package io.github.vjuranek;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.streaming.StreamingQuery;

public class SparkKafkaStructuredStreaming {

    public static void main(String[] args) throws Exception {
        SparkSession spark = SparkSession.builder().appName("SparkKafkaStructuredStreaming").getOrCreate();
        Dataset<Row> records = spark
                .readStream()
                .format("kafka")
                .option("kafka.bootstrap.servers", "localhost:9092")
                .option("subscribe", "test")
                .load()
                .selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)");

        Dataset<String> keys = records.select("key").as(Encoders.STRING());
        Dataset<String> values = records.select("value").as(Encoders.STRING());

        StreamingQuery keyQuery = keys.writeStream()
                .outputMode("append")
                .format("console")
                .start();
        StreamingQuery valueQuery = values.writeStream()
                .outputMode("append")
                .format("console")
                .start();

        keyQuery.awaitTermination();
        valueQuery.awaitTermination();
    }
}
