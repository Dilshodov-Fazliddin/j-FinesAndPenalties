package com.uzum.jfinesandpenalties.service;

import com.uzum.jfinesandpenalties.dto.request.FineRequest;
import com.uzum.jfinesandpenalties.dto.response.FineResponse;

public interface FineService {
    FineResponse create(FineRequest fineRequest);
}
