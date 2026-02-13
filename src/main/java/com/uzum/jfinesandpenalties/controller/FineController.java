package com.uzum.jfinesandpenalties.controller;

import com.uzum.jfinesandpenalties.dto.request.FineRequest;
import com.uzum.jfinesandpenalties.dto.response.FineResponse;
import com.uzum.jfinesandpenalties.service.FineService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
