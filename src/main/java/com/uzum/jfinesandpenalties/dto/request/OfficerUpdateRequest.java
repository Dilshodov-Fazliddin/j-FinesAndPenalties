package com.uzum.jfinesandpenalties.dto.request;

import com.uzum.jfinesandpenalties.constant.enums.Rank;

public record OfficerUpdateRequest(
        String firstName,
        String lastName,
        Integer age,
        Rank rank
) {

}
