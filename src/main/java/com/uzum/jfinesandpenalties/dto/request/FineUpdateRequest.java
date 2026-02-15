package com.uzum.jfinesandpenalties.dto.request;

import com.uzum.jfinesandpenalties.constant.enums.FineType;
import com.uzum.jfinesandpenalties.constant.enums.FinesStatus;

public record FineUpdateRequest(
        String OffenderPersonalIdentificationNumber,
        String name,
        String passportNumber,
        Long articleId,
        FinesStatus fineStatus,
        FineType fineType,
        String description,
        Long penaltyAmount
) {
}
