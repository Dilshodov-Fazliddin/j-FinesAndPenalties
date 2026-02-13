package com.uzum.jfinesandpenalties.entity;

import com.uzum.jfinesandpenalties.constant.enums.FinesStatus;
import com.uzum.jfinesandpenalties.entity.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;


@Entity(name = "Fine_entity")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder
public class FineEntity extends BaseEntity {

    @Column(nullable = false)
    String OffenderPersonalIdentificationNumber;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String passportNumber;

    @Column(nullable = false)
    Long articleId;

    @Enumerated(EnumType.STRING)
    FinesStatus fineStatus;

    @Column(nullable = false)
    Double penaltyAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "officer_id", nullable = false)
    OfficerEntity officer;
}
