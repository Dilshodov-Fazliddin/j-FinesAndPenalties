package com.uzum.jfinesandpenalties.dto.response;

import com.uzum.jfinesandpenalties.constant.enums.Rank;

public record OfficerResponse(
        Long id,
        String firstName,
        String lastName,
        Integer age,
        Rank rank,
        String budgeNumber
) {

}
