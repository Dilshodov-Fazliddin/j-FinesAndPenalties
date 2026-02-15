package com.uzum.jfinesandpenalties.controller;

import com.uzum.jfinesandpenalties.dto.request.FineRequest;
import com.uzum.jfinesandpenalties.dto.request.FineUpdateRequest;
import com.uzum.jfinesandpenalties.dto.response.FineResponse;
import com.uzum.jfinesandpenalties.service.FineService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fines-penalties/fines")
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class FineController {

    FineService fineService;

    @PostMapping
    public ResponseEntity<FineResponse> createFine(@Valid @RequestBody FineRequest fineRequest) {
        return ResponseEntity.ok(fineService.create(fineRequest));
    }

    @GetMapping
    public Page<FineResponse> getAllFines(
            Pageable pageable
    ) {
        return fineService.getAllFine(pageable);
    }

    @GetMapping("/{id}")
    public FineResponse getFineById(
            @PathVariable Long id
    ) {
        return fineService.getFineById(id);
    }

    @PutMapping("/{id}")
    public void updateFine(
            @PathVariable Long id,
            @Valid @RequestBody FineUpdateRequest updateRequest
    ) {
        fineService.updateResponseById(id, updateRequest);
    }

}
