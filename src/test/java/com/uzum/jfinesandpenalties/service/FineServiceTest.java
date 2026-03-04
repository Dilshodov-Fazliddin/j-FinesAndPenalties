package com.uzum.jfinesandpenalties.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.uzum.jfinesandpenalties.constant.enums.FinesStatus;
import com.uzum.jfinesandpenalties.dto.request.FineUpdateRequest;
import com.uzum.jfinesandpenalties.dto.response.FineResponse;
import com.uzum.jfinesandpenalties.dto.response.PageResponse;
import com.uzum.jfinesandpenalties.entity.FineEntity;
import com.uzum.jfinesandpenalties.mapper.FineMapper;
import com.uzum.jfinesandpenalties.repository.FineRepository;
import com.uzum.jfinesandpenalties.service.impl.FineServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class FineServiceTest {

    @Mock
    private FineRepository fineRepository;

    @Mock
    private FineMapper fineMapper;

    @InjectMocks
    private FineServiceImpl fineService;

    FineResponse fineResponse = new FineResponse(3L,"Nikita","AD123133",3L,4000L);
    FineEntity fineEntity = new FineEntity();

    @Test
    @DisplayName(value = "Get All fine")
    void getAllFine_ShouldReturnPageResponse() {
        Pageable pageable = PageRequest.of(0, 10);

        Page<FineEntity> finePage = new PageImpl<>(List.of(fineEntity), pageable, 1);

        when(fineRepository.findAll(pageable)).thenReturn(finePage);
        when(fineMapper.toResponse(fineEntity)).thenReturn(fineResponse);

        PageResponse<FineResponse> result = fineService.getAllFine(pageable);

        assertNotNull(result);
        assertEquals(1, result.content().size());
        assertEquals(fineResponse, result.content().get(0));
        assertEquals(1, result.page());
        assertEquals(1, result.totalElements());
        assertEquals(10, result.size());

        verify(fineRepository, times(1)).findAll(pageable);
        verify(fineMapper, times(1)).toResponse(any());
    }


    @Test
    @DisplayName("Get Fine entity by id")
    void getById_shouldReturnFineResponse() {

        when(fineRepository.findById(1L)).thenReturn(Optional.of(fineEntity));
        when(fineMapper.toResponse(fineEntity)).thenReturn(fineResponse);

        var result=fineService.getFineById(1L);

        assertThat(result).isNotNull();

        assertThat(fineResponse).isNotNull().isEqualTo(result);


        verify(fineRepository,times(1)).findById(1L);
    }

    @Test
    @DisplayName("Return Entity By id")
    void fetchById_shouldReturnFineEntity() {
        when(fineRepository.findById(1L)).thenReturn(Optional.of(fineEntity));

        var result=fineService.fetchById(1L);

        assertThat(result).isNotNull().isEqualTo(fineEntity);
    }

    @Test
    @DisplayName("Update Entity By id")
    void updateFineEntity() {
        FineUpdateRequest fineUpdateRequest =
                new FineUpdateRequest(null,null,null,null,FinesStatus.CANCELED,null,null,null);

        FineEntity fine = new FineEntity();
        when(fineRepository.findById(1L)).thenReturn(Optional.of(fine));


        fineService.updateResponseById(1L, fineUpdateRequest);
        verify(fineMapper, times(1)).updateFineFromDto(fineUpdateRequest, fine);
    }
}