package com.uzum.jfinesandpenalties.dto.response;

public record FineResponse(
        Long id,

        String offenderName,

        String passportNumber,

        Long articleId,

        Long penaltyAmount

) {
}
