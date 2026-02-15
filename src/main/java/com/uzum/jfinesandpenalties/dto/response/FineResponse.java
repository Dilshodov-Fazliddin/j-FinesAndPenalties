package com.uzum.jfinesandpenalties.dto.response;

public record FineResponse(
        Long id,

        String name,

        String passportNumber,

        Long articleId,

        Long penaltyAmount

) {
}
