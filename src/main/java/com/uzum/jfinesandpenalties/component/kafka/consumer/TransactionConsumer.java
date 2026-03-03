package com.uzum.jfinesandpenalties.component.kafka.consumer;

import com.uzum.jfinesandpenalties.constant.KafkaConstants;
import com.uzum.jfinesandpenalties.dto.event.FineCreatedEvent;
import com.uzum.jfinesandpenalties.dto.event.TransactionEvent;
import com.uzum.jfinesandpenalties.service.TransactionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import static com.uzum.jfinesandpenalties.constant.KafkaConstants.FINE_PAID;
import static com.uzum.jfinesandpenalties.constant.KafkaConstants.FINE_PAID_ID;

@Component
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TransactionConsumer {

    TransactionService transactionService;


    @KafkaListener(topics = FINE_PAID, groupId = FINE_PAID_ID)
    public void handleViolationCreated(@Payload TransactionEvent event) {
        try {
            transactionService.saveTransaction(event);
            log.info("TRANSACTION SAVED");
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
