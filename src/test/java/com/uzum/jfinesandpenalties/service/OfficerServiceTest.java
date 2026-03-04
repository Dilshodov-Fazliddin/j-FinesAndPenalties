package com.uzum.jfinesandpenalties.service;

import com.uzum.jfinesandpenalties.constant.enums.Rank;
import com.uzum.jfinesandpenalties.dto.request.OfficerRequest;
import com.uzum.jfinesandpenalties.dto.response.OfficerResponse;
import com.uzum.jfinesandpenalties.entity.OfficerEntity;
import com.uzum.jfinesandpenalties.mapper.OfficerMapper;
import com.uzum.jfinesandpenalties.repository.OfficerRepository;
import com.uzum.jfinesandpenalties.service.impl.OfficerServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class OfficerServiceTest {

    @Mock
    private OfficerRepository officerRepository;

    @Mock
    private OfficerMapper officerMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private OfficerServiceImpl officerService;

    OfficerResponse officerResponse = new OfficerResponse(1L,"Radmin","Pikacu",34, Rank.CHIEF_OFFICER,"UZB300");
    OfficerEntity officer= new OfficerEntity();

    @Test
    @DisplayName("GET BY ID OFFICER")
    void get_by_id_shouldReturnOfficerResponse() {

        when(officerRepository.findById(1L)).thenReturn(Optional.of(officer));
        when(officerMapper.toResponse(officer)).thenReturn(officerResponse);

        var byId = officerService.getById(1L);

        assertThat(byId).isNotNull().isEqualTo(officerResponse);

        verify(officerRepository,times(1)).findById(1L);
    }

    @Test
    @DisplayName("CREATE OFFICER")
    void create_shouldReturnOfficerResponse() {
        String password = "$2a$10$XWU7//A4lO77H/oPU8RGTevVc1LfEyaBZv3LvktrpdN2M4cnnD5G.";
        OfficerRequest officerRequest=new OfficerRequest("Radmin","Pikacu",34, Rank.CHIEF_OFFICER,"UZB300",password);

        OfficerEntity savedEntity = new OfficerEntity();
        when(passwordEncoder.encode(password)).thenReturn(password);
        when(officerMapper.toEntity(officerRequest)).thenReturn(officer);
        when(officerRepository.save(officer)).thenReturn(savedEntity);
        when(officerMapper.toResponse(savedEntity)).thenReturn(officerResponse);

        var result = officerService.create(officerRequest);

        assertNotNull(result);
        assertEquals(officerResponse, result);

        verify(officerRepository, times(1)).save(officer);
        verify(officerMapper).toResponse(savedEntity);
    }
}
