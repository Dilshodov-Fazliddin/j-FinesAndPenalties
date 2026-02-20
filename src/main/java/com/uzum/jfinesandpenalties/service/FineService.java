package com.uzum.jfinesandpenalties.service;

import com.uzum.jfinesandpenalties.dto.request.FineRequest;
import com.uzum.jfinesandpenalties.dto.request.FineUpdateRequest;
import com.uzum.jfinesandpenalties.dto.response.ArticleResponse;
import com.uzum.jfinesandpenalties.dto.response.FineResponse;
import com.uzum.jfinesandpenalties.dto.response.GcpResponse;
import com.uzum.jfinesandpenalties.dto.response.PageResponse;
import com.uzum.jfinesandpenalties.entity.FineEntity;
import com.uzum.jfinesandpenalties.entity.OfficerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FineService {
    FineResponse create(FineRequest fineRequest);
    PageResponse<FineResponse> getAllFine(Pageable pageable);
    FineResponse getFineById(Long id);
    void updateResponseById(Long id, FineUpdateRequest updateRequest);
    FineEntity fetchById(Long id);
}
