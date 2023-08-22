package io.github.vjuranek;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.streaming.StreamingQuery;
import org.apache.spark.sql.types.StructType;

import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.from_json;
import static org.apache.spark.sql.types.DataTypes.FloatType;
import static org.apache.spark.sql.types.DataTypes.IntegerType;

public class SparkKafkaStructuredStreamingJson {

    public static void main(String[] args) throws Exception {
        SparkSession spark = SparkSession.builder().appName("SparkKafkaStructuredStreaming").getOrCreate();
        Dataset<Row> records = spark
                .readStream()
                .format("kafka")
                .option("kafka.bootstrap.servers", "localhost:9092")
                .option("subscribe", "spark.public.boston_train")
                .option("startingOffsets", "earliest")
                .load()
                .selectExpr("CAST(value AS STRING)");

        StructType schema = new StructType()
                .add("id", IntegerType, true)
                .add("crim", FloatType, true)
                .add("zn", FloatType, true)
                .add("indus", FloatType, true)
                .add("chas", FloatType, true)
                .add("nox", FloatType, true)
                .add("rm", FloatType, true)
                .add("age", FloatType, true)
                .add("dis", FloatType, true)
                .add("rad", FloatType, true)
                .add("tax", FloatType, true)
                .add("ptratio", FloatType, true)
                .add("lstat", FloatType, true)
                .add("medv", FloatType, true)
                .add("target", FloatType, true);

        Dataset<Row> values = records.select(from_json(col("value"), schema).as("payload")).select("payload.*");
        Dataset<Row> features = values.select("crim", "zn", "indus", "chas", "nox", "rm", "age", "dis", "rad", "tax", "ptratio", "lstat");

        StreamingQuery descQuery = features.writeStream()
                .outputMode("append")
                .format("console")
                .start();
        descQuery.awaitTermination();
    }
}
