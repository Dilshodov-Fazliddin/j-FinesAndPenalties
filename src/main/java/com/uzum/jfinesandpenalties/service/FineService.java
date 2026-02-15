package com.uzum.jfinesandpenalties.service;

import com.uzum.jfinesandpenalties.dto.request.FineRequest;
import com.uzum.jfinesandpenalties.dto.request.FineUpdateRequest;
import com.uzum.jfinesandpenalties.dto.response.FineResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FineService {
    FineResponse create(FineRequest fineRequest);
    Page<FineResponse> getAllFine(Pageable pageable);
    FineResponse getFineById(Long id);
    void updateResponseById(Long id, FineUpdateRequest updateRequest);
}
