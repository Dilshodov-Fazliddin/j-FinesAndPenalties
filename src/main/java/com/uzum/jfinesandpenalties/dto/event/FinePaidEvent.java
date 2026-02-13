package com.uzum.jfinesandpenalties.dto.event;

import java.time.LocalDateTime;

public record FinePaidEvent(
        Long fineId,
        Long penaltyAmount,
        LocalDateTime paidAt
) {}


