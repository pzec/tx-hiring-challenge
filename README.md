# What does this application do?
* receives frames with uid and ts from one kafka topic
* outputs to another kafka topic an estimation of how many unique uids is accepted each minute
## How to run the application
* in console/terminal go to hiring-challenge directory
* mvn clean package
* make sure kafka is up (check kafka dashboard)
* java -jar ./target/hiring-challenge-0.0.1-SNAPSHOT.jar

#### this will end up with the application being started and listening on input-topic and sending to output-topic

### sending data to input-topic
* gzcat stream.jsonl.gz | kafka-console-producer --broker-list localhost:9092 --topic input-topic