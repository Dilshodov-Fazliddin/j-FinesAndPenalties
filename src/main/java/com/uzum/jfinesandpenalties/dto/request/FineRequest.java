package com.uzum.jfinesandpenalties.dto.request;

import com.uzum.jfinesandpenalties.constant.enums.FineType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record FineRequest(
        @NotNull(message = "officer id is blank")
        Long officerId,

        @NotNull(message = "Fine type is null")
        FineType fineType,

        @NotBlank(message = "description is blank")
        String description,

        @NotBlank(message = "pid is null")
        String offenderPersonalIdentificationNumber,

        @NotBlank(message = "name is blank")
        String name,

        @NotNull(message = "Article id is blank")
        Long articleId
) {}
