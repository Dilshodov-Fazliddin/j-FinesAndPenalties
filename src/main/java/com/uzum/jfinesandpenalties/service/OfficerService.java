package com.uzum.jfinesandpenalties.service;

import com.uzum.jfinesandpenalties.dto.request.OfficerRequest;
import com.uzum.jfinesandpenalties.dto.request.OfficerUpdateRequest;
import com.uzum.jfinesandpenalties.dto.response.OfficerResponse;

public interface OfficerService {
    OfficerResponse create(OfficerRequest officerRequest);
    OfficerResponse getById(Long id);
    void updateById(Long id, OfficerUpdateRequest request);
}
