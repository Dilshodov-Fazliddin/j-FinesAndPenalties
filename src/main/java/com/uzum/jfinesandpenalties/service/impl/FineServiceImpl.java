package com.uzum.jfinesandpenalties.service.impl;

import com.uzum.jfinesandpenalties.component.adapter.CourtAdapter;
import com.uzum.jfinesandpenalties.component.adapter.GcpAdapter;
import com.uzum.jfinesandpenalties.component.adapter.NotificationAdapter;
import com.uzum.jfinesandpenalties.component.kafka.producer.KafkaFineProducer;
import com.uzum.jfinesandpenalties.dto.event.FineCreatedEvent;
import com.uzum.jfinesandpenalties.dto.request.FineRequest;
import com.uzum.jfinesandpenalties.dto.response.FineResponse;
import com.uzum.jfinesandpenalties.exception.DataNotFoundException;
import com.uzum.jfinesandpenalties.mapper.FineMapper;
import com.uzum.jfinesandpenalties.repository.FineRepository;
import com.uzum.jfinesandpenalties.repository.OfficerRepository;
import com.uzum.jfinesandpenalties.service.FineService;
import com.uzum.jfinesandpenalties.utils.MessageBuilder;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    FineRepository fineRepository;
    OfficerRepository officerRepository;


    @Override
    public FineResponse create(FineRequest fineRequest) {

        var articleResponse = courtAdapter.fetchArticleById(fineRequest.articleId());
        var offender = gcpAdapter.getUser(fineRequest.offenderPersonalIdentificationNumber());
        var officerEntity = officerRepository
                .findById(fineRequest.officerId())
                .orElseThrow(() -> new DataNotFoundException("Officer with id: " + fineRequest.officerId() + "not found"));

        var fine = fineMapper.toEntity(fineRequest);

        fine.setPassportNumber(offender.passportNumber());
        fine.setPenaltyAmount(articleResponse.fine());
        fine.setOfficer(officerEntity);

        fine = fineRepository.save(fine);

        log.info("fine saved to db fine id: {}", fine.getId());

        kafkaFineProducer.publishForFineCreatedTopic(
                FineCreatedEvent
                        .builder()
                        .fineId(fine.getId())
                        .articleId(articleResponse.id())
                        .officerId(fineRequest.officerId())
                        .build());

        log.info("sent to topic {}", fine.getId());

        var message = MessageBuilder
                .SEND_FINE_MESSAGE(fineRequest, fine.getPenaltyAmount(), officerEntity.getFirstName());

        notificationAdapter.sendNotificationEmail(message, offender.email());

        log.info("notification send for {}", offender.email());
        return fineMapper.toResponse(fine);
    }
}
