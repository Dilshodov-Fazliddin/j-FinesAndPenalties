package com.uzum.jfinesandpenalties.dto.response;

public record ArticleResponse(
        Long id,
        String code,
        Double fine,
        String decisionType
) {
}
