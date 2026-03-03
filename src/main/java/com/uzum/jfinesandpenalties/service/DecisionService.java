package com.uzum.jfinesandpenalties.service;

import com.uzum.jfinesandpenalties.dto.event.DecisionCreatedEvent;

public interface DecisionService {
    void createDecision(DecisionCreatedEvent event);
}
