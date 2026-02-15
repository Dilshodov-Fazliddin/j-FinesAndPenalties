package com.uzum.jfinesandpenalties.service.impl;

import com.uzum.jfinesandpenalties.component.adapter.CourtAdapter;
import com.uzum.jfinesandpenalties.component.adapter.GcpAdapter;
import com.uzum.jfinesandpenalties.component.adapter.NotificationAdapter;
import com.uzum.jfinesandpenalties.component.kafka.producer.KafkaFineProducer;
import com.uzum.jfinesandpenalties.dto.event.FineCreatedEvent;
import com.uzum.jfinesandpenalties.dto.request.FineRequest;
import com.uzum.jfinesandpenalties.dto.request.FineUpdateRequest;
import com.uzum.jfinesandpenalties.dto.response.FineResponse;
import com.uzum.jfinesandpenalties.entity.FineEntity;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Override
    public Page<FineResponse> getAllFine(Pageable pageable) {
        var fineEntities = fineRepository.findAll(pageable);
        return fineEntities.map(fineMapper::toResponse);
    }

    @Override
    public FineResponse getFineById(Long id) {
        var fineEntity = fineRepository
                .findById(id)
                .orElseThrow(() -> new DataNotFoundException("The fine with this id not found id:" + id));

        return fineMapper.toResponse(fineEntity);
    }


    @Override
    @Transactional
    public void updateResponseById(Long id, FineUpdateRequest updateRequest) {
        var fineEntity = fineRepository
                .findById(id)
                .orElseThrow(() -> new DataNotFoundException("The fine with this id not found id:" + id));

        fineMapper.updateFineFromDto(updateRequest,fineEntity);

        log.info("Fine with id: {}  updated to {}",id,updateRequest);
    }
}
