package com.uzum.jfinesandpenalties.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PaymentRequest(
        @NotBlank(message = "Name is blank")
        String senderName,
        @NotBlank(message = "Token is blank")
        String senderToken,
        @NotNull(message = "amount is blank")
        Long amount,
        @NotNull(message = "fine id is null")
        Long decisionId
) {
}
