package com.uzum.jfinesandpenalties.component.kafka.consumer;


import com.uzum.jfinesandpenalties.component.adapter.CourtAdapter;
import com.uzum.jfinesandpenalties.dto.event.FineCreatedEvent;
import com.uzum.jfinesandpenalties.dto.request.ViolationRequest;
import com.uzum.jfinesandpenalties.service.FineService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static com.uzum.jfinesandpenalties.constant.KafkaConstants.*;

@Component
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class ViolationConsumer {

    CourtAdapter courtAdapter;

    FineService fineService;

    @KafkaListener(
            topics = FINE_CREATED,
            groupId = COURT_VIOLATION_GROUP_ID
    )
    public void handleViolationCreated(@Payload FineCreatedEvent event){
        try {

            var fine = fineService.fetchById(event.fineId());

            var violation = ViolationRequest.builder()
                    .personalIdentificationNumber(fine.getOffenderPersonalIdentificationNumber())
                    .articleId(fine.getArticleId())
                    .description(fine.getDescription())
                    .offenseTime(LocalDateTime.now())
                    .build();

            courtAdapter.createViolationForOffender(violation);

            log.info("Sent to court {}", violation);

        } catch (RuntimeException e) {

            log.info("Unexpected error: {}", e.getMessage());

            throw new RuntimeException(e);
        }
    }
}
