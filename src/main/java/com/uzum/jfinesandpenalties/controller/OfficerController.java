package com.uzum.jfinesandpenalties.controller;

import com.uzum.jfinesandpenalties.dto.request.OfficerRequest;
import com.uzum.jfinesandpenalties.dto.request.OfficerUpdateRequest;
import com.uzum.jfinesandpenalties.dto.response.OfficerResponse;
import com.uzum.jfinesandpenalties.service.OfficerService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fines-penalties/officers")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class OfficerController {
    OfficerService officerService;

    @PostMapping
    public OfficerResponse create(@RequestBody OfficerRequest request) {
        return officerService.create(request);
    }

    @GetMapping("/{id}")
    public OfficerResponse getById(@PathVariable Long id) {
        return officerService.getById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestBody OfficerUpdateRequest request) {
         officerService.updateById(id, request);
         return ResponseEntity.ok("Officer updated with id:" + id);
    }
}