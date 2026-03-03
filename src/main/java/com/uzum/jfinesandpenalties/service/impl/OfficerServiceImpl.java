package com.uzum.jfinesandpenalties.service.impl;

import com.uzum.jfinesandpenalties.dto.request.OfficerRequest;
import com.uzum.jfinesandpenalties.dto.request.OfficerUpdateRequest;
import com.uzum.jfinesandpenalties.dto.response.OfficerResponse;
import com.uzum.jfinesandpenalties.entity.OfficerEntity;
import com.uzum.jfinesandpenalties.exception.DataNotFoundException;
import com.uzum.jfinesandpenalties.mapper.OfficerMapper;
import com.uzum.jfinesandpenalties.repository.OfficerRepository;
import com.uzum.jfinesandpenalties.service.OfficerService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.uzum.jfinesandpenalties.constant.Constant.OFFICER_REDIS_KEYS;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class OfficerServiceImpl implements OfficerService {

    OfficerRepository officerRepository;
    OfficerMapper officerMapper;
    PasswordEncoder passwordEncoder;

    @Override
    public OfficerResponse create(OfficerRequest officerRequest) {
        OfficerEntity officer = officerMapper.toEntity(officerRequest);

        officer.setPassword(passwordEncoder.encode(officerRequest.password()));

        var saved = officerRepository.save(officer);

        return officerMapper.toResponse(saved);
    }


    @Override
    @Cacheable(
            value = OFFICER_REDIS_KEYS
    )
    public OfficerResponse getById(Long id) {
        var officer = officerRepository
                .findById(id).orElseThrow(() -> new DataNotFoundException("Officer not found"));

        return officerMapper.toResponse(officer);
    }


    @Override
    @Transactional
    @CacheEvict(value = OFFICER_REDIS_KEYS,key = "'officer:' + #id")
    public void updateById(Long id, OfficerUpdateRequest request) {
        var officer = officerRepository
                .findById(id).orElseThrow(() -> new DataNotFoundException("Officer not found"));

        officerMapper.updateOfficerFromDto(request,officer);
    }
}
