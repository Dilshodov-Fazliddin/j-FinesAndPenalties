package com.uzum.jfinesandpenalties.dto.response;

import lombok.Builder;

import java.util.UUID;

@Builder
public record TransactionResponse(
        UUID id,
        UUID referenceId,
        String status,
        Long amount,
        String currency
) {
}
