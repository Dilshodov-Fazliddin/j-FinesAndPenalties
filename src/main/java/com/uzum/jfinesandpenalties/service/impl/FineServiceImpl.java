package com.uzum.jfinesandpenalties.service.impl;

import com.uzum.jfinesandpenalties.component.adapter.CourtAdapter;
import com.uzum.jfinesandpenalties.component.adapter.GcpAdapter;
import com.uzum.jfinesandpenalties.component.adapter.NotificationAdapter;
import com.uzum.jfinesandpenalties.component.kafka.producer.KafkaFineProducer;
import com.uzum.jfinesandpenalties.dto.event.FineCreatedEvent;
import com.uzum.jfinesandpenalties.dto.request.FineRequest;
import com.uzum.jfinesandpenalties.dto.response.ArticleResponse;
import com.uzum.jfinesandpenalties.dto.response.FineResponse;
import com.uzum.jfinesandpenalties.dto.response.GcpResponse;
import com.uzum.jfinesandpenalties.entity.FineEntity;
import com.uzum.jfinesandpenalties.mapper.FineMapper;
import com.uzum.jfinesandpenalties.service.FineService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class FineServiceImpl implements FineService {

    KafkaFineProducer kafkaFineProducer;
    CourtAdapter courtAdapter;
    GcpAdapter gcpAdapter;
    NotificationAdapter notificationAdapter;
    FineMapper fineMapper;


    @Transactional
    @Override
    public FineResponse create(FineRequest fineRequest) {
        log.info("qotpya kemadi");
        var articleResponse = courtAdapter.fetchArticleById(fineRequest.articleId());
        log.info("galdi court response");

        var user = gcpAdapter.getUser(fineRequest.OffenderPersonalIdentificationNumber());

        log.info("galdi adapterresponse");

        var fine = fineMapper.toEntity(fineRequest);

        fine.setPassportNumber(user.passportNumber());
        fine.setPenaltyAmount(articleResponse.fine());

       // notificationAdapter.sendNotificationEmail(null, user.mail());

        kafkaFineProducer.publishForFineCreatedTopic(
                FineCreatedEvent
                        .builder()
                        .fineId(fine.getId())
                        .articleId(articleResponse.id())
                        .officerId(fineRequest.officerId())
                        .build());

        return fineMapper.toResponse(fine);
    }
}
