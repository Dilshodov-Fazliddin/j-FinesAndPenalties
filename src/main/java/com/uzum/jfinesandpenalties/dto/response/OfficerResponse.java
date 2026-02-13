package com.uzum.jfinesandpenalties.dto.response;

public record OfficerResponse(
        Long id,
        String firstName,
        String lastName,
        Integer age,
        String rank,
        String budgeNumber
) {

}
