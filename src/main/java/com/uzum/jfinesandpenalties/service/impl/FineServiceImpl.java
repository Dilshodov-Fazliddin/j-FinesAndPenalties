package com.uzum.jfinesandpenalties.service.impl;

import com.uzum.jfinesandpenalties.component.adapter.CourtAdapter;
import com.uzum.jfinesandpenalties.component.adapter.GcpAdapter;
import com.uzum.jfinesandpenalties.component.adapter.NotificationAdapter;
import com.uzum.jfinesandpenalties.component.kafka.producer.KafkaFineProducer;
import com.uzum.jfinesandpenalties.dto.event.FineCreatedEvent;
import com.uzum.jfinesandpenalties.dto.request.FineRequest;
import com.uzum.jfinesandpenalties.dto.request.FineUpdateRequest;
import com.uzum.jfinesandpenalties.dto.response.ArticleResponse;
import com.uzum.jfinesandpenalties.dto.response.FineResponse;
import com.uzum.jfinesandpenalties.dto.response.GcpResponse;
import com.uzum.jfinesandpenalties.dto.response.PageResponse;
import com.uzum.jfinesandpenalties.entity.FineEntity;
import com.uzum.jfinesandpenalties.entity.OfficerEntity;
import com.uzum.jfinesandpenalties.exception.DataNotFoundException;
import com.uzum.jfinesandpenalties.mapper.FineMapper;
import com.uzum.jfinesandpenalties.repository.FineRepository;
import com.uzum.jfinesandpenalties.repository.OfficerRepository;
import com.uzum.jfinesandpenalties.service.FineService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.uzum.jfinesandpenalties.constant.Constant.FINES_REDIS_KEYS;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class FineServiceImpl implements FineService {

    KafkaFineProducer kafkaFineProducer;
    CourtAdapter courtAdapter;
    GcpAdapter gcpAdapter;
    FineMapper fineMapper;
    FineRepository fineRepository;
    OfficerRepository officerRepository;

    @Override
    @Transactional
    public FineResponse create(FineRequest fineRequest) {

        var articleResponse = courtAdapter.fetchArticleById(fineRequest.articleId());

        var offender = gcpAdapter.getUser(fineRequest.offenderPersonalIdentificationNumber());

        var officerEntity = officerRepository
                .findById(fineRequest.officerId())
                .orElseThrow(() -> new DataNotFoundException("Officer with id: " + fineRequest.officerId() + "not found"));

        FineEntity fine = buildFine(fineRequest, articleResponse, offender, officerEntity);

        fine = fineRepository.save(fine);

        log.info("fine saved to db fine id: {}", fine.getId());

        publishFineCreatedEvent(fine,articleResponse,fineRequest,offender.email());

        log.info("sent to topic {}", fine.getId());

        return fineMapper.toResponse(fine);
    }

    @Override
    @Cacheable(
            value = FINES_REDIS_KEYS,
            key = "'page:' + #pageable.pageNumber + ':' + #pageable.pageSize + ':' + #pageable.sort.toString()",
            unless = "#result.content.isEmpty()"
    )
    public PageResponse<FineResponse> getAllFine(Pageable pageable) {
        var fines = fineRepository.findAll(pageable).map(fineMapper::toResponse);

        return new PageResponse<>(
                fines.getContent(),
                fines.getTotalPages(),
                fines.getSize(),
                fines.getTotalElements()
        );
    }

    @Override
    @Cacheable(
            value = FINES_REDIS_KEYS
    )
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

    @Override
    public FineEntity fetchById(Long id) {
        return fineRepository.findById(id).orElseThrow(()->new DataNotFoundException("Fine not found"));
    }

    private FineEntity buildFine(FineRequest request, ArticleResponse article, GcpResponse offender, OfficerEntity officer) {
        var fine = fineMapper.toEntity(request);
        fine.setPenaltyAmount(article.fine());
        fine.setPassportNumber(offender.passportNumber());
        fine.setOffenderName(offender.name());
        fine.setOfficer(officer);
        fine.setDescription(request.fineType().getDescription());

        return fine;
    }

    private void publishFineCreatedEvent(FineEntity fine, ArticleResponse article, FineRequest request,String email) {
        if (email != null) {
            kafkaFineProducer.publishForFineCreatedTopic(
                    FineCreatedEvent.builder()
                            .fineId(fine.getId())
                            .articleId(article.id())
                            .officerId(request.officerId())
                            .email(email)
                            .build()
            );
            log.info("Sent fineCreatedEvent to Kafka, fineId: {}", fine.getId());
        } else {
            log.warn("Offender email is null, event not sent for fineId: {}", fine.getId());
        }
    }
}
