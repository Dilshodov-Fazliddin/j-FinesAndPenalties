package com.uzum.jfinesandpenalties.dto.event;

import lombok.Builder;

import java.util.UUID;

@Builder
public record TransactionEvent(
        UUID transactionId,
        UUID referenceId,
        String status,
        Long amount,
        String currency
) {
}
