package com.uzum.jfinesandpenalties.entity;

import com.uzum.jfinesandpenalties.constant.enums.DecisionStatus;
import com.uzum.jfinesandpenalties.constant.enums.DecisionType;
import com.uzum.jfinesandpenalties.entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Entity(name = "decision_entity")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DecisionEntity extends BaseEntity {

    @Column(unique = true,nullable = false)
    String decisionNumber;

    @Column(nullable = false)
    Double fineAmount;

    @Column(nullable = false)
    String comment;

    @Column(nullable = false)
    DecisionType decisionType;

    @Column(nullable = false)
    String judge;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    DecisionStatus decisionStatus;
}
