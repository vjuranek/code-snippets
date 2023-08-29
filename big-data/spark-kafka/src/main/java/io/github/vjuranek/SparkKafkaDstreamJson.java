package io.github.vjuranek;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.SparkConf;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.regression.StreamingLinearRegressionWithSGD;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import scala.Tuple2;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class SparkKafkaDstreamJson {

    public static void main(String[] args) throws Exception {
        SparkConf conf = new SparkConf().setMaster("local[2]").setAppName("test");
        JavaStreamingContext jssc = new JavaStreamingContext(conf, Durations.seconds(10));


        Set<String> trainTopic = new HashSet<>(Arrays.asList("spark.public.boston_train"));
        Set<String> testTopic = new HashSet<>(Arrays.asList("spark.public.boston_test"));
        Map<String, Object> kafkaParams = new HashMap<>();
        kafkaParams.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        kafkaParams.put(ConsumerConfig.GROUP_ID_CONFIG, "dbz_demo21");
        kafkaParams.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        kafkaParams.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        kafkaParams.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        JavaInputDStream<ConsumerRecord<String, String>> trainStream = KafkaUtils.createDirectStream(
                jssc,
                LocationStrategies.PreferConsistent(),
                ConsumerStrategies.Subscribe(trainTopic, kafkaParams));
        JavaInputDStream<ConsumerRecord<String, String>> testStream = KafkaUtils.createDirectStream(
                jssc,
                LocationStrategies.PreferConsistent(),
                ConsumerStrategies.Subscribe(testTopic, kafkaParams));

        JavaDStream<LabeledPoint> train = trainStream.map(ConsumerRecord::value)
                .map(SparkKafkaDstreamJson::toLabeledPointString)
                .map(LabeledPoint::parse)
                .cache();
        train.print();

        JavaDStream<LabeledPoint> test = testStream.map(ConsumerRecord::value)
                .map(SparkKafkaDstreamJson::toLabeledPointString)
                .map(LabeledPoint::parse);
        JavaPairDStream<Double, Vector> predict = test.mapToPair(lp -> new Tuple2<>(lp.label(), lp.features()));
        test.print();

        int numFeatures = 2;
        StreamingLinearRegressionWithSGD model = new StreamingLinearRegressionWithSGD().setInitialWeights(Vectors.zeros(numFeatures));
        model.trainOn(train);

        model.predictOnValues(predict).print();
        model.predictOn(train.map(lp -> lp.getFeatures())).print();
        model.predictOn(test.map(lp -> lp.getFeatures())).print();

        jssc.start();
        jssc.awaitTermination();
        jssc.stop();
    }

    private static String toLabeledPointString(String json) throws ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONObject o = (JSONObject)jsonParser.parse(json);
        //return String.format("%s, %s %s %s %s %s %s %s %s %s %s %s %s %s",
        return String.format("%s, %s %s",
                //o.get("target"),
                o.get("medv"),
                //o.get("crim"),
                //o.get("zn"),
                //o.get("indus"),
                //o.get("chas"),
                //o.get("nox"),
                o.get("rm"),
                //o.get("age"),
                //o.get("dis"),
                //o.get("rad"),
                //o.get("tax"),
                //o.get("ptratio"),
                o.get("lstat"));
    }
}
