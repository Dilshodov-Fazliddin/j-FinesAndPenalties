package com.uzum.jfinesandpenalties.dto.request;

public record OfficerUpdateRequest(
        String firstName,
        String lastName,
        Integer age,
        String rank,
        String budgeNumber,
        String password
) {

}
