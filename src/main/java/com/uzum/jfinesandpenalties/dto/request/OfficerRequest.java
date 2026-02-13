package com.uzum.jfinesandpenalties.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record OfficerRequest(
        @NotBlank(message = "First name is required")
        String firstName,

        @NotBlank(message = "Last name is required")
        String lastName,

        @NotNull(message = "Age is required")
        @Min(value = 18, message = "User must be at least 18 years old")
        Integer age,

        @NotBlank(message = "Rank is required")
        String rank,

        @NotBlank(message = "Badge number is required")
        String budgeNumber,

        @NotBlank(message = "Password is required")
        @Size(min = 8, message = "Password must be at least 8 characters long")
        String password
) {
}
