package com.uzum.jfinesandpenalties.component.kafka.consumer;

import com.uzum.jfinesandpenalties.component.adapter.NotificationAdapter;
import com.uzum.jfinesandpenalties.dto.event.FineCreatedEvent;
import com.uzum.jfinesandpenalties.entity.FineEntity;
import com.uzum.jfinesandpenalties.exception.HttpServerException;
import com.uzum.jfinesandpenalties.service.FineService;
import com.uzum.jfinesandpenalties.utils.MessageBuilder;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import static com.uzum.jfinesandpenalties.constant.KafkaConstants.*;

@Component
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class NotificationConsumer {

    NotificationAdapter notificationAdapter;
    FineService fineService;

    @KafkaListener(
            topics = FINE_CREATED,
            groupId = NOTIFICATION_GROUP_ID
    )
    public void handleFineCreated(@Payload FineCreatedEvent event){
        try {
            FineEntity fine = fineService.fetchById(event.fineId());

            String message = MessageBuilder.SEND_FINE_MESSAGE(fine);

            notificationAdapter.sendNotification(message, event.email());

            log.info("Notification sent to {}", event.email());
        }catch (HttpServerException e){

            log.info("Unexpected error: {}", e.getMessage());

            throw new RuntimeException(e);
        }
    }

}
