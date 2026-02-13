package com.uzum.jfinesandpenalties.mapper;

import com.uzum.jfinesandpenalties.dto.request.FineRequest;
import com.uzum.jfinesandpenalties.dto.request.FineUpdateRequest;
import com.uzum.jfinesandpenalties.dto.response.FineResponse;
import com.uzum.jfinesandpenalties.entity.FineEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface FineMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fineStatus", constant = "CREATED")
    FineEntity toEntity(FineRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFineFromDto(FineUpdateRequest dto, @MappingTarget FineEntity entity);

    FineResponse toResponse(FineEntity entity);
}
