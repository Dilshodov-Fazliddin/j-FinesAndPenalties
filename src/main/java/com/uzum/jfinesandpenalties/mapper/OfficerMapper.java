package com.uzum.jfinesandpenalties.mapper;

import com.uzum.jfinesandpenalties.dto.request.OfficerRequest;
import com.uzum.jfinesandpenalties.dto.request.OfficerUpdateRequest;
import com.uzum.jfinesandpenalties.dto.response.OfficerResponse;
import com.uzum.jfinesandpenalties.entity.OfficerEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface OfficerMapper {
    @Mapping(target = "id", ignore = true)
    OfficerEntity toEntity(OfficerRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateOfficerFromDto(OfficerUpdateRequest dto, @MappingTarget OfficerEntity entity);

    OfficerResponse toResponse(OfficerEntity entity);
}
