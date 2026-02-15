package com.uzum.jfinesandpenalties.dto.response;

public record GcpResponse(
        String name,
        String surname,
        String address,
        Integer age,
        String citizenship,
        String personalIdentificationNumber,
        String passportNumber,
        String email
) {
}
