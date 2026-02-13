package com.uzum.jfinesandpenalties.dto.event;

import lombok.Builder;

@Builder
public record FineCreatedEvent(
        Long fineId,
        Long articleId,
        Long penaltyAmount,
        Long officerId
) {}
