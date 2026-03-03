package com.uzum.jfinesandpenalties.component.kafka.consumer;

import com.uzum.jfinesandpenalties.constant.KafkaConstants;
import com.uzum.jfinesandpenalties.dto.event.DecisionCreatedEvent;
import com.uzum.jfinesandpenalties.service.DecisionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import static com.uzum.jfinesandpenalties.constant.KafkaConstants.COURT_GROUP_ID;
import static com.uzum.jfinesandpenalties.constant.KafkaConstants.DECISION_CREATED;

@Component
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class DecisionConsumer {

    DecisionService decisionService;

    @KafkaListener(
            topics = DECISION_CREATED,
            groupId = COURT_GROUP_ID
    )
    public void handleDecision(@Payload DecisionCreatedEvent event) {
        try {
            decisionService.createDecision(event);
            log.info("Decision processed successfully {}", event);
        } catch (Exception e) {
            log.error("Error processing decision event", e);
            throw e;
        }
    }
}
