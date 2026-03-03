package com.uzum.jfinesandpenalties.mapper;

import com.uzum.jfinesandpenalties.dto.event.DecisionCreatedEvent;
import com.uzum.jfinesandpenalties.entity.DecisionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DecisionMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "decisionStatus", constant = "UN_PAID")
    DecisionEntity toEntity(DecisionCreatedEvent event);
}
