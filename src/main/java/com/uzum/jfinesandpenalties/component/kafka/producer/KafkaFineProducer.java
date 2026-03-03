package com.uzum.jfinesandpenalties.component.kafka.producer;

import com.uzum.jfinesandpenalties.component.adapter.NotificationAdapter;
import com.uzum.jfinesandpenalties.constant.KafkaConstants;
import com.uzum.jfinesandpenalties.dto.event.FineCreatedEvent;
import com.uzum.jfinesandpenalties.dto.event.FinePaidEvent;
import com.uzum.jfinesandpenalties.utils.MessageBuilder;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class KafkaFineProducer {
    KafkaTemplate<String, Object> kafkaTemplate;

    public void publishForFineCreatedTopic(FineCreatedEvent event){
        kafkaTemplate.send(KafkaConstants.FINE_CREATED,event);
        log.info("Sent FineCreatedEvent: {}", event);
    }

    public void publishForFinePaidEvent(FinePaidEvent event) {
        kafkaTemplate.send(KafkaConstants.FINE_PAID, event);
        log.info("Sent FinePaidEvent: {}", event);
    }
}


