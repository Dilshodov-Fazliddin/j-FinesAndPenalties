package com.uzum.jfinesandpenalties.service;

import com.uzum.jfinesandpenalties.dto.event.DecisionCreatedEvent;
import com.uzum.jfinesandpenalties.entity.DecisionEntity;

public interface DecisionService {
    void createDecision(DecisionCreatedEvent event);
    DecisionEntity findById(Long id);
}
