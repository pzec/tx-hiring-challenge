package com.tamedia.hiringchallenge;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ConcurrentHashMultiset;
import com.google.common.collect.Multiset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;


@SpringBootApplication
public class HiringChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(HiringChallengeApplication.class, args);
	}

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	private Multiset<String> uidMultiset = ConcurrentHashMultiset.create();
	private ObjectMapper objectMapper = new ObjectMapper();
	private Long minTs;
	private Long maxTs;
	private Long iteration = 1l;

	@Value( "${output-topic}" )
	private String outputTopicName;

	@KafkaListener(id = "myId", topics = "input-topic")
	public void listen(String in) {
		//
		//System.out.println(in);
		InputData inputData = null;
		try {
			inputData = objectMapper.readValue(in, InputData.class);
			if (minTs == null || minTs > inputData.getTs()) {
				minTs = inputData.getTs();
			}
			if (maxTs == null || maxTs < inputData.getTs()) {
				maxTs = inputData.getTs();
			}
			uidMultiset.add(inputData.getUid());
			//update information every (long number) iterations and submit to output kafka topic
			if (iteration % 5000 == 0 ) {
				OutputData outputData = new OutputData();
				int allEventsRecorded = uidMultiset.stream()
						.map(s -> uidMultiset.count(s))
						.reduce(0, Integer::sum);
				double average = (60000d/(maxTs - minTs)) * allEventsRecorded/uidMultiset.size();
				int minOccurrence = uidMultiset.stream()
						.map(s -> uidMultiset.count(s))
						.min(Integer::compare).get();
				int maxOccurrence = uidMultiset.stream()
						.map(s -> uidMultiset.count(s))
						.max(Integer::compare).get();
				// these arithmetic operations seem to add to execution time
				// so they were commented out
				double lowerErrorBound = minOccurrence*60000d/(1d*(maxTs - minTs));
				double upperErrorBound = maxOccurrence*60000d/(maxTs - minTs);
				outputData.setAverageUsersPerMinute(average);
				outputData.setMaxOccurancesPerUid(maxOccurrence);
				outputData.setMinOccurancesPerUid(minOccurrence);
				// the same as above
				outputData.setLowerErrorBound(Math.round(lowerErrorBound * 100.0) / 100.0);
				outputData.setUpperErrorBound(Math.round(upperErrorBound * 100.0) / 100.0);
				kafkaTemplate.send(outputTopicName, objectMapper.writeValueAsString(outputData));
			} else {
				iteration++;
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

}
