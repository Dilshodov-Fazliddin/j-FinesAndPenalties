package com.uzum.jfinesandpenalties.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record TransactionRequest(@NotNull(message = "referenceId required") UUID referenceId,
                                 @NotNull(message = "transaction type required") String type,
                                 @NotNull(message = "amount is required") @Positive(message = "amount should be positive") Long amount,
                                 @NotNull(message = "currency required") String currency,
                                 @NotNull(message = "merchantId required") UUID merchantId,
                                 @NotBlank(message = "senderName required") String senderName,
                                 @NotBlank(message = "senderToken required") String senderToken,
                                 @NotBlank(message = "receiverName required") String receiverName,
                                 @NotBlank(message = "receiverToken required") String receiverToken) {

}


