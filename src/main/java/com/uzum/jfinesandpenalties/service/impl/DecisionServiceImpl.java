package com.uzum.jfinesandpenalties.service.impl;

import com.uzum.jfinesandpenalties.dto.event.DecisionCreatedEvent;
import com.uzum.jfinesandpenalties.entity.DecisionEntity;
import com.uzum.jfinesandpenalties.exception.DataNotFoundException;
import com.uzum.jfinesandpenalties.mapper.DecisionMapper;
import com.uzum.jfinesandpenalties.repository.DecisionRepository;
import com.uzum.jfinesandpenalties.service.DecisionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class DecisionServiceImpl implements DecisionService {

    DecisionRepository decisionRepository;
    DecisionMapper decisionMapper;

    @Override
    @Transactional
    public void createDecision(DecisionCreatedEvent event) {
        var decision = decisionMapper.toEntity(event);
        decisionRepository.save(decision);
    }

    @Override
    public DecisionEntity findById(Long id) {
        return decisionRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Decision not found"));
    }
}
