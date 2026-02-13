package com.uzum.jfinesandpenalties.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record FineRequest(
        @NotNull(message = "fine is blank")
        Long officerId,

        @NotBlank(message = "pid is null")
        String OffenderPersonalIdentificationNumber,

        @NotBlank(message = "name is blank")
        String name,

        @NotBlank(message = "passport number is blank")
        String passportNumber,

        @NotNull(message = "Article id is blank")
        Long articleId
) {}
