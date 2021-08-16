package com.tamedia.hiringchallenge;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TopicConfigurations {

    @Bean
    public NewTopic intputTopic() {
        return TopicBuilder.name("input-topic")
                .partitions(5)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic outputTopic() {
        return TopicBuilder.name("output-topic")
                .partitions(5)
                .replicas(1)
                .build();
    }
}
