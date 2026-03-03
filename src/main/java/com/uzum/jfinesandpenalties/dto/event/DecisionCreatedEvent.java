package com.uzum.jfinesandpenalties.dto.event;

import com.uzum.jfinesandpenalties.constant.enums.DecisionType;

public record DecisionCreatedEvent(
        String  decisionNumber,
        Double fineAmount,
        String comment,
        DecisionType decisionType,
        String judge
) {
}
