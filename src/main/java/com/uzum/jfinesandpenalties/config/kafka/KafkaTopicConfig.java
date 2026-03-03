package com.uzum.jfinesandpenalties.config.kafka;

import com.uzum.jfinesandpenalties.constant.KafkaConstants;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;

@EnableKafka
@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic FinesTopic() {
        return TopicBuilder.name(KafkaConstants.FINE_CREATED).partitions(3).replicas(1).build();
    }


}
