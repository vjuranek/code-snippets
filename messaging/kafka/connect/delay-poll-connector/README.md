Simple Kafka Connect source connector

Build connecotr and copy to Kafka Connect plugin path (`plugin.path` in `config/connect-standalone.properties`)
```
mvn clean package
cp target/delay-poll-connector-1.0-SNAPSHOT.jar $KAFKA_CONNECT_PLUING_PATH
```

Start Zookeeper, Kafka and Kafka Connect:
```
./bin/zookeeper-server-start.sh config/zookeeper.properties
./bin/kafka-server-start.sh config/server.properties
./bin/connect-standalone.sh config/connect-standalone.properties
```

Start connector:
```
curl -i -X POST -H "Accept:application/json" -H  "Content-Type:application/json" http://localhost:8083/connectors/ -d @delay_poll.json
```

Consume test topic:
```
./bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --from-beginning --property print.key=true --topic test
```
