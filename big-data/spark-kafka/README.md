= Streaming data from Kafka to Spark

== Setup

Start Kafka and console producer:
* `./bin/zookeeper-server-start.sh config/zookeeper.properties`
* `./bin/kafka-server-start.sh config/server.properties`
* `./bin/kafka-console-producer.sh --topic test --bootstrap-server localhost:9092 --property parse.key=true --property key.separator=":"`

Compile the Spark app and send it to Spark:
* `./bin/spark-submit $PATH_TO_THE_REPO/big-data/spark-kafka/target/spark-kafka-1.0-SNAPSHOT-jar-with-dependencies.jar`